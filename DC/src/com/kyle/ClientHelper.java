package com.kyle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;


public class ClientHelper {
   private MyClientDatagramSocket mySocket;
   private InetAddress serverHost;
   private int serverPort;

   ClientHelper(String hostName, String portNum)
      throws SocketException, UnknownHostException { 
  	   this.serverHost = InetAddress.getByName(hostName);
  		this.serverPort = Integer.parseInt(portNum);
      // instantiates a datagram socket for both sending
      // and receiving data
   	this.mySocket = new MyClientDatagramSocket(); 
   } 
	
   public String getResponse (String message)
      throws SocketException, IOException {                                                                                 
      String response = "";
      mySocket.sendMessage( serverHost, serverPort, message);
	   // now receive the echo
      response = mySocket.receiveMessage();
      return response.trim();
   } //end getResponse



   public String uploadFile(String message, String fileName)
           throws SocketException, IOException,FileNotFoundException,NoSuchFileException {
      String reply = "";
      message += fileName;
      Path file = Paths.get("C://"+ fileName);
      byte[] arrayConvert = Files.readAllBytes(file);
      String fileData = new String(arrayConvert);
      message += " " + fileData;
      mySocket.sendMessage( serverHost, serverPort, message);
      reply = mySocket.receiveMessage();
      return reply;
   }

   public String downloadFile(String message, String fileName)
           throws SocketException, IOException,FileNotFoundException,NoSuchFileException {
      String reply = "";
      message += fileName;
      Path file = Paths.get("C://Users//Amanda//Desktop//DC//kyle//" + fileName);
      byte[] arrayConvert = Files.readAllBytes(file);
      String fileData = new String(arrayConvert);
      message += " " + fileData;
      mySocket.sendMessage( serverHost, serverPort, message);
      reply = mySocket.receiveMessage();
      return reply;
   }

   public void done( ) throws SocketException {
      mySocket.close( );
   }  //end done

} //end class
