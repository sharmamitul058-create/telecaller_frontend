package com.example.telecallerapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class DonationActivity extends AppCompatActivity {

    private static final String TAG = "DONATION_DEBUG";

    EditText etName, etPhone, etLocation, etAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etLocation = findViewById(R.id.etLocation);
        etAmount = findViewById(R.id.etAmount);

        findViewById(R.id.btnSubmitDonation).setOnClickListener(v -> {
            Log.d(TAG, "Submit clicked");
            saveDonation();
        });
    }

    private void saveDonation() {


        try {
            String name = etName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String location = etLocation.getText().toString().trim();
            String amount = etAmount.getText().toString().trim();

            if (name.isEmpty() || phone.isEmpty() || amount.isEmpty()) {
                Toast.makeText(this, "Fill all required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            String userId = FirebaseAuth.getInstance().getUid();
            if (userId == null) {
                Toast.makeText(this, "Session expired, login again", Toast.LENGTH_LONG).show();
                return;
            }

            Donor donor = new Donor(
                    null,
                    name,
                    phone,
                    location,
                    amount,
                    userId
            );

            FirebaseDatabase.getInstance()
                    .getReference("donors")
                    .child(userId)
                    .push()
                    .setValue(donor)
                    .addOnSuccessListener(unused ->
                            Toast.makeText(this, "Donation saved", Toast.LENGTH_LONG).show())
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Firebase error: " + e.getMessage(), Toast.LENGTH_LONG).show());

        } catch (Exception e) {
            Toast.makeText(this, "Crash: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}