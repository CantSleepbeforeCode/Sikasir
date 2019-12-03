package com.example.sikasir.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import static android.provider.BaseColumns._ID;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "SIKASIR_DATABASE";
    public static final int DATABASE_VERSION = 1;

    public static final String SQL_TABLE_PRODUCT = String.format("CREATE TABLE %s"
            + " (%s TEXT NOT NULL PRIMARY KEY," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s INTEGER," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_PRODUCT,
            BaseColumns._ID,
            DatabaseContract.productColumn.ID_PRODUCT,
            DatabaseContract.productColumn.PRODUCT_CATEGORY,
            DatabaseContract.productColumn.PRODUCT_NAME,
            DatabaseContract.productColumn.NUMBER_OF_PRODUCT,
            DatabaseContract.productColumn.SELLING_PRICE,
            DatabaseContract.productColumn.PURCHASE_PRICE);

    public static final String SQL_TABLE_TRANSACTION = String.format("CREATE TABLE %s"
            + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " FOREIGN KEY(%s) REFERENCES %s(%s))",
            DatabaseContract.TABLE_TRANSACTION,
            BaseColumns._ID,
            DatabaseContract.transactionColumn.ID_TRANSACTION,
            DatabaseContract.transactionColumn.NUMBER_OF_PRODUCT,
            DatabaseContract.transactionColumn.PAYMENT,
            DatabaseContract.transactionColumn.ID_PRODUCT,
            DatabaseContract.transactionColumn.ID_PRODUCT,
            DatabaseContract.TABLE_PRODUCT,
            DatabaseContract.productColumn.ID_PRODUCT);

    public static final String SQL_TABLE_USER = String.format("CREATE TABLE %s"
            + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_USER,
            BaseColumns._ID,
            DatabaseContract.userColumn.ID,
            DatabaseContract.userColumn.USER_NAME,
            DatabaseContract.userColumn.USER_LEVEL,
            DatabaseContract.userColumn.PASSWORD);

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_TABLE_USER);
        sqLiteDatabase.execSQL(SQL_TABLE_PRODUCT);
        sqLiteDatabase.execSQL(SQL_TABLE_TRANSACTION);
        Log.d("cekTable", "Berhasil");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_USER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_PRODUCT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_TRANSACTION);
        onCreate(sqLiteDatabase);
    }
}
