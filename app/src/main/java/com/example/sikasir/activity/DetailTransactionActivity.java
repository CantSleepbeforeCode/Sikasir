package com.example.sikasir.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sikasir.R;
import com.example.sikasir.database.ProductHelper;
import com.example.sikasir.database.TransactionHelper;
import com.example.sikasir.entity.Product;
import com.example.sikasir.entity.Transaction;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

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

        final ArrayList<Transaction> transactions = helper.queryById(idTransaction);

        final ProductHelper productHelper = new ProductHelper(this);
        productHelper.open();
        final ArrayList<Product> products = productHelper.queryById(transactions.get(0).getIdProduct());

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
                String fileName = "Transaksi_" + products.get(0).getId() + "_" + products.get(0).getName() + ".xls";

                File sdCard = Environment.getExternalStorageDirectory();
                File directory = new File(sdCard.getAbsolutePath() + "/transaction");

                if (!directory.isDirectory()) {
                    directory.mkdirs();
                }

                File file = new File(directory, fileName);

                WorkbookSettings workbookSettings = new WorkbookSettings();
                workbookSettings.setLocale(new Locale("en", "EN"));
                WritableWorkbook workbook;

                try {
                    workbook = Workbook.createWorkbook(file, workbookSettings);
                    WritableSheet sheet = workbook.createSheet("Transaction", 0);

                    try {
                        sheet.addCell(new Label(0, 0, "name"));
                        sheet.addCell(new Label(1, 0, "sum"));
                        sheet.addCell(new Label(2, 0, "income"));
                        sheet.addCell(new Label(3, 0, "outcome"));

                        sheet.addCell(new Label(0, 1, products.get(0).getName()));
                        sheet.addCell(new Label(1, 1, transactions.get(0).getNumberOfProduct()));
                        sheet.addCell(new Label(2, 1, transactions.get(0).getPayment()));
                        sheet.addCell(new Label(3, 1, txtOutcome.getText().toString()));

                    } catch (WriteException e) {
                        e.printStackTrace();
                    }
                    workbook.write();
                    try {
                        workbook.close();
                    } catch (WriteException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Snackbar.make(btnPrint, "Transaksi Berhasil di print", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private String formatRupiah(double currency) {
        Locale locale = new Locale("in", "ID");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        return format.format(currency);
    }
}
