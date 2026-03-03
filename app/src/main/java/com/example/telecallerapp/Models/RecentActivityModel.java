package com.example.telecallerapp.Models;

public class RecentActivityModel {

    public String leadName;
    public String action;
    public long timestamp;

    public RecentActivityModel() {}

    public RecentActivityModel(String leadName, String action, long timestamp) {
        this.leadName = leadName;
        this.action = action;
        this.timestamp = timestamp;
    }
}
