package com.example.sikasir.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.sikasir.R;
import com.example.sikasir.adapter.ProductAdapter;
import com.example.sikasir.database.ProductHelper;
import com.example.sikasir.entity.Product;
import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class SeeProductActivity extends AppCompatActivity implements LoadDataCallback {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private static final String EXTRA_STATE = "EXTRA_STATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_barang);

        recyclerView = findViewById(R.id.rv_product);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new ProductAdapter(this);
        recyclerView.setAdapter(adapter);

        ProductHelper helper = new ProductHelper(getApplicationContext());
        helper.open();

        if (savedInstanceState == null) {
            new LoadProductAsync(helper, this).execute();
        } else {
            ArrayList<Product> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setListProduct(list);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListProduct());
    }

    @Override
    public void postExecute(ArrayList<Product> products) {
        if (products.size() > 0) {
            adapter.setListProduct(products);
        } else {
            adapter.setListProduct(new ArrayList<Product>());
            Snackbar.make(recyclerView, "Tidak ada data saat ini", Snackbar.LENGTH_SHORT).show();
        }
    }

    private static class LoadProductAsync extends AsyncTask<Void, Void, ArrayList<Product>> {
        private final WeakReference<ProductHelper> weakProductHelper;
        private final WeakReference<LoadDataCallback> weakCallback;

        private LoadProductAsync(ProductHelper helper, LoadDataCallback callback) {
            weakProductHelper = new WeakReference<>(helper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected ArrayList<Product> doInBackground(Void... voids) {
            return weakProductHelper.get().query();
        }

        @Override
        protected void onPostExecute(ArrayList<Product> products) {
            super.onPostExecute(products);

            weakCallback.get().postExecute(products);
        }
    }
}

interface LoadDataCallback{
    void postExecute(ArrayList<Product> products);
}