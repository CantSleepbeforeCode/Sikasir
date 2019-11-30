package com.example.sikasir.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sikasir.R;
import com.google.android.material.snackbar.Snackbar;

public class LandingActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txtUsername;
    Button btnSignOut, btnInputDataStok, btnInputDataTransaksi, btnUpdateData, btnSeeTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getStringExtra("KEY_USERLEVEL").equals("admin")) {
            setContentView(R.layout.activity_landing_admin);
            btnInputDataStok = findViewById(R.id.btn_input_data_stok);
            btnUpdateData = findViewById(R.id.btn_update_data);
            btnInputDataStok.setOnClickListener(this);
            btnUpdateData.setOnClickListener(this);
        } else {
            setContentView(R.layout.activity_landing_kasir);
            btnInputDataTransaksi = findViewById(R.id.btn_input_data_transaksi);
            btnInputDataTransaksi.setOnClickListener(this);
        }

        txtUsername = findViewById(R.id.txt_welcome);
        btnSeeTransaction = findViewById(R.id.btn_see_transaction);
        btnSignOut = findViewById(R.id.btn_log_out);
        btnSeeTransaction.setOnClickListener(this);
        btnSignOut.setOnClickListener(this);

        txtUsername.setText(String.format("Welcome, %s", getIntent().getStringExtra("KEY_USERNAME")));
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        String message = "Sorry, this feature is not available yet";
        int duration = Snackbar.LENGTH_LONG;
        View object;
        switch (view.getId()) {
            case R.id.btn_log_out:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_see_transaction:
//                intent = new Intent(this, SeeTransaction.class);
                object = findViewById(R.id.btn_see_transaction);
                showSnackbar(object, message, duration);
                break;
            case R.id.btn_input_data_stok:
//                intent = new Intent(this, InputStok.class);
                object = findViewById(R.id.btn_input_data_stok);
                showSnackbar(object, message, duration);
                break;
            case R.id.btn_input_data_transaksi:
//                intent = new Intent(this, InputTransaction.class);
                object = findViewById(R.id.btn_input_data_transaksi);
                showSnackbar(object, message, duration);
                break;
            case R.id.btn_update_data:
//                intent = new Intent(this, UpdateData.class);
                object = findViewById(R.id.btn_update_data);
                showSnackbar(object, message, duration);
                break;
            default:
                break;
        }
    }

    public void showSnackbar(View view, String message, int duration){
        Snackbar.make(view, message, duration).show();
    }
}
