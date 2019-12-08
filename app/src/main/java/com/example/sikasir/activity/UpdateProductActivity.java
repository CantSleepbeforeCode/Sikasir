package com.example.sikasir.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sikasir.R;
import com.example.sikasir.database.ProductHelper;
import com.example.sikasir.entity.Product;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class UpdateProductActivity extends AppCompatActivity {
    EditText iptIdBarang, iptKategoriBarang, iptNamaBarang, iptJumlahBarang, iptHargaJualBarang, iptHargaBeliBarang;
    Button btnUbah;

    ProductHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        final String oldId = getIntent().getStringExtra("EXTRA_ID_PRODUCT");

        iptIdBarang = findViewById(R.id.ipt_id_update_barang);
        iptKategoriBarang = findViewById(R.id.ipt_kategori_update_barang);
        iptNamaBarang = findViewById(R.id.ipt_nama_update_barang);
        iptJumlahBarang = findViewById(R.id.ipt_jumlah_update_barang);
        iptHargaJualBarang = findViewById(R.id.ipt_harga_jual_update_barang);
        iptHargaBeliBarang = findViewById(R.id.ipt_harga_beli_update_barang);
        btnUbah = findViewById(R.id.btn_ubah_barang);

        helper = new ProductHelper(this);
        helper.open();

        final ArrayList<Product> products = helper.queryById(oldId);

        iptIdBarang.setText(products.get(0).getId());
        iptKategoriBarang.setText(products.get(0).getCategory());
        iptNamaBarang.setText(products.get(0).getName());
        iptJumlahBarang.setText(String.valueOf(products.get(0).getSum()));
        iptHargaJualBarang.setText(products.get(0).getSellingPrice());
        iptHargaBeliBarang.setText(products.get(0).getPurchasePrice());

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Product> checkId = helper.queryById(iptIdBarang.getText().toString());
                Product updateProduct = new Product();
                if (iptIdBarang.getText().toString().equals(oldId)) {
                    updateProduct.setId(iptIdBarang.getText().toString());
                    updateProduct.setCategory(iptKategoriBarang.getText().toString());
                    updateProduct.setName(iptNamaBarang.getText().toString());
                    updateProduct.setSum(Integer.parseInt(iptJumlahBarang.getText().toString()));
                    updateProduct.setPurchasePrice(iptHargaBeliBarang.getText().toString());
                    updateProduct.setSellingPrice(iptHargaJualBarang.getText().toString());

                    helper.update(updateProduct, oldId);

                    Snackbar.make(btnUbah, "Data Berhasil Diubah", Snackbar.LENGTH_SHORT).show();

                    Intent intent = new Intent(UpdateProductActivity.this, SeeProductActivity.class);
                    startActivity(intent);
                } else {
                    if (checkId.size() > 0) {
                        Snackbar.make(btnUbah, "Data dengan ID " + iptIdBarang.getText().toString() + " Telah tersedia", Snackbar.LENGTH_SHORT).show();
                    } else {
                        updateProduct.setId(iptIdBarang.getText().toString());
                        updateProduct.setCategory(iptKategoriBarang.getText().toString());
                        updateProduct.setName(iptNamaBarang.getText().toString());
                        updateProduct.setSum(Integer.parseInt(iptJumlahBarang.getText().toString()));
                        updateProduct.setPurchasePrice(iptHargaBeliBarang.getText().toString());
                        updateProduct.setSellingPrice(iptHargaJualBarang.getText().toString());

                        helper.update(updateProduct, oldId);

                        Snackbar.make(btnUbah, "Data Berhasil Diubah", Snackbar.LENGTH_SHORT).show();

                        Intent intent = new Intent(UpdateProductActivity.this, SeeProductActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });


    }
}
