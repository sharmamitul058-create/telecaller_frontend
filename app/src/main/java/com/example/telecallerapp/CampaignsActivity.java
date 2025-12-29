package com.example.telecallerapp;

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

        ArrayList<String> campaigns = new ArrayList<>();
        campaigns.add("Q4 SaaS Outreach - Active - 65/100");
        campaigns.add("Cold Leads Reactivation - Paused - 12/100");
        campaigns.add("Webinar Follow-up - Draft - 0/50");

        recyclerView.setAdapter(new CampaignAdapter(campaigns));
    }
}
