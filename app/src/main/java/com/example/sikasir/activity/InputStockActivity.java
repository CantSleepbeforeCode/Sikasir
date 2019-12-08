package com.example.sikasir.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sikasir.R;
import com.example.sikasir.database.ProductHelper;
import com.example.sikasir.entity.Product;

import java.util.ArrayList;

public class InputStockActivity extends AppCompatActivity {
    EditText iptIdBarang, iptKategoriBarang, iptNamaBarang, iptJumlahBarang, iptHargaJualBarang, iptHargaBeliBarang;
    Button btnSimpan;
    ProductHelper helper;

    private static final String EXTRA_ID = "EXTRA_ID";
    private static final String EXTRA_CATEGORY = "EXTRA_CATEGORY";
    private static final String EXTRA_NAME = "EXTRA_NAME";
    private static final String EXTRA_SUM = "EXTRA_SUM";
    private static final String EXTRA_SELLING = "EXTRA_SELLING";
    private static final String EXTRA_PURCHASE = "EXTRA_PURCHASE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_stock);

        iptIdBarang = findViewById(R.id.ipt_id_barang);
        iptKategoriBarang = findViewById(R.id.ipt_kategori_barang);
        iptNamaBarang = findViewById(R.id.ipt_nama_barang);
        iptJumlahBarang = findViewById(R.id.ipt_jumlah_barang);
        iptHargaJualBarang = findViewById(R.id.ipt_harga_jual_barang);
        iptHargaBeliBarang = findViewById(R.id.ipt_harga_beli_barang);
        btnSimpan = findViewById(R.id.btn_simpan_barang);

        if (savedInstanceState != null) {
            iptIdBarang.setText(savedInstanceState.getString(EXTRA_ID));
            iptKategoriBarang.setText(savedInstanceState.getString(EXTRA_CATEGORY));
            iptNamaBarang.setText(savedInstanceState.getString(EXTRA_NAME));
            iptJumlahBarang.setText(savedInstanceState.getString(EXTRA_SUM));
            iptHargaJualBarang.setText(savedInstanceState.getString(EXTRA_SELLING));
            iptHargaBeliBarang.setText(savedInstanceState.getString(EXTRA_PURCHASE));
        }

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = iptIdBarang.getText().toString();
                String kategori = iptKategoriBarang.getText().toString();
                String nama = iptNamaBarang.getText().toString();
                String jumlah = iptJumlahBarang.getText().toString();
                String hargaJual = iptHargaJualBarang.getText().toString();
                String hargaBeli = iptHargaBeliBarang.getText().toString();

                try {
                    if (!id.equals("") && !kategori.equals("") && !nama.equals("") && !jumlah.equals("") && !hargaJual.equals("") && !hargaBeli.equals("")) {
                        helper = new ProductHelper(getApplicationContext());
                        helper.open();
                        ArrayList<Product> cekProduct = helper.queryById(id);
                        if (cekProduct.size() == 0) {
                            Product product = new Product();
                            product.setId(id);
                            product.setCategory(kategori);
                            product.setName(nama);
                            product.setSum(Integer.parseInt(jumlah.trim()));
                            product.setSellingPrice(hargaJual);
                            product.setPurchasePrice(hargaBeli);
                            helper.insert(product);

                            Intent intent = new Intent(getApplicationContext(), LandingActivity.class);
                            intent.putExtra("KEY_USERLEVEL", "admin");
                            startActivity(intent);

                        } else {
                            Toast.makeText(getApplicationContext(), "Data dengan ID ini telah tersedia", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Field tidak boleh ada kosong", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Field jumlah harus angka", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_ID, iptIdBarang.getText().toString());
        outState.putString(EXTRA_CATEGORY, iptKategoriBarang.getText().toString());
        outState.putString(EXTRA_NAME, iptNamaBarang.getText().toString());
        outState.putString(EXTRA_SUM, iptJumlahBarang.getText().toString());
        outState.putString(EXTRA_SELLING, iptHargaJualBarang.getText().toString());
        outState.putString(EXTRA_PURCHASE, iptHargaBeliBarang.getText().toString());
    }
}
