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

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
                String sum = iptSumBarang.getText().toString();
                String money = iptUangBayar.getText().toString();

                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                String getDate = format.format(date);
                Log.d("cekTanggal", getDate);

                ProductHelper productHelper = new ProductHelper(getApplicationContext());
                productHelper.open();

                if (!id.equals("") && !sum.equals("")  && !money.equals("")) {
                    ArrayList<Product> isExist = productHelper.queryById(id);
                    if (isExist.size() > 0) {
                        if (isExist.get(0).getSum() >= Integer.parseInt(sum)) {
                            int sumSellingPrice = Integer.parseInt(isExist.get(0).getSellingPrice()) * Integer.parseInt(sum);
                            if ( sumSellingPrice < Integer.parseInt(money)) {
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

                                int newerSum = isExist.get(0).getSum() - Integer.parseInt(sum);

                                Product product = new Product();
                                product.setId(isExist.get(0).getId());
                                product.setCategory(isExist.get(0).getCategory());
                                product.setName(isExist.get(0).getName());
                                product.setSum(newerSum);
                                product.setPurchasePrice(isExist.get(0).getPurchasePrice());
                                product.setSellingPrice(isExist.get(0).getSellingPrice());

                                productHelper.update(product, isExist.get(0).getId());

                                Snackbar.make(btnSimpan, "Transaksi Berhasil Dimasukkan", Snackbar.LENGTH_SHORT).show();
                            } else {
                                Snackbar.make(btnSimpan, "Uang Kurang, harga yang harus dibayar " + formatRupiah(sumSellingPrice), Snackbar.LENGTH_SHORT).show();
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

    private String formatRupiah(double currency) {
        Locale locale = new Locale("in", "ID");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        return format.format(currency);
    }
}
