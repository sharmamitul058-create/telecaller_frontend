package com.example.telecallerapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LeadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);

        // Get title from menu click
        String type = getIntent().getStringExtra("type");
        if (type == null) {
            type = "ALL";
    }
        TextView tvHeading = findViewById(R.id.tvHeading);
        tvHeading.setText(type + "Leads");

        RecyclerView recyclerView = findViewById(R.id.recyclerLeads);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Dummy dynamic data
        ArrayList<String> allLeads = new ArrayList<>();
        allLeads.add("Akash Gupta - 9876543210 - Fresh");
        allLeads.add("Mitul Sharma - 9123456780 - Contacted");
        allLeads.add("Aryan Jha - 9988776655 - Interested");
        allLeads.add("Akanksha Agrawal - 9988998878 - Fresh");
        allLeads.add("Abhijeet Singh - 9876543210 - Fresh");

        ArrayList<String> filteredLeads = new ArrayList<>();
        if (type != null && type.equalsIgnoreCase("ALL")) {
            filteredLeads.addAll(allLeads);
        } else {
            for (String lead : allLeads) {
                if (lead.endsWith(type)) {
                    filteredLeads.add(lead);
                }
            }
        }
// Adapter
        LeadAdapter adapter = new LeadAdapter(filteredLeads);
        recyclerView.setAdapter(adapter);

        // Adapter
    }
}
