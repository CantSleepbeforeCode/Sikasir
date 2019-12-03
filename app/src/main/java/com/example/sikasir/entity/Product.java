package com.example.sikasir.entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static com.example.sikasir.database.DatabaseContract.getColumnInt;
import static com.example.sikasir.database.DatabaseContract.getColumnString;
import static com.example.sikasir.database.DatabaseContract.productColumn.ID_PRODUCT;
import static com.example.sikasir.database.DatabaseContract.productColumn.NUMBER_OF_PRODUCT;
import static com.example.sikasir.database.DatabaseContract.productColumn.PRODUCT_CATEGORY;
import static com.example.sikasir.database.DatabaseContract.productColumn.PRODUCT_NAME;
import static com.example.sikasir.database.DatabaseContract.productColumn.PURCHASE_PRICE;
import static com.example.sikasir.database.DatabaseContract.productColumn.SELLING_PRICE;

public class Product implements Parcelable {
    public String id, category, name, sellingPrice, purchasePrice;
    public int sum;

    public Product(String id, String category, String name, int sum, String sellingPrice, String purchasePrice) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.sum = sum;
        this.sellingPrice = sellingPrice;
        this.purchasePrice = purchasePrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Product() {}

    public Product(Cursor cursor) {
        this.id = getColumnString(cursor, ID_PRODUCT);
        this.category = getColumnString(cursor, PRODUCT_CATEGORY);
        this.name = getColumnString(cursor, PRODUCT_NAME);
        this.sum = getColumnInt(cursor, NUMBER_OF_PRODUCT);
        this.sellingPrice = getColumnString(cursor, SELLING_PRICE);
        this.purchasePrice = getColumnString(cursor, PURCHASE_PRICE);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.category);
        dest.writeString(this.name);
        dest.writeInt(this.sum);
        dest.writeString(this.sellingPrice);
        dest.writeString(this.purchasePrice);
    }

    protected Product(Parcel in) {
        this.id = in.readString();
        this.category = in.readString();
        this.name = in.readString();
        this.sum = in.readInt();
        this.sellingPrice = in.readString();
        this.purchasePrice = in.readString();
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
