package com.example.telecallerapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.telecallerapp.Models.Users;
import com.example.telecallerapp.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding binding;
    private FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        setContentView(binding.getRoot());


        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etName = findViewById(R.id.etName);
                String enteredUserName = etName.getText().toString().trim();


                SharedPreferences prefs = getSharedPreferences("USER_PREFS", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("USER_NAME", enteredUserName); //
                editor.apply();

                auth.createUserWithEmailAndPassword(binding.etEmailAddress.getText().toString(), binding.etPassword.getText().toString()).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                                                                                                                    @Override
                                                                                                                                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                                                                                                                                        if (task.isSuccessful()) {
                                                                                                                                                                            String uid = task.getResult().getUser().getUid();

                                                                                                                                                                            Users user = new Users(
                                                                                                                                                                                    binding.etName.getText().toString(),
                                                                                                                                                                                    binding.etEmailAddress.getText().toString(),
                                                                                                                                                                                    "Telecaller"
                                                                                                                                                                            );

                                                                                                                                                                            FirebaseDatabase.getInstance()
                                                                                                                                                                                    .getReference("Users")
                                                                                                                                                                                    .child(uid)
                                                                                                                                                                                    .setValue(user);

                                                                                                                                                                            Toast.makeText(SignupActivity.this,
                                                                                                                                                                                    "User Created Successfully", Toast.LENGTH_SHORT).show();
                                                                                                                                                                        }
                                                                                                                                                                        else{
                                                                                                                                                                            Toast.makeText(SignupActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                                                                                                                                        }

                                                                                                                                                                    }
                                                                                                                                                                }

                );


            }
        });









    }
}