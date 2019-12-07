package com.example.sikasir.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sikasir.R;
import com.example.sikasir.database.ProductHelper;
import com.example.sikasir.entity.Product;
import com.example.sikasir.entity.Transaction;

import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {
    private final ArrayList<Transaction> listTransaction = new ArrayList<>();
    private final Activity activity;

    public TransactionAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<Transaction> getListTransaction() {
        return listTransaction;
    }

    public void setListTransaction(ArrayList<Transaction> listTransaction) {
        if (listTransaction.size() > 0) {
            this.listTransaction.clear();
        }
        this.listTransaction.addAll(listTransaction);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        ProductHelper helper = new ProductHelper(activity);
        ArrayList<Product> product = helper.queryById(listTransaction.get(position).getIdProduct());
        String productName = product.get(0).getName();
        holder.tvTransaction.setText(String.format("%s %s %s", listTransaction.get(position).getDate(),
                productName,
                listTransaction.get(position).getIdProduct()));
        holder.baseTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("berhasil", "yeay");
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTransaction.size();
    }

    class TransactionViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTransaction;
        final LinearLayout baseTransaction;

        TransactionViewHolder(View view) {
            super(view);
            tvTransaction = view.findViewById(R.id.tv_transaction_info);
            baseTransaction = view.findViewById(R.id.base_transaction_info);
        }
    }

}
