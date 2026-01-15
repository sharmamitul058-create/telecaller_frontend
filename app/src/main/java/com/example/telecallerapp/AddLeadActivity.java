package com.example.telecallerapp;

import android.os.Bundle;
import android.widget.EditText;

import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddLeadActivity extends AppCompatActivity {

    EditText etName, etPhone;
    DatabaseReference leadRef;

    RadioGroup rgStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lead);

        etName = findViewById(R.id.etLeadName);
        etPhone = findViewById(R.id.etLeadPhone);
        rgStatus = findViewById(R.id.rgStatus);

        findViewById(R.id.btnSaveLead).setOnClickListener(v -> saveLead());
    }


    private void saveLead() {

        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();


        if (name.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Enter name & phone", Toast.LENGTH_SHORT).show();
            return;
        }

        String status = "Fresh";
        int selectedId = rgStatus.getCheckedRadioButtonId();
        if (selectedId == R.id.rbInterested) {
            status = "Interested";
        }

        String userId = FirebaseAuth.getInstance().getUid();
        if (userId == null) return;

        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("leads")
                .child(userId);

        String id = ref.push().getKey();

        Lead lead = new Lead(id, name, phone, status);

        ref.child(id).setValue(lead)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Lead added", Toast.LENGTH_SHORT).show();
                    finish();
                });
    }
}

