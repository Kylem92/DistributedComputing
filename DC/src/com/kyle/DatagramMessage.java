package com.kyle;

import java.net.*;

public class DatagramMessage{
   private String message;
   private InetAddress senderAddress;
   private int senderPort;
   public void putVal(String message, InetAddress addr, int port) {
      this.message = message.trim();
      this.senderAddress = addr;
      this.senderPort = port;
   }

   public String getMessage( ) {
      return this.message.trim();
   }

   public InetAddress getAddress( ) {
      return this.senderAddress;
   }

   public int getPort( ) {
      return this.senderPort;
   }
} // end class  
