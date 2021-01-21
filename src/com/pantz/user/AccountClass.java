package com.pantz.user;

import java.util.ArrayList;

public class AccountClass {

    private ArrayList<EmailClass> superMail;
    private String userName;
    private String userPassword;
    private int userAutoId=1;

    public AccountClass(String userName , String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.superMail = new ArrayList<>();

    }

    ////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////Setters & Getters/////////////////////////
    //////////////////////////////////////////////////////////////////////////

    public ArrayList<EmailClass> getSuperMail() {
        return superMail;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public int getUserAutoId() {
        return userAutoId;
    }

    ///////////////////////////////////////////////////////////////////////



    /**
     * Checks if passwords match
     * @param givenPassword ---> Password that user gives
     * @return ---> true or false
     */

    public boolean isPasswordCorrect(String givenPassword){

        return this.userPassword.equals(givenPassword);
    }



    /**
     * To give a unique Id for every user
     * in order yo have a specific id foe
     * each user.
     */

    private void giveUniqueId(){
        userAutoId = userAutoId;
        userAutoId++;
    }


}
