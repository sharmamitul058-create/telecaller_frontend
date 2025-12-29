package com.example.telecallerapp;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DonationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);

        findViewById(R.id.btnSubmitDonation).setOnClickListener(v ->
                Toast.makeText(this, "Donation recorded (UI only)", Toast.LENGTH_SHORT).show()
        );
    }
}
