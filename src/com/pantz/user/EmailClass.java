package com.pantz.user;

public class EmailClass {

   private int id;
   private  boolean isNew;
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
        this.isNew = true;
        this.idIncreaser=1; //μπορει να θελει αλλαγη θεσης
    }


    public void setId(int id) {
        id = idIncreaser++;
    }

    public int getId() {
        return id;
    }
}
