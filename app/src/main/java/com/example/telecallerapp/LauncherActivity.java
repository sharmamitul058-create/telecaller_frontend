 package com.example.telecallerapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

    public class LauncherActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                startActivity(new Intent(this, LoginActivity.class));
            } else {
                startActivity(new Intent(this, MainActivity.class));
            }

            finish(); // VERY IMPORTANT
        }
    }

