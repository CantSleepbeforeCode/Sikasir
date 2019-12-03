package com.example.sikasir.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                Log.d("button", "tepencet");
                String id = iptIdBarang.getText().toString();
                String sum = iptIdBarang.getText().toString();
                String money = iptUangBayar.getText().toString();
                if (!id.equals("") && !sum.equals("")  && !money.equals("")) {

                } else {
                    Toast.makeText(getApplicationContext(), "Field tidak boleh ada kosong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
