package com.pantz.user;

import java.util.ArrayList;

public class AccountClass {

    private ArrayList<EmailClass> mailbox;
    private String username;
    private String password;
    private int userAutoId;

    public AccountClass(String userName , String userPassword) {
        this.username = userName;
        this.password = userPassword;
        this.mailbox = new ArrayList<>();
        this.userAutoId = 1; //CHECK

    }

    ////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////Setters & Getters/////////////////////////
    //////////////////////////////////////////////////////////////////////////

    public ArrayList<EmailClass> getMailbox() {
        return mailbox;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getUserAutoId() {
        return userAutoId;
    }

    ///////////////////////////////////////////////////////////////////////
    /////////////////////////////METHODS//////////////////////////////////


    /**
     * Checks if passwords match
     * @param givenPassword ---> Password that user gives
     * @return ---> true or false
     */

    //CHECK
    public boolean isPasswordCorrect(String givenPassword){

        return this.password.equals(givenPassword);
    }




    /**
     * To give a unique Id for every user
     * in order yo have a specific id for
     * each user.
     */

    //CHECK
    private void giveUniqueId(){
        userAutoId = userAutoId;
        userAutoId++;
    }


    /**
     *
     * @param idToSearch ---> in order to search and find the mail with this user's id
     * @return  --> email if the id exists/ null if id doesn't
     */
    public EmailClass giveEmail(int idToSearch){
         for(EmailClass emailToReturn : mailbox){

             if(idToSearch==emailToReturn.getId()){
                 return  emailToReturn;
             }

         }
        System.out.println("Not found!"); //for testing
         return null;

    }







}
