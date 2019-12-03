package com.example.sikasir.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sikasir.R;

public class InputStockActivity extends AppCompatActivity {
    EditText iptIdBarang, iptKategoriBarang, iptNamaBarang, iptJumlahBarang, iptHargaJualBarang, iptHargaBeliBarang;
    Button btnSimpan;

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
                if (!id.equals("") && !kategori.equals("") && !nama.equals("") && !jumlah.equals("") && !hargaJual.equals("") && !hargaBeli.equals("")) {
                    
                } else {
                    Toast.makeText(getApplicationContext(), "Field tidak boleh ada kosong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
