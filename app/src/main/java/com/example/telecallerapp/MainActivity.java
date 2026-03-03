package com.example.telecallerapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cloudinary.android.MediaManager;
import com.example.telecallerapp.databinding.ActivityMainBinding;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding biding;
    FirebaseAuth auth;
    ImageView imgProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);

        imgProfile = drawerLayout.findViewById(R.id.imgProfile);
        TextView tvName = drawerLayout.findViewById(R.id.tvName);

        auth = FirebaseAuth.getInstance();
        String uid = auth.getUid();

        if(uid == null){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(uid)
                .child("profileImage")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            Glide.with(MainActivity.this)
                                    .load(snapshot.getValue(String.class))
                                    .circleCrop()
                                    .into(imgProfile);
                        }
                    }

                    @Override public void onCancelled(@NonNull DatabaseError error) {}
                });




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
        loadRecentActivity();

        TextView menuProfile = drawerLayout.findViewById(R.id.menuProfile);



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

    private void loadRecentActivity() {

        String uid = FirebaseAuth.getInstance().getUid();
        if (uid == null) return;

        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(uid)
                .child("RecentActivity");

        ref.orderByChild("timestamp")
                .limitToLast(10)
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        ArrayList<String[]> list = new ArrayList<>();

                        for (DataSnapshot snap : snapshot.getChildren()) {

                            String name = snap.child("leadName").getValue(String.class);
                            String action = snap.child("action").getValue(String.class);

                            if (name != null && action != null) {
                                list.add(new String[]{name, action, "Now"});
                            }
                        }

                        Collections.reverse(list);

                        RecyclerView recyclerRecent = findViewById(R.id.recyclerRecentActivity);
                        recyclerRecent.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        recyclerRecent.setAdapter(new RecentActivityAdapter(list));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadProfileImage();
    }
        private void loadProfileImage() {

        String uid = FirebaseAuth.getInstance().getUid();
        if (uid == null) return;

        FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(uid)
                .child("profileImage")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String url = snapshot.getValue(String.class);

                            Glide.with(MainActivity.this)
                                    .load(url)
                                    .circleCrop()
                                    .into(imgProfile);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
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
