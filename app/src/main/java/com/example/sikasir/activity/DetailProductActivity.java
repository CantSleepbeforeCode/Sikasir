package com.example.sikasir.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.sikasir.R;
import com.example.sikasir.database.ProductHelper;
import com.example.sikasir.entity.Product;

import java.util.ArrayList;

public class DetailProductActivity extends AppCompatActivity {
    TextView txtId, txtName, txtCategory, txtSum, txtIncome, txtOutcome;
    ProductHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        txtId = findViewById(R.id.tv_value_id_detail_barang);
        txtName = findViewById(R.id.tv_value_name_detail_barang);
        txtCategory = findViewById(R.id.tv_value_kategori_detail_barang);
        txtSum = findViewById(R.id.tv_value_jumlah_detail_barang);
        txtIncome = findViewById(R.id.tv_value_harga_beli_detail_barang);
        txtOutcome = findViewById(R.id.tv_value_harga_jual_detail_barang);

        final String id = getIntent().getStringExtra("EXTRA_ID_PRODUCT");

        helper = new ProductHelper(this);
        helper.open();

        final ArrayList<Product> products = helper.queryById(id);

        txtId.setText(products.get(0).getId());
        txtName.setText(products.get(0).getName());
        txtCategory.setText(products.get(0).getCategory());
        txtSum.setText(String.valueOf(products.get(0).getSum()));
        txtIncome.setText(products.get(0).getPurchasePrice());
        txtOutcome.setText(products.get(0).getSellingPrice());
    }
}
