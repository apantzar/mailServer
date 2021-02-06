package com.pantz;

import com.pantz.user.AccountClass;
import com.pantz.user.EmailClass;
import com.pantz.utils.BufferClass;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MailServer {
    private int port;
    private ServerSocket socket;
    private ArrayList<AccountClass> accounts = new ArrayList<>();
    private ArrayList<Functions> functions = new ArrayList<>();
    private String menuOptionInput;
    private boolean stat;
    private static final int DEF_PORT=2340;
    public  MailServer theServer;
    private static  final String WRONG_INPUT_FLAG = "[✘] Dear user, please give valid 'ip' 'port' values";


    public MailServer(int port){
        this.port = port;
        theServer = this;
        initAccounts();
        starter();

    }



    public MailServer(){
        this(DEF_PORT);
    }




    public static void main(String[] args) {

        try{
            if(args.length==1){
                new MailServer(Integer.parseInt(args[0]));
            }else{
                new MailServer();
            }
        }catch (Exception e){

            System.out.println(WRONG_INPUT_FLAG);

            e.printStackTrace();

        }






    }



    public void initAccounts(){

        AccountClass accountClass1 = new AccountClass("thomson@gmail.com" , "5637");
        AccountClass accountClass2 = new AccountClass("alex@csd.com" , "8900");
        AccountClass accountClass3 = new AccountClass("maria@protonmail.com" , "789");

        accountClass1.addEmail(new EmailClass("thomson@gmail.com" ,"alex@csd.com","Exams Results","Hello, 09009:10 , 0450:3 , 09380:8 , 900:1"));


    }

    public void starter(){


        try{
           socket = new ServerSocket(port);
            System.out.println("Wait...");

        }catch (IOException e){
            System.out.println("error");
            e.printStackTrace();
        }

        while (true){
            Socket cl = null;
            try {
                cl = socket.accept();
            }catch (IOException e){

                e.printStackTrace();
                continue;


            }

            System.out.println("You are connected -->"+cl.getInetAddress() + "::" +port);
            Functions f = new Functions(cl);

        }



    }




    //////////////////////////////////////////////////////////////////////////////
    /////////////////////////////The-Functions-Class/////////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    /**
     * This class will have all the valid actions for MailServer like
     * logIn(), newEmail(), showEmails(), readEmail() etc.
     *
     */


    public class Functions extends Thread{

        public static final String EXIT_FLAG = "Bye!";

        private Socket theClient;
        private ObjectInputStream inputCl;
        private ObjectOutputStream outputCl;
        private BufferClass out;
        private ArrayList<String> loginClient = new ArrayList<>() ;
        private ArrayList <String> noLoginClient = new ArrayList<>();
        private AccountClass mailUser;


        public Functions(Socket theClient){
            this.theClient = theClient;
            out = new BufferClass();


            try{
                inputCl = new ObjectInputStream(theClient.getInputStream());
                outputCl= new ObjectOutputStream(theClient.getOutputStream());
                start();
            }catch (IOException e){
                exit();
                e.printStackTrace();
            }



        }


        /**
         * Method giveValuestoList adds options
         * into lists for guest users , registered users
         */

        public void giveValuestoList(){

            noLoginClient.add("Login");
            noLoginClient.add("Register");
            noLoginClient.add("exit");

            loginClient.add("NewEmail");
            loginClient.add("ShowEmails");
            loginClient.add("ReadEmail");
            loginClient.add("DeleteEmail");
            loginClient.add("LogOut");
            loginClient.add("Exit");



            while (true){

                if(mailUser.equals(null)){

                    stat = welcomeMsg("Hello, you connected as a guest",stat);
                    menuOptionInput = menuInput(noLoginClient);
                }else{
                    stat = welcomeMsg("Welcome back "+ mailUser.getUsername(),stat);
                    menuOptionInput = menuInput(loginClient);
                }



                choice(menuOptionInput);







            }




        }




        public void choice(String index){

            switch (index){

                case "Exit":
                    exit();
                    break;
                case "Login":
                    logIn();
                    break;
                case "Register":
                    register();
                    break;
                case "newEmail":
                    newEmail();
                    break;
                case "showEmails":
                    showEmails();
                    break;
                case "readEmails":
                    readEmails();
                    break;
                case "deleteEmails":
                    deleteEmails();
                    break;
                case "logOut":
                    logOut();
                    break;


            }




        }





        public void logIn(){







        }


        public void register(){


            String username;
            String password;

            out.adder("Type your username "+System.lineSeparator());
            username = inputGetter();



            out.adder("Type your password"+System.lineSeparator());
            password=inputGetter();

            AccountClass newUser = new AccountClass(username , password);


        }



        public void readEmails(){

        }


        public void newEmail(){



        }


        public  void showEmails(){



        }


        public void deleteEmails(){


        }



        public void logOut(){


        }







        /**
         *
         * @param msg --> message to show to user
         * @param stat ---> boolean variable to use above to examine if user
         *             is guest or not.
         * @return --> stat value (true or false)
         */

        public boolean welcomeMsg(String msg , boolean stat){

            if((mailUser.equals(null) && stat) || (!mailUser.equals(null) && !stat)){

                out.addDisplayLine(10 , '=');
                out.adder(msg + System.lineSeparator());

                return !stat;

            }


            return stat;

        }






        /**
         *Shows menu and returns user's input
         * @param menuOptions
         * @return
         */

        private String menuInput(ArrayList<String> menuOptions){

            while (true){
                out.addDisplayLine(10 , '=');
                for (String option: menuOptions){
                    out.adder(") "+ System.lineSeparator() );

                }
                out.addDisplayLine(10 , '=');
                menuOptionInput = inputGetter();


                if(menuOptions.contains(menuOptionInput)|| menuOptionInput.equals(null)){
                    return menuOptionInput;
                }else{
                    out.adder("Check your input. " + System.lineSeparator());
                }

            }




        }

        /**
         * Sends the output to user & returns the input
         * @return user's input
         */

        private String inputGetter(){

            try {
                outputCl.writeObject(out.push());
                return (String) inputCl.readObject();

            }catch (Exception e){

                e.printStackTrace();
                return null;
            }

        }


        /**
         * This, close(), method will close streams and
         * write the exit_flag in order to stop
         * the process.
         */

        public void exit(){
            try{

                outputCl.writeObject(EXIT_FLAG);

                //closing streams
                inputCl.close();
                outputCl.close();
                theClient.close();

            }catch (Exception e){
                e.printStackTrace();
            }
        }




    }






}
