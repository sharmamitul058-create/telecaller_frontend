package com.example.telecallerapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.telecallerapp.Models.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    TextView tvName, tvEmail, tvRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvRole = findViewById(R.id.tvRole);
        SharedPreferences prefs = getSharedPreferences("USER_PREFS", MODE_PRIVATE);

        String name = prefs.getString("USER_NAME", "");
        String email = prefs.getString("USER_EMAIL", "");

        TextView tvName = findViewById(R.id.tvName);
        TextView tvEmail = findViewById(R.id.tvEmail);

        tvName.setText(name);
        tvEmail.setText(email);


        String uid = FirebaseAuth.getInstance().getUid();
        if (uid == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        FirebaseAuth auth = FirebaseAuth.getInstance();

        TextView tvUserName = findViewById(R.id.tvName);


        TextView tvUserEmail = findViewById(R.id.tvEmail);

        if (uid != null) {
            FirebaseDatabase.getInstance()
                    .getReference("Users")
                    .child(uid)
                    .get()
                    .addOnSuccessListener(snapshot -> {
                        if (snapshot.exists()) {

                            String firebasename = snapshot.child("name").getValue(String.class);
                            String firebaseemail = snapshot.child("email").getValue(String.class);
                            tvUserName.setText(firebasename);
                            tvUserEmail.setText(firebaseemail);

                            tvName.setText(name != null ? name : "User");
                            tvEmail.setText(email != null ? email : auth.getCurrentUser().getEmail());
                        }
                    })
                    .addOnFailureListener(e -> {
                        tvName.setText("User");
                        tvEmail.setText(auth.getCurrentUser().getEmail());
                    });
        }


        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(uid);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user = snapshot.getValue(Users.class);

                if (user == null) {
                    Toast.makeText(ProfileActivity.this,
                            "Profile data not found", Toast.LENGTH_SHORT).show();
                    return;
                }

                tvName.setText(user.getname());
                tvEmail.setText(user.getMail());
                tvRole.setText("Role: " + user.role);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this,
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
