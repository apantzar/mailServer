package com.pantz;

import com.pantz.utils.BufferClass;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MailClient {

    public static final String CONNECTION_ERROR_FLAG = "[✘] Oh no, connection error 〲",
     NOT_FOUND_CONNECTION__ERROR_FLAG = "[✘] Wow ,server is down now please try again ⍨ ",
     WRONG_INPUT_FLAG = "[✘] Dear user, please give valid 'ip' 'port' values",
     EXIT_FLAG = "Bye!";

    private Scanner usersGivenInput;
    private int port;
    private InetAddress ipAddress;
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private String inputForStarter , getResponseValue;


    public MailClient() throws UnknownHostException{
        this(4456, InetAddress.getLocalHost());
    }

    public MailClient( int port, InetAddress ipAddress) {
        this.usersGivenInput = new Scanner(System.in);
        this.port = port;
        this.ipAddress = ipAddress;

        checkConnection();


    }


    public static void main(String[] args){

        try{
            if(args.length == 2){
                InetAddress ipAddress = InetAddress.getByName(args[0]);
                int port = Integer.parseInt(args[1]);
                new MailClient(port , ipAddress);


            }
        }catch (Exception e){

        }
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


    /**
     * To start connection with the server
     */

    public void starter(){

        while (true){

            if(UI()){
                inputForStarter = this.usersGivenInput.nextLine();
                postInput(inputForStarter);


            }else{
                break;
            }
        }

        close(null);

    }


    /**
     * This method close streams, and shows
     * the error.
     * @param error --> the error that program closed
     */
    public void close(String error){

        if(error != null){
            System.out.println(error);
        }
        try {
            input.close();
            output.close();
            socket.close();

        }catch (Exception e){
            System.out.println("Can't close streams: ");
            e.printStackTrace();

        }

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


    /***
     *
     * @return true or false:
     */


    public boolean UI(){
        BufferClass bufferClass = new BufferClass();
        try{
            getResponseValue = (String) input.readObject();

            if(getResponseValue.equals(EXIT_FLAG)){
                return false;
            }

            bufferClass.addDisplayLine(10 , '=');
            System.out.println("[*] MailServer");
            bufferClass.addDisplayLine(10 , '=');
            bufferClass.adder(getResponseValue);
            System.out.println(bufferClass.push());


        }catch(Exception e){

            close(NOT_FOUND_CONNECTION__ERROR_FLAG);
            e.printStackTrace();

        }

        return true;
    }


    /**
     * Sends the users input to server
     * @param index --> temporary variable to be send to server
     */

    public void postInput(String index){

        try {
            output.writeObject(index);
        }catch (IOException e){
            close(CONNECTION_ERROR_FLAG);
            e.printStackTrace();
        }

    }



}
