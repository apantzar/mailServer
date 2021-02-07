package com.pantz.user;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountClass {

    private List<EmailClass> mailbox;
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

    public List<EmailClass> getMailbox() {
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
    /////////////////////////////////////////////////////////////////////


    /**
     * To check if the username of the email account is valid( for example email without @
     * is not valid)
     * @param username --> input to check if username is valid
     * @return --> the match (true or false)
     */

    public boolean validationChecker(String username){
        Pattern patternForCheck = Pattern.compile(".+@.+\\...+");
        Matcher matcher = patternForCheck.matcher(username);
        return matcher.matches();
    }



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
     * @param id ---> in order to find the email that should be deleted
     * @return false if the emailToDelete is equal to null
     */

    public boolean deleteEmail(int id){

        EmailClass emailToDelete = giveEmail(id);
        if(emailToDelete==null){
            return false;
        }
        //Will be  change (maybe)
        return mailbox.remove(emailToDelete);

    }


    /**
     *
     * @param newEmail ---> email to add
     * @return---> true or false depends the check statement
     */


    public boolean addEmail(EmailClass newEmail){

        if(newEmail.getReceiver().equals(newEmail.getSender())){
            return  false;
        }

        if(!newEmail.getReceiver().equals(username)){
            return false;
        }

        return true;


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
