package com.example.telecallerapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

public class LeadActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Lead> leadList;
    LeadAdapter adapter;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);


        // ✅ Get type SAFELY here
        type = getIntent().getStringExtra("type");
        if (type == null) type = "ALL";

        TextView tvHeading = findViewById(R.id.tvHeading);
        tvHeading.setText(type + " Leads");

        recyclerView = findViewById(R.id.recyclerLeads);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        leadList = new ArrayList<>();
        adapter = new LeadAdapter(leadList);
        recyclerView.setAdapter(adapter);

        String userId = FirebaseAuth.getInstance().getUid();
        if (userId == null) return;

        DatabaseReference leadRef = FirebaseDatabase
                .getInstance()
                .getReference("leads")
                .child(userId);

        leadRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                leadList.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    Lead lead = ds.getValue(Lead.class);
                    if (lead == null || lead.status == null) continue;

                    if (type.equalsIgnoreCase("ALL") ||
                            lead.status.equalsIgnoreCase(type)) {
                        leadList.add(lead);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
}
