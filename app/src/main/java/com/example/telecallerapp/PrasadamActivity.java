package com.example.telecallerapp;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PrasadamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prasadam);

        findViewById(R.id.btnSubmitPrasadam).setOnClickListener(v ->
                Toast.makeText(this, "Prasadam request saved (UI only)", Toast.LENGTH_SHORT).show()
        );
    }
}
