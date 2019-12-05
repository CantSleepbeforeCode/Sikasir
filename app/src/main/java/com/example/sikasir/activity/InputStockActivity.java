package com.example.sikasir.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sikasir.R;
import com.example.sikasir.database.ProductHelper;
import com.example.sikasir.entity.Product;

public class InputStockActivity extends AppCompatActivity {
    EditText iptIdBarang, iptKategoriBarang, iptNamaBarang, iptJumlahBarang, iptHargaJualBarang, iptHargaBeliBarang;
    Button btnSimpan;
    ProductHelper helper;

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
                        Product product = new Product();
                        product.setId(id);
                        product.setCategory(kategori);
                        product.setName(nama);
                        product.setSum(Integer.parseInt(jumlah.trim()));
                        product.setSellingPrice(hargaJual);
                        product.setPurchasePrice(hargaBeli);
                        helper.insert(product);
                    } else {
                        Toast.makeText(getApplicationContext(), "Field tidak boleh ada kosong", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Field jumlah harus angka", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
