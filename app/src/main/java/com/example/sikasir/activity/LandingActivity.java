package com.example.sikasir.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.sikasir.R;

public class LandingActivity extends AppCompatActivity {
    TextView txtUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        txtUsername = findViewById(R.id.txt_welcome);

        txtUsername.setText(String.format("Selamat datang, %s", getIntent().getStringExtra("KEY_USERNAME")));
    }
}
