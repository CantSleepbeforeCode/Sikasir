package com.example.sikasir.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.sikasir.entity.Product;

import java.util.ArrayList;

import static com.example.sikasir.database.DatabaseContract.TABLE_PRODUCT;
import static com.example.sikasir.database.DatabaseContract.productColumn.ID_PRODUCT;
import static com.example.sikasir.database.DatabaseContract.productColumn.NUMBER_OF_PRODUCT;
import static com.example.sikasir.database.DatabaseContract.productColumn.PRODUCT_CATEGORY;
import static com.example.sikasir.database.DatabaseContract.productColumn.PRODUCT_NAME;
import static com.example.sikasir.database.DatabaseContract.productColumn.PURCHASE_PRICE;
import static com.example.sikasir.database.DatabaseContract.productColumn.SELLING_PRICE;

public class ProductHelper {
    private static String DATABASE_TABLE_PRODUCT  = TABLE_PRODUCT;
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public ProductHelper(Context context) {
        this.context = context;
    }

    public ProductHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        return this;
    }

    public ArrayList<Product> queryById(String productId) {
        ArrayList<Product> arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(DATABASE_TABLE_PRODUCT,
                null,
                ID_PRODUCT + " = ?",
                new String[]{productId},
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            Product product = new Product();
            product.setId(cursor.getString(cursor.getColumnIndexOrThrow(ID_PRODUCT)));
            product.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_CATEGORY)));
            product.setName(cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_NAME)));
            product.setSum(cursor.getInt(cursor.getColumnIndexOrThrow(NUMBER_OF_PRODUCT)));
            product.setSellingPrice(cursor.getString(cursor.getColumnIndexOrThrow(SELLING_PRICE)));
            product.setPurchasePrice(cursor.getString(cursor.getColumnIndexOrThrow(PURCHASE_PRICE)));
            arrayList.add(product);
            cursor.close();
            return arrayList;
        }
        return new  ArrayList<>();
    }

    public ArrayList<Product> query() {
        ArrayList<Product> arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(DATABASE_TABLE_PRODUCT,
                null,
                null,
                null,
                null,
                null,
                ID_PRODUCT + " ASC",
                null);
        cursor.moveToFirst();
        Product product;
        if (cursor.getCount() > 0) {
            do {
                product = new Product();
                product.setId(cursor.getString(cursor.getColumnIndexOrThrow(ID_PRODUCT)));
                product.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_CATEGORY)));
                product.setName(cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_NAME)));
                product.setSum(cursor.getInt(cursor.getColumnIndexOrThrow(NUMBER_OF_PRODUCT)));
                product.setSellingPrice(cursor.getString(cursor.getColumnIndexOrThrow(SELLING_PRICE)));
                product.setPurchasePrice(cursor.getString(cursor.getColumnIndexOrThrow(PURCHASE_PRICE)));

                arrayList.add(product);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(Product product) {
        open();
        ContentValues values = new ContentValues();
        values.put(ID_PRODUCT, product.getId());
        values.put(PRODUCT_CATEGORY, product.getCategory());
        values.put(PRODUCT_NAME, product.getName());
        values.put(NUMBER_OF_PRODUCT, product.getSum());
        values.put(SELLING_PRICE, product.getSellingPrice());
        values.put(PURCHASE_PRICE, product.getPurchasePrice());
        return sqLiteDatabase.insert(DATABASE_TABLE_PRODUCT, null, values);
    }

    public int update(Product product) {
        ContentValues values = new ContentValues();
        values.put(ID_PRODUCT, product.getId());
        values.put(PRODUCT_CATEGORY, product.getCategory());
        values.put(PRODUCT_NAME, product.getName());
        values.put(NUMBER_OF_PRODUCT, product.getSum());
        values.put(SELLING_PRICE, product.getSellingPrice());
        values.put(PURCHASE_PRICE, product.getPurchasePrice());
        return sqLiteDatabase.update(DATABASE_TABLE_PRODUCT, values, ID_PRODUCT + " = '" + product.getId() + "';", null);
    }

    public int delete(String idProduct) {
        return sqLiteDatabase.delete(DATABASE_TABLE_PRODUCT, ID_PRODUCT + " = '" + idProduct + "';", null);
    }
}
