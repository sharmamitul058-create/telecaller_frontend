package com.example.telecallerapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessageTemplatesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_templates);

        RecyclerView recyclerView = findViewById(R.id.recyclerTemplates);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<String> templates = new ArrayList<>();
        templates.add("Follow-up Call - Hi {{name}}, just following up on our last conversation.");
        templates.add("Demo Reminder - Reminder about your scheduled demo tomorrow.");
        templates.add("Thank You - Thank you for your time today.");
        templates.add("Cold Outreach - We help businesses improve conversions.");

        recyclerView.setAdapter(new MessageTemplateAdapter(templates));
    }
}
