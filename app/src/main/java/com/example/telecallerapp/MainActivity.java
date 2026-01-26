package com.example.telecallerapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telecallerapp.databinding.ActivityMainBinding;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding biding;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout actionAddLead = findViewById(R.id.actionAddLead);
        LinearLayout btnStartDialing = findViewById(R.id.btnStartDialing);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_dashboard) {
                return true;
            } else if (id == R.id.nav_leads) {
                openLeadsScreen("ALL");
                return true;
            } else if (id == R.id.nav_campaigns) {
                startActivity(new Intent(this, CampaignsActivity.class));
                return true;
            } else if (id == R.id.nav_call_stats) {
                startActivity(new Intent(this, CallStatusActivity.class));
                return true;
            }
            return false;
        });



        bottomNav.setSelectedItemId(R.id.nav_dashboard);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_dashboard) {
                return true; // already here
            }

            if (id == R.id.nav_leads) {
                // SAME as drawer "All Leads"
                openLeadsScreen("ALL");
                return true;
            }

            if (id == R.id.nav_campaigns) {
                startActivity(new Intent(this, CampaignsActivity.class));
                return true;
            }

            if (id == R.id.nav_call_stats) {
                startActivity(new Intent(this, CallStatusActivity.class));
                return true;
            }


            return false;
        });




        RecyclerView recyclerRecent = findViewById(R.id.recyclerRecentActivity);
        recyclerRecent.setLayoutManager(new LinearLayoutManager(this));
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        TextView menuProfile = drawerLayout.findViewById(R.id.menuProfile);
        TextView tvName = findViewById(R.id.tvName);

        SharedPreferences prefs = getSharedPreferences("USER_PREFS", MODE_PRIVATE);
        String name = prefs.getString("USER_NAME",null);

        tvName.setText(name);


        if (name != null && !name.isEmpty()) {
            tvName.setText(name);
        } else {
            tvName.setText("User");
        }



        toolbar.setNavigationOnClickListener(v -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });
        menuProfile.setOnClickListener(v->{
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        });


        ArrayList<String[]> recentList = new ArrayList<>();
        recentList.add(new String[]{"John Doe", "No answer • Added to callback list", "2m ago"});
        recentList.add(new String[]{"Sarah Smith", "Callback scheduled • Pricing inquiry", "2:00 PM"});
        recentList.add(new String[]{"Michael Key", "Deal closed • Contract sent", "1h ago"});

        RecentActivityAdapter adapter =
                new RecentActivityAdapter(recentList);
        recyclerRecent.setAdapter(adapter);

        findViewById(R.id.btnStartDialing).setOnClickListener(v -> {
            openLeadsScreen("Fresh");

        });



        actionAddLead.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddLeadActivity.class));
        });

        btnStartDialing.setOnClickListener(v ->{
            startActivity(new Intent(MainActivity.this,LeadActivity.class
            ));
        });




        // Dashboard cards
        CardView cardFresh = findViewById(R.id.cardFresh);
        CardView cardContacted = findViewById(R.id.cardContacted);
        CardView cardInterested = findViewById(R.id.cardInterested);

        cardFresh.setOnClickListener(v -> openLeadsScreen("Fresh"));
        cardContacted.setOnClickListener(v -> openLeadsScreen("Contacted"));
        cardInterested.setOnClickListener(v -> openLeadsScreen("Interested"));

        // Drawer


        findViewById(R.id.menuLogout).setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

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
            Intent intent = new Intent(MainActivity.this, DonationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        findViewById(R.id.menuPrasadam).setOnClickListener(v -> {
            startActivity(new Intent(this, PrasadamActivity.class));
            drawerLayout.closeDrawers();
        });
        findViewById(R.id.menuAllLeads).setOnClickListener(v -> {
            openLeadsScreen("ALL");
        });
        findViewById(R.id.menuDonors).setOnClickListener(v -> {
            startActivity(new Intent(this, MyDonorActivity.class));
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
