package com.example.telecallerapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.telecallerapp.Models.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import java.util.HashMap;
import java.util.Map;


public class ProfileActivity extends AppCompatActivity {

    TextView tvName, tvEmail, tvRole;
    ImageView imgProfile;
    FirebaseStorage storage;
    DatabaseReference userRef;
    FirebaseAuth auth;
    ActivityResultLauncher<String> imagePicker;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_profile);

            imgProfile = findViewById(R.id.imgProfile);
            tvName = findViewById(R.id.tvName);
            tvEmail = findViewById(R.id.tvEmail);
            tvRole = findViewById(R.id.tvRole);
            loadProfileImage();


        String uid = FirebaseAuth.getInstance().getUid();

        FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(uid)
                .child("profileImage")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.exists()){

                            Glide.with(ProfileActivity.this)
                                    .load(snapshot.getValue(String.class))
                                    .circleCrop()
                                    .into(imgProfile);
                        }
                    }

                    @Override public void onCancelled(@NonNull DatabaseError error) {}
                });



        imgProfile = findViewById(R.id.imgProfile);
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
                imagePicker=
                registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {

                    if (uri == null) return;

                    Glide.with(this).load(uri).circleCrop().into(imgProfile);

                    uploadProfileImage(uri);
                });

        uid = auth.getUid();
        if(uid == null){
            finish();
            return;
        }

        userRef = FirebaseDatabase.getInstance().getReference("Users").child(uid);


        userRef.get().addOnSuccessListener(snapshot -> {
            if(snapshot.exists()){
                tvName.setText(snapshot.child("name").getValue(String.class));
                tvEmail.setText(snapshot.child("email").getValue(String.class));
                tvRole.setText("Role: Telecaller");
            }
        });

        imgProfile.setOnClickListener(v -> openGallery());



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

    private void uploadProfileImage(Uri uri) {

        MediaManager.get().upload(uri)
                .unsigned("telecaller_unsigned")
                .option("folder", "profile_pics")
                .callback(new UploadCallback() {

                    @Override
                    public void onSuccess(String requestId, Map resultData) {

                        String imageUrl = resultData.get("secure_url").toString();

                        String uid = FirebaseAuth.getInstance().getUid();

                        FirebaseDatabase.getInstance()
                                .getReference("Users")
                                .child(uid)
                                .child("profileImage")
                                .setValue(imageUrl)
                                .addOnSuccessListener(aVoid ->
                                        Toast.makeText(ProfileActivity.this,"Saved!",Toast.LENGTH_SHORT).show()
                                );
                    }

                    @Override public void onError(String requestId, ErrorInfo error) {
                        Toast.makeText(ProfileActivity.this,error.getDescription(),Toast.LENGTH_LONG).show();
                    }

                    @Override public void onProgress(String requestId, long bytes, long totalBytes) {}
                    @Override public void onReschedule(String requestId, ErrorInfo error) {}
                    @Override public void onStart(String requestId) {}
                })
                .dispatch();
    }


    private void openGallery() {
        imagePicker.launch("image/*");
    }
    private void loadProfileImage() {

        String uid = FirebaseAuth.getInstance().getUid();
        if(uid == null) return;

        FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(uid)
                .child("profileImage")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.exists()){
                            Glide.with(ProfileActivity.this)
                                    .load(snapshot.getValue(String.class))
                                    .circleCrop()
                                    .into(imgProfile);
                        }
                    }

                    @Override public void onCancelled(@NonNull DatabaseError error) {}
                });
    }

}
