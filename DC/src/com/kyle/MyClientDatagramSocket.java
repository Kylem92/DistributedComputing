package com.kyle;

import java.net.*;
import java.io.*;


public class MyClientDatagramSocket extends DatagramSocket {
static final int MAX_LEN = 100;  
   MyClientDatagramSocket( ) throws SocketException{
     super( );
   }
   MyClientDatagramSocket(int portNo) throws SocketException{
     super(portNo);
   }
   public void sendMessage(InetAddress receiverHost,
                           int receiverPort,
                           String message)
   		          throws IOException {	
         byte[ ] sendBuffer = message.getBytes( );                                     
         DatagramPacket datagram = 
            new DatagramPacket(sendBuffer, sendBuffer.length, 
                                  receiverHost, receiverPort);
         this.send(datagram);
   } // end sendMessage

   public String receiveMessage()
		throws IOException {		
         byte[ ] receiveBuffer = new byte[MAX_LEN];
         DatagramPacket datagram =
            new DatagramPacket(receiveBuffer, MAX_LEN);
         this.receive(datagram);
         String message = new String(receiveBuffer);
         return message.trim();
   } //end receiveMessage

    public void sendFile()
    {
        try
        {
            InetAddress IP = InetAddress.getByName("localhost");
            int port = 7;
            File file = new File("c:\\abc.txt");
            // create a buffer for the file data
            int len = (int) file.length();
            byte[] message = new byte[len];
            FileInputStream in = new FileInputStream(file);
            int bytes_read = 0, n;
            do {
                n = in.read(message, bytes_read, len - bytes_read);
                bytes_read += n;
            } while ((bytes_read < len) && (n != -1));
            DatagramPacket packet = new DatagramPacket(message, message.length, IP, port);
            DatagramSocket socket = new DatagramSocket();

// Create a datagram socket, send the packet through it, and close it.
            socket.send(packet);
            socket.close();
        } catch (Exception ex)
        {
            System.err.println(ex.getMessage());
        }
    }

    public void receiveFile() throws IOException {
        byte b[] = new byte[1024];

        FileWriter fileWriter = new FileWriter("C:\\Users\\Amanda\\Desktop\\abc.txt");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        while (true) {
            DatagramPacket dp = new DatagramPacket(b, b.length);
            this.receive(dp);
            String str = new String(new String(dp.getData(), 0, dp.getLength()));
            if (str.trim().equals("  "))
                break;
            bufferedWriter.write(str);
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }

} //end class
