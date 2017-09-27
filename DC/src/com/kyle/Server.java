package com.kyle;


import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Server {
   public static void main(String[] args) throws IOException {
       try {
           // instantiates a datagram socket for both sending
           // and receiving data
           MyServerDatagramSocket mySocket = new MyServerDatagramSocket(7);
           System.out.println("Server ready.");
           UserAcc user = new UserAcc();
           user.setLoggedIn(false);
           while (true) {  // forever loop

               DatagramMessage request = mySocket.receiveMessageAndSender();
               String message = request.getMessage();
               System.out.println("Request received: "+ message);

               //System.out.println(message.substring(0,3));
                   switch (message.substring(0,3)) {
                       case "100":
                           message = request.getMessage();
                           String username = user.getPartofString(message, 1);
                           String password = user.getPartofString(message, 2);
                           user.setName(username);
                           if (user.login(username, password)){
                               mySocket.sendMessage(request.getAddress(), request.getPort(), "101:Login Successful!");
                               user.setLoggedIn(true);
                               break;
                           }
                           else if (!user.login(username, password)) {
                               mySocket.sendMessage(request.getAddress(), request.getPort(), "102:Login Unsuccessful. Using credentials to set up account.");
                               user.login(username, password);
                               user.setLoggedIn(true);
                               break;
                           }

                       case "200":

                           String fileName = message.split(" ")[1];
                           String fileData = message.split(" ")[2];
                           try {
                               File upFile = new File("C:/Users/Amanda/Desktop/DC/" + user.getName() + "/"+ fileName);

                               if (!upFile.exists()) {
                                   // Create File on Server if it doens't exist yet.
                                   upFile.createNewFile();
                                    // Write to File on Server
                                   Files.write(Paths.get("C:/Users/Amanda/Desktop/DC/" + user.getName() + "/" + fileName), fileData.getBytes(), StandardOpenOption.APPEND);
                               }
                               String conformation = "201:File Name: " + fileName + " has been uploaded to server";
                               mySocket.sendMessage(request.getAddress(),
                                       request.getPort(), conformation);
                           } catch(FileAlreadyExistsException ex){
                               System.out.println("202:The file already Exists");
                           }


                               break;
                       case "300":

                           String file = message.split(" ")[1];
                           String data = message.split(" ")[2];
                           try {
                               File fetchFile = new File("C:/abc/"+ file);

                               if (!fetchFile.exists()) {
                                   // Create File on Client if it doens't exist yet.
                                   fetchFile.createNewFile();
                                   // Write to File on Client
                                   Files.write(Paths.get("C:/abc/" +file), data.getBytes(), StandardOpenOption.APPEND);
                               }
                               String conformation = "301:File Name: " + file + " has been downloaded to client";
                               mySocket.sendMessage(request.getAddress(),
                                       request.getPort(), conformation);
                           } catch(FileAlreadyExistsException ex){
                               System.out.println("302:The file already Exists");
                           }

                           break;
                       case "400":
                           mySocket.sendMessage(request.getAddress(),
                                   request.getPort(), "401:Logging Off");
                           user.logOff();
                           break;
                       default:
                           mySocket.sendMessage(request.getAddress(),
                                   request.getPort(), "402:Incorrect input!! Logging Off");
                           user.logOff();
                           break;
                   }


               }//end while


       } // end try
       catch (Exception ex) {
           ex.printStackTrace( );
       } // end catch


   } //end main
   

} // end class      
