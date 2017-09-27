package com.kyle;

import java.io.*;
import java.net.SocketException;


public class Client {

   public static void main(String[] args) throws SocketException {
	   
      InputStreamReader is = new InputStreamReader(System.in);
      BufferedReader br = new BufferedReader(is);

      try {

         String hostName = "localhost";
         String portNum = "7";
         ClientHelper helper =
            new ClientHelper(hostName, portNum);
         boolean done = false;
         String message = "", response;
         while (!done) {

            System.out.println("Please enter L to Login, U to Upload, D to download or Q to log off: ");
            String choice = br.readLine();

            if(choice.charAt(0) == 'L')
               message += "100:Login";
            if(choice.charAt(0)== 'U')
               message += "200:Upload";
            if(choice.charAt(0)== 'D')
               message += "300:Download";
            if(choice.charAt(0)== 'Q')
               message += "400:Logout";

               response = "";

            if(message.equals("100:Login")) {
               System.out.println("Enter username and password: ");
               message += " ";
               message += br.readLine();

               response = helper.getResponse(message);

               System.out.println(response);

               message = "";
            }
            else if(message.equals("200:Upload")){
               System.out.println("Enter name of the file to upload: ");
               message += " ";
               String filename = br.readLine();
               response = helper.uploadFile(message, filename);
               System.out.println(response);
               message = "";
            }
            else if(message.equals("300:Download"))
            {
               System.out.println("Enter name of the file to download: ");
               message += " ";
               String filename = br.readLine();
               response = helper.downloadFile(message, filename);
               System.out.println(response);
               message = "";

            }
            else if(message.equals("400:Logout"))
            {
               System.out.println("401:Logging off");
               response = helper.getResponse(message);
               message = "";
            }
         }// end while


      } // end try  
      catch (Exception ex) {
         ex.printStackTrace( );
      } // end catch


   } //end main

} // end class      
