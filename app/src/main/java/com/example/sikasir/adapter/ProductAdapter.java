package com.example.sikasir.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sikasir.R;
import com.example.sikasir.activity.UpdateDataActivity;
import com.example.sikasir.database.ProductHelper;
import com.example.sikasir.entity.Product;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private final ArrayList<Product> listProduct = new ArrayList<>();
    private final Activity activity;

    public ProductAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<Product> getListProduct() {
        return listProduct;
    }

    public void setListProduct(ArrayList<Product> listProduct) {
        if (listProduct.size() > 0) {
            this.listProduct.clear();
        }
        this.listProduct.addAll(listProduct);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, final int position) {
        holder.tvProduct.setText(String.format("%s %s", listProduct.get(position).getId(), listProduct.get(position).getName()));
        holder.cvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductHelper helper = new ProductHelper(activity.getApplicationContext());
                helper.delete(listProduct.get(position).getId());
            }
        });

        holder.cvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, UpdateDataActivity.class);
                intent.putExtra(UpdateDataActivity.EXTRA_ID_PRODUCT, listProduct.get(position));
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        final TextView tvProduct;
        final CardView cvDelete, cvUpdate;

        ProductViewHolder(View view) {
            super(view);
            tvProduct = view.findViewById(R.id.txt_product);
            cvDelete = view.findViewById(R.id.cv_delete_product);
            cvUpdate = view.findViewById(R.id.cv_update_product);
        }
    }
}
