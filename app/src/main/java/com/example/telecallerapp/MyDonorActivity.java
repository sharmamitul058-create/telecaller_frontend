package com.example.telecallerapp;

import android.os.Bundle;
import android.widget.Toast;

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

public class MyDonorActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Donor> donorList;
    DonorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_donor);

        recyclerView = findViewById(R.id.recyclerDonors);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        donorList = new ArrayList<>();
        adapter = new DonorAdapter(donorList);
        recyclerView.setAdapter(adapter);

        loadDonors();
    }

    private void loadDonors() {
        String userId = FirebaseAuth.getInstance().getUid();

        if (userId == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("donors")
                .child(userId);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                donorList.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    Donor donor = ds.getValue(Donor.class);
                    if (donor != null) {
                        donorList.add(donor);
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MyDonorActivity.this,
                        error.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
