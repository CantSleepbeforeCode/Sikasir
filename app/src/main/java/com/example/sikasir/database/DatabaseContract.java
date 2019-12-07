package com.example.sikasir.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String TABLE_USER = "tbl_user";
    public static final String TABLE_PRODUCT = "tbl_product";
    public static final String TABLE_TRANSACTION = "tbl_transaction";
    public static final String AUTHORITY = "com.example.sikasir";
    private static final String SCHEME = "content";

    public static final Uri CONTENT_URI_USER = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_USER)
            .build();

    public static final class userColumn implements BaseColumns {
        public static String ID = "id";
        public static String USER_NAME = "user_name";
        public static String USER_LEVEL = "user_level";
        public static String PASSWORD = "password";
    }

    public static final class productColumn implements BaseColumns {
        public static String ID_PRODUCT = "id_product";
        public static String PRODUCT_CATEGORY = "product_category";
        public static String PRODUCT_NAME = "product_name";
        public static String NUMBER_OF_PRODUCT = "number_of_product";
        public static String SELLING_PRICE = "selling_price";
        public static String PURCHASE_PRICE = "purchase_price";
    }

    public static final class transactionColumn implements BaseColumns {
        public static String ID_TRANSACTION = "id_transaction";
        public static String NUMBER_OF_PRODUCT = "number_of_product";
        public static String PAYMENT = "payment";
        public static String DATE = "date";
        public static String ID_PRODUCT = "id_product";
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }
}
