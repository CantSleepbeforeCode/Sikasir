package com.example.sikasir.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sikasir.R;
import com.example.sikasir.database.ProductHelper;
import com.example.sikasir.database.TransactionHelper;
import com.example.sikasir.entity.Product;
import com.example.sikasir.entity.Transaction;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class DetailTransactionActivity extends AppCompatActivity {
    TextView txtHeader, txtTitle, txtName, txtSum, txtIncome, txtOutcome;
    Button btnPrint;

    private TransactionHelper helper;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaction);

        String idTransaction = getIntent().getStringExtra("EXTRA_ID");

        txtHeader = findViewById(R.id.base_header_detail_transaction);
        txtTitle = findViewById(R.id.title_transaction);
        txtName = findViewById(R.id.base_name_detail_transaction);
        txtSum = findViewById(R.id.base_sum_detail_transaction);
        txtIncome = findViewById(R.id.base_income_transaction);
        txtOutcome = findViewById(R.id.base_outcome_transaction);

        btnPrint = findViewById(R.id.btn_print_transaction);

        helper = new TransactionHelper(this);
        helper.open();

        ArrayList<Transaction> transactions = helper.queryById(idTransaction);

        ProductHelper productHelper = new ProductHelper(this);
        productHelper.open();
        ArrayList<Product> products = productHelper.queryById(transactions.get(0).getIdProduct());

        int outcome = Integer.parseInt(transactions.get(0).getPayment()) - (Integer.parseInt(products.get(0).getSellingPrice()) * Integer.parseInt(transactions.get(0).getNumberOfProduct()));

        txtHeader.setText(txtHeader.getText() + transactions.get(0).getDate());
        txtTitle.setText(String.format("%s %s %s", transactions.get(0).getDate(), products.get(0).getName(), transactions.get(0).getIdProduct()));
        txtName.setText(txtName.getText() + " " + products.get(0).getName());
        txtSum.setText(txtSum.getText() + " " + transactions.get(0).getNumberOfProduct());
        txtIncome.setText(txtIncome.getText() + " " + formatRupiah(Double.valueOf(transactions.get(0).getPayment())));
        txtOutcome.setText(txtOutcome.getText() + " " + formatRupiah(outcome));

        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private String formatRupiah(double currency) {
        Locale locale = new Locale("in", "ID");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        return format.format(currency);
    }
}
