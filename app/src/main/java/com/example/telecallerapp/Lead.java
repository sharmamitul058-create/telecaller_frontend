package com.example.telecallerapp;

public class Lead {
    public String id;

    public String name;
    public String phone;
    public String status; // Fresh / Contacted / Interested
    public String createdAt;

    public Lead(){}

    public Lead(String id, String name, String phone, String status) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.status = status;
        this.createdAt = String.valueOf(System.currentTimeMillis());
    }

}
