package com.example.sikasir.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sikasir.R;
import com.example.sikasir.database.UserHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnAdmin, btnKasir;
    UserHelper userHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdmin = findViewById(R.id.btn_admin);
        btnKasir = findViewById(R.id.btn_kasir);

        btnAdmin.setOnClickListener(this);
        btnKasir.setOnClickListener(this);

        userHelper = new UserHelper(this);
        if (!userHelper.isUsernameExists("siAdmin", getApplicationContext())) {
            userHelper.insertAdmin();
        }

        if (!userHelper.isUsernameExists("siKasir", getApplicationContext())) {
            userHelper.insertKasir();
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        switch (view.getId()) {
            case R.id.btn_admin:
                intent.putExtra(LoginActivity.EXTRA_USER, "admin");
                startActivity(intent);
                break;
            case R.id.btn_kasir:
                intent.putExtra(LoginActivity.EXTRA_USER, "kasir");
                startActivity(intent);
                break;

        }
    }
}
