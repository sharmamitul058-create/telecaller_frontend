package com.example.telecallerapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class PrasadamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prasadam)
        ;
        EditText etDateOfDonation = findViewById(R.id.etDateOfDonation);

        etDateOfDonation.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String date = String.format(
                                "%02d/%02d/%04d",
                                selectedDay,
                                selectedMonth + 1,
                                selectedYear
                        );
                        etDateOfDonation.setText(date);
                    },
                    year, month, day
            );

            datePickerDialog.show();
        });


        findViewById(R.id.btnSubmitPrasadam).setOnClickListener(v ->
                Toast.makeText(this, "Prasadam request saved (UI only)", Toast.LENGTH_SHORT).show()
        );
    }
}
