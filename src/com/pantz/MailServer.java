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
import java.util.List;

public class MailServer  {

    private static final int SIZE_ID = 10;
    private static final int SIZE_FROM = 22;
    private static final int SIZE_SUBJECT = 40;

    private int port;
    private ArrayList<String> functionsList;
    private ServerSocket socket;
    private List<AccountClass> accounts ;
    private List<Functions> functions;


    private static final int DEF_PORT=1919;

    private AccountClass theUser;
    private static  final String WRONG_INPUT_FLAG = "[X] Dear user, please give valid 'ip' 'port' values";


    public MailServer(int port){
        this.port = port;
        accounts = new ArrayList<>();
        functions = new ArrayList<>();
        Functions.theServer = this;

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
        accountClass1.addEmail(new EmailClass("alex@csd.com" ,"thomson@gmail.com","Answer to this","Check your inbox"));
        accountClass1.addEmail(new EmailClass("thomson@gmail.com" ,"alex@csd.com","Exams Results","Hello, 09009:10 , 0450:3 , 09380:8 , 900:1"));

        isNewAccountAdder(accountClass1);
        isNewAccountAdder(accountClass2);
        isNewAccountAdder(accountClass3);


    }


    /**
     * To start the server in the port that it listens
     * Moreover, sends new function, stores the
     * function into the list.
     *
     */

    public void starter(){


        try{
           socket = new ServerSocket(port);
            System.out.println("Listening for incoming connections...");

        }catch (IOException e){
            System.out.println("[X] Incoming connections error");
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
            Functions functionNotTheList = new Functions(cl);
            functions.add(functionNotTheList);

        }

    }


    public synchronized boolean isNewAccountAdder(AccountClass account){

        if(searchWithUserName(account.getUsername() )!= null){
            return false;
        }

        if(!account.validationChecker(account.getUsername())){
            return false;
        }
        accounts.add(account);

        return true;

    }



    public synchronized AccountClass searchWithUserName(String username){
        for(AccountClass accountClass : accounts){
            if(username.equals(accountClass.getUsername())){
                return accountClass;
            }
        }

        return null;
    }


    public synchronized void remover(Functions function){
        functionsList.remove(function);
        System.out.println("Disconnected");

    }








    //////////////////////////////////////////////////////////////////////////////
    /////////////////////////////The-Functions-Class/////////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    /**
     * This class will have all the valid actions for MailServer like
     * logIn(), newEmail(), showEmails(), readEmail() etc.
     *
     */


    public static class Functions extends Thread{

        public static final String EXIT_FLAG = "Bye!";
        public static MailServer theServer;

        private static Socket theClient;
        private ObjectInputStream inputCl;
        private ObjectOutputStream outputCl;
        private BufferClass out;
        private ArrayList<String> loginClient = new ArrayList<>() ;
        private ArrayList <String> noLoginClient = new ArrayList<>();
        private AccountClass mailUser;
        private AccountClass userR ,userL;
        private String menuOptionInput;
        private boolean stat;
        private AccountClass theUser;

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




