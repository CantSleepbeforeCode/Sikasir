package com.example.sikasir.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sikasir.R;

public class InputTransactionActivity extends AppCompatActivity {
    EditText iptIdBarang, iptSumBarang, iptUangBayar;
    Button btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_transaction);

        iptIdBarang = findViewById(R.id.ipt_id_barang);
        iptSumBarang = findViewById(R.id.ipt_sum_barang);
        iptUangBayar = findViewById(R.id.ipt_uang_bayar);
        btnSimpan = findViewById(R.id.btn_simpan);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iptIdBarang.getText() !=  null) {
                    String id = iptIdBarang.getText().toString();
                    if (iptSumBarang != null) {
                        String sum = iptIdBarang.getText().toString();
                        if (iptUangBayar != null) {

                        } else {
                            iptUangBayar.setError(getString(R.string.error_blank_field));
                        }
                    } else {
                        iptSumBarang.setError(getString(R.string.error_blank_field));
                    }
                } else {
                    iptIdBarang.setError(getString(R.string.error_blank_field));
                }
            }
        });
    }
}
