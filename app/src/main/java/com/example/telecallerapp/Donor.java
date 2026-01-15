package com.example.telecallerapp;

public class Donor {

    public String donorId;
    public String name;
    public String phone;
    public String location;
    public String amount;
    public String userId;

    // REQUIRED empty constructor for Firebase
    public Donor() {
    }

    public Donor(String donorId,
                 String name,
                 String phone,
                 String location,
                 String amount,
                 String userId) {

        this.donorId = donorId;
        this.name = name;
        this.phone = phone;
        this.location = location;
        this.amount = amount;
        this.userId = userId;
    }
}
