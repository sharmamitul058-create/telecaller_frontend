package com.example.telecallerapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;



import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CampaignsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaigns);

        RecyclerView recyclerView = findViewById(R.id.recyclerCampaigns);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<String[]> campaigns = new ArrayList<>();
        campaigns.add(new String[]{"Q4 SaaS Outreach", "Active", "CTOs in FinTech", "65/100", "Oct 1", "Dec 31"});
        campaigns.add(new String[]{"Cold Leads Reactivation", "Paused", "Retail Managers", "12/100", "Sep 15", "Nov 15"});
        campaigns.add(new String[]{"Webinar Follow-up", "Draft", "Recent Registrants", "0/50", "Yesterday", "-"});

        recyclerView.setAdapter(new CampaignAdapter(campaigns));
    }
}
