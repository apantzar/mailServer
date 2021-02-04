package com.pantz;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class MailClient {

    public static final String CONNECTION_ERROR_FLAG = "Oh no, connection error";

    private Scanner usersGivenInput;
    private int port;
    private InetAddress ipAddress;
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    public MailClient(Scanner usersGivenInput, int port, InetAddress ipAddress) {
        this.usersGivenInput = new Scanner(System.in);
        this.port = port;
        this.ipAddress = ipAddress;

        checkConnection();


    }


    /**
     * Try to connect to the server
     */

    public void checkConnection(){
        if(tryToConnect()){

            starter();

        }else{

            close(CONNECTION_ERROR_FLAG);

        }
    }



    public void starter(){


    }

    public void close(String error){

    }


    /**
     *
     * @return---> true or false if server has connection problem
     */
    public boolean tryToConnect(){
        try{
            socket = new Socket(ipAddress , port);
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());


        }catch (IOException e){
            e.printStackTrace();
            return false;
        }

        return true;
    }



}
