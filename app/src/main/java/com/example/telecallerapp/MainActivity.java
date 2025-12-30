package com.example.telecallerapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerRecent = findViewById(R.id.recyclerRecentActivity);
        recyclerRecent.setLayoutManager(new LinearLayoutManager(this));
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);

        toolbar.setNavigationOnClickListener(v -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });


        ArrayList<String[]> recentList = new ArrayList<>();
        recentList.add(new String[]{"John Doe", "No answer • Added to callback list", "2m ago"});
        recentList.add(new String[]{"Sarah Smith", "Callback scheduled • Pricing inquiry", "2:00 PM"});
        recentList.add(new String[]{"Michael Key", "Deal closed • Contract sent", "1h ago"});

        RecentActivityAdapter adapter =
                new RecentActivityAdapter(recentList);
        recyclerRecent.setAdapter(adapter);




        // Dashboard cards
        CardView cardFresh = findViewById(R.id.cardFresh);
        CardView cardContacted = findViewById(R.id.cardContacted);
        CardView cardInterested = findViewById(R.id.cardInterested);

        cardFresh.setOnClickListener(v -> openLeadsScreen("Fresh"));
        cardContacted.setOnClickListener(v -> openLeadsScreen("Contacted"));
        cardInterested.setOnClickListener(v -> openLeadsScreen("Interested"));

        // Drawer

        findViewById(R.id.menuSettings).setOnClickListener(v -> {
            drawerLayout.closeDrawers();
        });

        findViewById(R.id.menuLogout).setOnClickListener(v -> {
            finish();

        });
        findViewById(R.id.menuSettings).setOnClickListener(v -> {
            startActivity(new Intent(this, SettingsActivity.class));
            drawerLayout.closeDrawers();
        });
        findViewById(R.id.menuCampaigns).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CampaignsActivity.class));
            drawerLayout.closeDrawers();
        });
        findViewById(R.id.menuTemplates).setOnClickListener(v -> {
            startActivity(new Intent(this, MessageTemplatesActivity.class));
            drawerLayout.closeDrawers();
        });
        findViewById(R.id.menuDonation).setOnClickListener(v -> {
            startActivity(new Intent(this, DonationActivity.class));
            drawerLayout.closeDrawers();
        });
        findViewById(R.id.menuPrasadam).setOnClickListener(v -> {
            startActivity(new Intent(this, PrasadamActivity.class));
            drawerLayout.closeDrawers();
        });
        findViewById(R.id.menuAllLeads).setOnClickListener(v -> {
            openLeadsScreen("ALL");
        });
        findViewById(R.id.menuCallStatus).setOnClickListener(v -> {
            startActivity(new Intent(this, CallStatusActivity.class));
        });




    }

    private void openLeadsScreen(String type) {
        Intent intent = new Intent(MainActivity.this, LeadActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

}
