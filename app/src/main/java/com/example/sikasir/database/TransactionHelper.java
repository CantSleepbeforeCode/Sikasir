package com.example.sikasir.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.sikasir.entity.Transaction;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.sikasir.database.DatabaseContract.TABLE_TRANSACTION;
import static com.example.sikasir.database.DatabaseContract.transactionColumn.DATE;
import static com.example.sikasir.database.DatabaseContract.transactionColumn.ID_PRODUCT;
import static com.example.sikasir.database.DatabaseContract.transactionColumn.ID_TRANSACTION;
import static com.example.sikasir.database.DatabaseContract.transactionColumn.NUMBER_OF_PRODUCT;
import static com.example.sikasir.database.DatabaseContract.transactionColumn.PAYMENT;

public class TransactionHelper {
    private static String DATABASE_TABLE_TRANSACTION = TABLE_TRANSACTION;
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public TransactionHelper(Context context) {
        this.context = context;
    }

    public TransactionHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        return this;
    }

    public ArrayList<Transaction> queryById(String productId) {
        ArrayList<Transaction> arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(DATABASE_TABLE_TRANSACTION,
                null,
                ID_PRODUCT + " = ?",
                new String[]{productId},
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            Transaction transaction = new Transaction();
            transaction.setId(cursor.getString(cursor.getColumnIndexOrThrow(ID_TRANSACTION)));
            transaction.setNumberOfProduct(cursor.getString(cursor.getColumnIndexOrThrow(NUMBER_OF_PRODUCT)));
            transaction.setPayment(cursor.getString(cursor.getColumnIndexOrThrow(PAYMENT)));
            transaction.setIdProduct(cursor.getString(cursor.getColumnIndexOrThrow(ID_PRODUCT)));
            transaction.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));
            arrayList.add(transaction);
            cursor.close();
            return arrayList;
        }
        return new ArrayList<>();
    }

    public ArrayList<Transaction> queryByDate(String date) {
        ArrayList<Transaction> arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(DATABASE_TABLE_TRANSACTION,
                null,
                DATE + " = ?",
                new String[]{date},
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            Transaction transaction = new Transaction();
            transaction.setId(cursor.getString(cursor.getColumnIndexOrThrow(ID_TRANSACTION)));
            transaction.setNumberOfProduct(cursor.getString(cursor.getColumnIndexOrThrow(NUMBER_OF_PRODUCT)));
            transaction.setPayment(cursor.getString(cursor.getColumnIndexOrThrow(PAYMENT)));
            transaction.setIdProduct(cursor.getString(cursor.getColumnIndexOrThrow(ID_PRODUCT)));
            transaction.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));
            arrayList.add(transaction);
            cursor.close();
            return arrayList;
        }
        return new ArrayList<>();
    }

    public ArrayList<Transaction> query() {
        ArrayList<Transaction> arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(DATABASE_TABLE_TRANSACTION,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        Transaction transaction;
        if (cursor.getCount() > 0) {
            do {
                transaction = new Transaction();
                transaction.setId(cursor.getString(cursor.getColumnIndexOrThrow(ID_TRANSACTION)));
                transaction.setNumberOfProduct(cursor.getString(cursor.getColumnIndexOrThrow(NUMBER_OF_PRODUCT)));
                transaction.setPayment(cursor.getString(cursor.getColumnIndexOrThrow(PAYMENT)));
                transaction.setIdProduct(cursor.getString(cursor.getColumnIndexOrThrow(ID_PRODUCT)));
                transaction.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));

                arrayList.add(transaction);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(Transaction transaction) {
        open();
        ContentValues values = new ContentValues();
//        values.put(ID_TRANSACTION, transaction.getId());
        values.put(NUMBER_OF_PRODUCT, transaction.getNumberOfProduct());
        values.put(PAYMENT, transaction.getPayment());
        values.put(ID_PRODUCT, transaction.getIdProduct());
        return sqLiteDatabase.insert(DATABASE_TABLE_TRANSACTION, null, values);
    }

    public int update(Transaction transaction) {
        ContentValues values = new ContentValues();
        values.put(ID_TRANSACTION, transaction.getId());
        values.put(NUMBER_OF_PRODUCT, transaction.getNumberOfProduct());
        values.put(PAYMENT, transaction.getPayment());
        values.put(ID_PRODUCT, transaction.getIdProduct());
        return sqLiteDatabase.update(DATABASE_TABLE_TRANSACTION, values, ID_TRANSACTION + " = '" + transaction.getId() + "';", null);
    }

    public int delete(String idTransaction) {
        return sqLiteDatabase.delete(DATABASE_TABLE_TRANSACTION, ID_TRANSACTION = " = '" + idTransaction + "';", null);
    }
}
