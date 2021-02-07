package com.pantz.user;

public class EmailClass {

   private int id;
   private  boolean isNewEmail;
   private String sender;
   private String receiver;
   private String subject;
   private String mainbody;
   private int idIncreaser; //CHECK


    public EmailClass(String sender, String receiver, String subject, String mainbody) {
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
        this.mainbody = mainbody;

        this.id = id;
        this.isNewEmail = true;
        this.idIncreaser=1; //μπορει να θελει αλλαγη θεσης
    }


    /**
     * We have to increase the email id in order to
     * have unique ids for our emails.
     * @param id --> For each mail sets a unique id
     */
    public void setId(int id) {
        id = idIncreaser++;
    }


 /*   public boolean isNewEmail() {
        return isNewEmail;
    }*/

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

    public int getIdIncreaser() {
        return idIncreaser;
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


    public void isNew(){
        isNewEmail = true;
    }
}
