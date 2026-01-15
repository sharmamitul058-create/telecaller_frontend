package com.example.telecallerapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.telecallerapp.Models.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.bumptech.glide.Glide;



public class EditProfileActivity extends AppCompatActivity{
ImageView imgProfile;
        EditText etName, etEmail, etPhone;
Button btnSave;

Uri imageUri;
DatabaseReference userRef;
StorageReference storageRef;
String uid;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_profile);

    imgProfile = findViewById(R.id.imgProfile);
    etName = findViewById(R.id.etName);
    etEmail = findViewById(R.id.etEmail);
    etPhone = findViewById(R.id.etPhone);
    btnSave = findViewById(R.id.btnSave);
    uid = FirebaseAuth.getInstance().getUid();
    if (uid == null) {
        return;
    }

    userRef = FirebaseDatabase.getInstance()
            .getReference("Users")
            .child(uid);

    storageRef = FirebaseStorage.getInstance()
            .getReference("profile_images")
            .child(uid + ".jpg");

    loadProfile();

    imgProfile.setOnClickListener(v -> pickImage());

    btnSave.setOnClickListener(v -> saveProfile());
}

private void loadProfile() {
    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            Users user = snapshot.getValue(Users.class);
            if (user == null) return;

            etName.setText(user.getname());
            etEmail.setText(user.getMail());



            if (user.getProfilepic() != null) {
                Glide.with(EditProfileActivity.this)
                        .load(user.getProfilepic())
                        .placeholder(R.drawable.ic_person)
                        .into(imgProfile);
            }
        }

        @Override public void onCancelled(@NonNull DatabaseError error) {}
    });
}

private void pickImage() {
    Intent intent = new Intent(Intent.ACTION_PICK);
    intent.setType("image/*");
    startActivityForResult(intent, 101);
}

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 101 && resultCode == RESULT_OK && data != null) {
        imageUri = data.getData();
        imgProfile.setImageURI(imageUri);
    }
}

private void saveProfile() {
    userRef.child("name").setValue(etName.getText().toString());
    userRef.child("phone").setValue(etPhone.getText().toString());

    if (imageUri != null) {
        storageRef.putFile(imageUri)
                .addOnSuccessListener(task ->
                        storageRef.getDownloadUrl()
                                .addOnSuccessListener(uri ->
                                        userRef.child("profileImageUrl")
                                                .setValue(uri.toString())
                                ));
    }

    Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show();
}
}
