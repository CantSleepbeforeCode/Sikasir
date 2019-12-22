package com.example.sikasir.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.sikasir.R;
import com.example.sikasir.adapter.TransactionAdapter;
import com.example.sikasir.database.TransactionHelper;
import com.example.sikasir.entity.Transaction;
import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class SeeTransactionActivity extends AppCompatActivity implements LoadTransactionCallback {
    RecyclerView recyclerView;
    Button btnDatePicker;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat simpleDateFormat;
    TransactionHelper helper;
    TransactionAdapter adapter;

    String date = "";

    private static final String EXTRA_STATE = "EXTRA_STATE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_transaction);

        recyclerView = findViewById(R.id.rv_transaction);
        btnDatePicker = findViewById(R.id.button_date_search);
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new TransactionAdapter(this);
        recyclerView.setAdapter(adapter);

        helper = new TransactionHelper(getApplicationContext());
        helper.open();

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        if (savedInstanceState != null) {
            ArrayList<Transaction> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setListTransaction(list);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListTransaction());
    }

    @Override
    public void postExecute(ArrayList<Transaction> transactions) {
        Log.d("totalData", String.valueOf(transactions.size()));
        if (transactions.size() > 0) {
           adapter.setListTransaction(transactions);
        } else {
            adapter.setListTransaction(new ArrayList<Transaction>());
            Snackbar.make(recyclerView, "Tidak ada data pada tanggal " + date, Snackbar.LENGTH_SHORT).show();
        }
    }

    private static class LoadTransactionSync extends AsyncTask<Void, Void, ArrayList<Transaction>> {
        private final WeakReference<TransactionHelper> weakTransactionHelper;
        private final WeakReference<LoadTransactionCallback> weakTransactionCallback;
        private final String date;

        private LoadTransactionSync(TransactionHelper helper, LoadTransactionCallback callback, String date) {
            weakTransactionHelper = new WeakReference<>(helper);
            weakTransactionCallback = new WeakReference<>(callback);
            this.date = date;
        }

        @Override
        protected ArrayList<Transaction> doInBackground(Void... voids) {
            return weakTransactionHelper.get().queryByDate(date);
        }

        @Override
        protected void onPostExecute(ArrayList<Transaction> transactions) {
            super.onPostExecute(transactions);
            weakTransactionCallback.get().postExecute(transactions);
        }
    }

    private void showDateDialog() {
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(i, i1, i2);

                date = simpleDateFormat.format(newDate.getTime());

                search(date);

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void search(String date) {
        new LoadTransactionSync(helper, this, date).execute();
    }
}

interface LoadTransactionCallback{
    void postExecute(ArrayList<Transaction> transactions);
}
