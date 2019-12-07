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
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InputTransactionActivity extends AppCompatActivity {
    EditText iptIdBarang, iptSumBarang, iptUangBayar;
    Button btnSimpan;

    TransactionHelper helper;

    private static final String EXTRA_ID = "EXTRA_ID";
    private static final String EXTRA_SUM = "EXTRA_SUM";
    private static final String EXTRA_MONEY = "EXTRA_MONEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_transaction);

        iptIdBarang = findViewById(R.id.ipt_id_barang);
        iptSumBarang = findViewById(R.id.ipt_sum_barang);
        iptUangBayar = findViewById(R.id.ipt_uang_bayar);
        btnSimpan = findViewById(R.id.btn_simpan);

        if (savedInstanceState != null) {
            iptIdBarang.setText(savedInstanceState.getString(EXTRA_ID));
            iptSumBarang.setText(savedInstanceState.getString(EXTRA_SUM));
            iptUangBayar.setText(savedInstanceState.getString(EXTRA_MONEY));
        }

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = iptIdBarang.getText().toString();
                String sum = iptIdBarang.getText().toString();
                String money = iptUangBayar.getText().toString();

                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                String getDate = format.format(date);

                ProductHelper productHelper = new ProductHelper(getApplicationContext());
                productHelper.open();

                if (!id.equals("") && !sum.equals("")  && !money.equals("")) {
                    ArrayList<Product> isExist = productHelper.queryById(id);
                    if (isExist.size() > 0) {
                        if (isExist.get(0).getSum() > 0) {
                            if (Integer.parseInt(isExist.get(0).getSellingPrice()) < Integer.parseInt(money)) {
                                Transaction transaction = new Transaction();
                                transaction.setIdProduct(id);
                                transaction.setNumberOfProduct(sum);
                                transaction.setPayment(money);
                                transaction.setDate(getDate);

                                helper = new TransactionHelper(getApplicationContext());
                                helper.insert(transaction);

                                iptIdBarang.setText("");
                                iptSumBarang.setText("");
                                iptUangBayar.setText("");

                                Snackbar.make(btnSimpan, "Transaksi Berhasil Dimasukkan", Snackbar.LENGTH_SHORT).show();
                            } else {
                                Snackbar.make(btnSimpan, "Uang Kurang, harga barang " + isExist.get(0).getPurchasePrice(), Snackbar.LENGTH_SHORT).show();
                            }
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_ID, iptIdBarang.getText().toString());
        outState.putString(EXTRA_SUM, iptSumBarang.getText().toString());
        outState.putString(EXTRA_MONEY, iptUangBayar.getText().toString());
    }
}
