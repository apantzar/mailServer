package com.pantz.user;

public class EmailClass {

   private int id;
   private  boolean isNewEmail=true;
   private String sender, receiver, subject, mainbody;
   private int idIncreaser=1; //CHECK


    public EmailClass(String sender, String receiver, String subject, String mainbody) {
        setId();
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
        this.mainbody = mainbody;

    }


    /**
     * We have to increase the email id in order to
     * have unique ids for our emails.
     * For each mail sets a unique id
     */
    public void setId() {
        id = idIncreaser++;
    }


    ///////////////////////////////////////SETTERS-GETTERS///////////////////////////////////////////




    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSubject() {
        return subject;
    }

    public String getMainbody() {
        return mainbody;
    }

    public int getId() {
        return id;
    }


    /**
     * Checks if the email is old mail
     * and sets the @variable isNewEmail= false
     */
    public boolean isRead(){

        return  isNewEmail ;
    }

    public  void setRead(){
        isNewEmail = false;
    }


    /**
     * Checks if the email is new mail
     * and sets the @variable isNewEmail= true
     */


}
