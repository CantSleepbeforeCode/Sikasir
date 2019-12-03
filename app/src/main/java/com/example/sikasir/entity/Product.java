package com.example.sikasir.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    public String id, category, name, sum, sellingPrice, purchasePrice;

    public Product(String id, String category, String name, String sum, String sellingPrice, String purchasePrice) {
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

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.category);
        dest.writeString(this.name);
        dest.writeString(this.sum);
        dest.writeString(this.sellingPrice);
        dest.writeString(this.purchasePrice);
    }

    protected Product(Parcel in) {
        this.id = in.readString();
        this.category = in.readString();
        this.name = in.readString();
        this.sum = in.readString();
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
