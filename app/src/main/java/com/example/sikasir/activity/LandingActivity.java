package com.example.sikasir.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
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

        if (getIntent().getStringExtra("KEY_USERNAME") == null) {
            txtUsername.setText("Apa yang ingin anda lakukan hari ini?");
        } else {
            txtUsername.setText(String.format("Welcome, %s", getIntent().getStringExtra("KEY_USERNAME")));
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_log_out:
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
                builder.setCancelable(true);
                builder.setTitle("Konfirmasi Log Out");
                builder.setMessage("Apakah benar anda ingin Keluar?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(LandingActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.btn_see_transaction:
                intent = new Intent(this, SeeTransactionActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_input_data_stok:
                intent = new Intent(this, InputStockActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_input_data_transaksi:
                intent = new Intent(this, InputTransactionActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_update_data:
                intent = new Intent(this, SeeProductActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void showSnackbar(View view, String message, int duration){
        Snackbar.make(view, message, duration).show();
    }
}
