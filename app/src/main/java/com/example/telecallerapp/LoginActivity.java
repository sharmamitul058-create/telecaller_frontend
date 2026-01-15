package com.example.telecallerapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth auth;
    EditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        TextView tvSignUp = findViewById(R.id.tvSignUp);
        Button btnLogin = findViewById(R.id.btnLogin);

        // Go to Sign Up
        tvSignUp.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, SignupActivity.class))
        );

        // Login button
        btnLogin.setOnClickListener(v -> {

            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {

                            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();

                        } else {
                            Toast.makeText(this,
                                    "Invalid email or password",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }

    // ✅ Auto-login ONLY if already authenticated
    @Override
    protected void onStart() {
        super.onStart();


        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }
}
















































































































































