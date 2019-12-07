package com.example.sikasir.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.sikasir.R;
import com.example.sikasir.adapter.TransactionAdapter;
import com.example.sikasir.database.TransactionHelper;
import com.example.sikasir.entity.Transaction;
import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class SeeTransactionActivity extends AppCompatActivity implements LoadTransactionCallback, View.OnClickListener {
    EditText edtSearch;
    ImageButton btnSearch;
    RecyclerView recyclerView;

    TransactionHelper helper;
    TransactionAdapter adapter;
    private static final String EXTRA_STATE = "EXTRA_STATE";
    private static final String EXTRA_DATE = "EXTRA_DATE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_transaction);

        edtSearch = findViewById(R.id.et_search_transaction);
        btnSearch = findViewById(R.id.btn_search_transaction);
        recyclerView = findViewById(R.id.rv_transaction);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new TransactionAdapter(this);
        recyclerView.setAdapter(adapter);

        helper = new TransactionHelper(getApplicationContext());
        helper.open();

        btnSearch.setOnClickListener(this);

        if (savedInstanceState != null) {
            ArrayList<Transaction> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setListTransaction(list);
            }

            edtSearch.setText(savedInstanceState.getString(EXTRA_DATE));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListTransaction());
        outState.putString(EXTRA_DATE, edtSearch.getText().toString());
    }

    @Override
    public void postExecute(ArrayList<Transaction> transactions) {
        if (transactions.size() > 0) {
           adapter.setListTransaction(transactions);
        } else {
            adapter.setListTransaction(new ArrayList<Transaction>());
            Snackbar.make(recyclerView, "Tidak ada data pada tanggal " + edtSearch.getText(), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_search_transaction) {
            String dateKeySearch = edtSearch.getText().toString();
            new LoadTransactionSync(helper, this, dateKeySearch).execute();
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
}

interface LoadTransactionCallback{
    void postExecute(ArrayList<Transaction> transactions);
}
