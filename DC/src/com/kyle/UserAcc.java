package com.kyle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Kyle on 08/11/2016.
 */
public class UserAcc {
    private boolean status = false;
    public String name;


    public boolean login(String username, String password) throws IOException {
        if(checkLogin(username, password)){
            status = true;
            return true;
        }
        else
        {
            writeFile(username, password);
            createDir(username);
            status = true;
            return false;
        }
    }

    public void setLoggedIn(boolean status){this.status=status;}


    public static void createDir(String message) throws FileNotFoundException, IOException {
        String fileName = message;
        new File(fileName).mkdir();

    }



    public static String firstWord(String input) {
        return input.split(" ")[0];
    }

    public static String getPartofString(String input, int part) {
        return input.split(" ")[part];
    }



    private static boolean checkLogin(String username, String password) throws FileNotFoundException, IOException {

        //prepare String
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(username);
        sBuilder.append(" ");
        sBuilder.append(password);

        //Check Login file for username/password
        File f = new File("login.txt");
        if (!f.exists()) {

            f.createNewFile();
        }
        try {
            Scanner scanner = new Scanner(f);

            //read the file line by line
            int lineNum = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lineNum++;
                if (line.equalsIgnoreCase(sBuilder.toString())) {

                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.print("Username password error");
        }
        return false;
    }


    public static void writeFile(String username, String password) throws IOException {
        File f = new File("login.txt");

        FileOutputStream oFile = new FileOutputStream(f,true);
        // get the content in bytes
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(username);
        sBuilder.append(" ");
        sBuilder.append(password);
        byte[] messageBytes = sBuilder.toString().getBytes();

        try {
            oFile.write(messageBytes);
            oFile.write(System.getProperty("line.separator").getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
        oFile.flush();
        oFile.close();

    }

    public boolean getLoggedIn(){
        return status;
    }

    public void logOff()
    {
        setLoggedIn(false);
    }


    public void setName(String name) {
        this.name = name;
    }
    public String getName(){return name;}
}
