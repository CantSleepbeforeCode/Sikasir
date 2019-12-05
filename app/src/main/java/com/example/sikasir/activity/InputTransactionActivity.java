package com.example.sikasir.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sikasir.R;
import com.example.sikasir.database.ProductHelper;
import com.example.sikasir.database.TransactionHelper;
import com.example.sikasir.entity.Product;
import com.example.sikasir.entity.Transaction;

import java.util.ArrayList;

public class InputTransactionActivity extends AppCompatActivity {
    EditText iptIdBarang, iptSumBarang, iptUangBayar;
    Button btnSimpan;

    TransactionHelper helper;
    ProductHelper productHelper;

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
                String id = iptIdBarang.getText().toString();
                String sum = iptIdBarang.getText().toString();
                String money = iptUangBayar.getText().toString();
                if (!id.equals("") && !sum.equals("")  && !money.equals("")) {
                    ArrayList<Product> isExist = productHelper.queryById(id);
                    if (isExist.size() > 0) {
                        if (isExist.get(0).getSum() > 0) {
                            Transaction transaction = new Transaction();
                            transaction.setIdProduct(id);
                            transaction.setNumberOfProduct(sum);
                            transaction.setPayment(money);
                            helper.insert(transaction);
                        } else {
                            Toast.makeText(getApplicationContext(), "Data dengan ID " + id + " Habis", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Data dengan ID " + id + " Tidak Tersedia", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Field tidak boleh ada kosong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