        private String menuInput(ArrayList<String> menuOptions){

            while (true){
                out.addDisplayLine(10 , '=');
                for (String option: menuOptions){
                    out.adder("--> " +option +System.lineSeparator() );

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
         * Method run() adds options
         * into lists for guest users , registered users
         *  run() <--Thread method
         */

        public void run(){

            boolean stat = true;

            noLoginClient.add("Login");
            noLoginClient.add("Register");
            noLoginClient.add("Exit");

            loginClient.add("NewEmail");
            loginClient.add("ShowEmails");
            loginClient.add("ReadEmail");
            loginClient.add("DeleteEmail");
            loginClient.add("LogOut");
            loginClient.add("Exit");



            while (true){

                if(mailUser == null){

                    stat = welcomeMsg("Hello, you  connected as a guest",stat);
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
                case "NewEmail":
                    newEmail();
                    break;
                case "ShowEmails":
                    showEmails();
                    break;
                case "ReadEmails":
                    readEmails();
                    break;
                case "DeleteEmails":
                    deleteEmails();
                    break;
                case "LogOut":
                    logOut();
                    break;
            }

        }





        public void logIn(){

            AccountClass testUser;
            String username , password;

            out.adder("Type your username"+System.lineSeparator());
            username = inputGetter();

            out.adder("Type your password"+System.lineSeparator());
            password=inputGetter();

            testUser = theServer.searchWithUserName(username);
            if(testUser==null|| !testUser.isPasswordCorrect(password)){
                out.adder("[X] Login Failed. Wrong username or password"+System.lineSeparator());
            }else {
                this.mailUser= testUser;
            }


        }


        public void register(){

            AccountClass userTest;
            String username, password;

            out.adder("Type your username "+System.lineSeparator());
            username = inputGetter();

            out.adder("Type your password"+System.lineSeparator());
            password=inputGetter();

            userTest = new AccountClass(username , password);

            if(theServer.isNewAccountAdder(userTest)){
               this.mailUser = userTest;
            }else{
                out.adder("[X] Username exists");
            }


        }


        /**
         * Creates and sends email
         * to an account.
         *
         */


        public void newEmail(){

            EmailClass email;
            String mainMail, receiver , subject;
            AccountClass toRece;

            out.adder("Receiver: "+System.lineSeparator());
            receiver = inputGetter();

            out.adder("Subject: "+System.lineSeparator());
            subject = inputGetter();

            out.adder("Main mail: "+System.lineSeparator());
            mainMail = inputGetter();

            toRece = theServer.searchWithUserName(receiver);
            email = new EmailClass(mailUser.getUsername() , receiver , subject , mainMail);

            if(toRece.addEmail(email)&& toRece != null){
                out.adder("Sent"+System.lineSeparator());

            }else{
                out.adder("[X] Error not sent"+System.lineSeparator());
            }
        }


        /**
         * Shows the email with the unique id
         */

        public void readEmails(){
            int theId;
            EmailClass emailClass;

            out.adder("Email with id --->"+System.lineSeparator());

            try{
                theId = Integer.parseInt(inputGetter());
            }catch (Exception e){
                theId = -1;
                e.printStackTrace();
            }
            emailClass = theUser.giveEmail(theId);

            if(emailClass != (null)){
                out.addStringWithSize(10 , "FROM: ");
                out.adder(emailClass.getSender());
                out.adder(System.lineSeparator());
                out.addStringWithSize(10 , "SUBJECT");
                out.adder(emailClass.getSubject());
                out.adder(System.lineSeparator());
                out.addDisplayLine(10 , '|');
                out.adder(emailClass.getMainbody()+System.lineSeparator());
                emailClass.setRead();

            }else{
                out.adder("[X] Error during reading email");
            }

        }


        /**
         * This Method is responsible to show the mailbox
         * to user.
         * @return true or false
         */

        public boolean  showEmails(){
            if(mailUser.getMailbox().size() <1){
                out.adder("Empty mailbox"+System.lineSeparator());
                return false;
            }
            out.addStringWithSize(SIZE_ID, "ID");
            out.addStringWithSize(SIZE_FROM, "FROM");
            out.addStringWithSize(SIZE_SUBJECT, "SUBJECT");
            out.adder(System.lineSeparator());

            for(EmailClass emailClass : mailUser.getMailbox()){
                String theId =  String.format("%d. %s", emailClass.getId() , emailClass.isRead() ? "[New]" : "");

                out.addStringWithSize(SIZE_ID, theId);
                out.addStringWithSize(SIZE_FROM, emailClass.getSender());
                out.addStringWithSize(SIZE_SUBJECT, emailClass.getSubject());
                out.adder(System.lineSeparator());

            }

            return true;

        }

        /**
         * This method will delete
         * an existing email
         */

        public void deleteEmails(){
            int theId;
            out.adder("ID Email: "+System.lineSeparator());
            try{

                theId = Integer.parseInt(inputGetter());

            }catch (Exception e){
                theId = -1;
                e.printStackTrace();
            }

            if(mailUser.deleteEmail(theId)){
                out.adder("Email id -->"+theId+" deleted");
            }else{
                out.adder("[X] Error during delete process ");
            }


        }



        /**
         *
         * @param msg --> message to show to user
         * @param stat ---> boolean variable to use above to examine if user
         *             is guest or not.
         * @return --> stat value (true or false)
         */

        public boolean welcomeMsg(String msg , boolean stat){

            if((mailUser==null && stat) || (mailUser!=null && !stat)){

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


        /**
         * Logout method
         */
        public void logOut(){

            mailUser=null;

        }





    }






}
