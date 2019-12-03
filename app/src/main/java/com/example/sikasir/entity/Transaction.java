package com.example.sikasir.entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static com.example.sikasir.database.DatabaseContract.getColumnString;
import static com.example.sikasir.database.DatabaseContract.transactionColumn.ID_PRODUCT;
import static com.example.sikasir.database.DatabaseContract.transactionColumn.ID_TRANSACTION;
import static com.example.sikasir.database.DatabaseContract.transactionColumn.NUMBER_OF_PRODUCT;
import static com.example.sikasir.database.DatabaseContract.transactionColumn.PAYMENT;

public class Transaction implements Parcelable {
    private String id, numberOfProduct, payment, idProduct;

    public Transaction(String id, String numberOfProduct, String payment, String idProduct) {
        this.id = id;
        this.numberOfProduct = numberOfProduct;
        this.payment = payment;
        this.idProduct = idProduct;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumberOfProduct() {
        return numberOfProduct;
    }

    public void setNumberOfProduct(String numberOfProduct) {
        this.numberOfProduct = numberOfProduct;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public Transaction() {}

    public Transaction(Cursor cursor) {
        this.id = getColumnString(cursor, ID_TRANSACTION);
        this.numberOfProduct = getColumnString(cursor, NUMBER_OF_PRODUCT);
        this.payment = getColumnString(cursor, PAYMENT);
        this.idProduct = getColumnString(cursor, ID_PRODUCT);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.numberOfProduct);
        dest.writeString(this.payment);
        dest.writeString(this.idProduct);
    }

    protected Transaction(Parcel in) {
        this.id = in.readString();
        this.numberOfProduct = in.readString();
        this.payment = in.readString();
        this.idProduct = in.readString();
    }

    public static final Parcelable.Creator<Transaction> CREATOR = new Parcelable.Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel source) {
            return new Transaction(source);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };
}
