package com.example.sikasir.util;

import android.view.View;

public class CustomOnClickListener implements View.OnClickListener {
    private final int position;
    private final OnItemCallback onItemCallback;

    public CustomOnClickListener(int position, OnItemCallback onItemCallback) {
        this.position = position;
        this.onItemCallback = onItemCallback;
    }

    @Override
    public void onClick(View view) {
        onItemCallback.onItemClicked(view, position);
    }

    public interface OnItemCallback {
        void onItemClicked(View view, int position);
    }
}
