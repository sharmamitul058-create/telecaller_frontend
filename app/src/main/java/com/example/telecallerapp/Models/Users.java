package com.example.telecallerapp.Models;

public class Users {
    public String role;

    String name;


    public Users(String profilepic, String mail, String password, String name, String userId, String lastMessage) {
        Profilepic = profilepic;
        this.mail = mail;
        this.password = password;
        this.userId = userId;
        this.lastMessage = lastMessage;
        this.name = name;
    }
    public Users(){}
    //Signup COnstructor
    public Users( String mail, String password, String name) {

        this.mail = mail;
        this.password = password;
        this.name = name;

    }

    public String getProfilepic() {
        return Profilepic;
    }

    public String getname(){return name;}

    public void setProfilepic(String profilepic) {
        Profilepic = profilepic;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    String Profilepic, mail, password, userId, lastMessage, userName;
}
