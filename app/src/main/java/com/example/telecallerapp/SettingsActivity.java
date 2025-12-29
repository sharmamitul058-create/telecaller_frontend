package com.example.telecallerapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.telecallerapp.EditProfileActivity;
import com.example.telecallerapp.R;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;


public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setupRow(R.id.changePassword, "Change Password", R.drawable.ic_lock);
        setupRow(R.id.privacySecurity, "Privacy & Security", R.drawable.ic_security);
        setupRow(R.id.notifications, "Notifications", R.drawable.ic_notifications);
        setupRow(R.id.callSettings, "Call Settings", R.drawable.ic_call);
        setupRow(R.id.languageRegion, "Language & Region", R.drawable.ic_language);



        findViewById(R.id.btnEditProfile).setOnClickListener(v ->
                startActivity(new Intent(this, EditProfileActivity.class)));

        findViewById(R.id.btnLogout).setOnClickListener(v ->
                showLogoutDialog());
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Sign Out")
                .setMessage("Are you sure you want to sign out?")
                .setPositiveButton("Yes, Sign Out", (d, w) -> finishAffinity())
                .setNegativeButton("Cancel", null)
                .show();
    }
    private void setupRow(int rowId, String title, int iconRes) {
        View row = findViewById(rowId);
        if (row == null) return;

        TextView titleView = row.findViewById(R.id.title);
        ImageView iconView = row.findViewById(R.id.icon);

        if (titleView != null) titleView.setText(title);
        if (iconView != null) iconView.setImageResource(iconRes);
    }

}


