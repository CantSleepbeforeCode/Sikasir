package com.example.sikasir.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.sikasir.entity.User;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.sikasir.database.DatabaseContract.TABLE_USER;
import static com.example.sikasir.database.DatabaseContract.userColumn.ID;
import static com.example.sikasir.database.DatabaseContract.userColumn.PASSWORD;
import static com.example.sikasir.database.DatabaseContract.userColumn.USER_LEVEL;
import static com.example.sikasir.database.DatabaseContract.userColumn.USER_NAME;

public class UserHelper {
    private static String DATABASE_TABLE_USER = TABLE_USER;
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public UserHelper(Context context) {
        this.context = context;
    }

    public UserHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        return this;
    }

    public ArrayList<User> query() {
        ArrayList<User> arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(DATABASE_TABLE_USER,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        User user;
        if (cursor.getCount() > 0) {
            do {
                user = new User();
                user.setId(cursor.getString(cursor.getColumnIndexOrThrow(ID)));
                user.setUsername(cursor.getString(cursor.getColumnIndexOrThrow(USER_NAME)));
                user.setUserLevel(cursor.getString(cursor.getColumnIndexOrThrow(USER_LEVEL)));
                user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(PASSWORD)));

                arrayList.add(user);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(User user) {
        open();
        ContentValues values = new ContentValues();
        values.put(ID, user.getId());
        values.put(USER_NAME, user.getUsername());
        values.put(USER_LEVEL, user.getUserLevel());
        values.put(PASSWORD, user.getPassword());
        return sqLiteDatabase.insert(DATABASE_TABLE_USER, null, values);
    }

    public long insertAdmin() {
        open();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, "siAdmin");
        values.put(USER_LEVEL, "admin");
        values.put(PASSWORD, "1234");
        return sqLiteDatabase.insert(DATABASE_TABLE_USER, null, values);
    }

    public long insertKasir() {
        open();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, "siKasir");
        values.put(USER_LEVEL, "kasir");
        values.put(PASSWORD, "1234");
        return sqLiteDatabase.insert(DATABASE_TABLE_USER, null, values);
    }

    public int update(User user) {
        ContentValues values = new ContentValues();
        values.put(ID, user.getId());
        values.put(USER_NAME, user.getUsername());
        values.put(USER_LEVEL, user.getUserLevel());
        values.put(PASSWORD, user.getPassword());
        return sqLiteDatabase.update(DATABASE_TABLE_USER, values, USER_NAME + " = '" + user.getUsername() + "';", null);
    }

    public int delete(String userId) {
        return sqLiteDatabase.delete(DATABASE_TABLE_USER, ID + " = '" + userId + "'", null);
    }

    public Cursor queryByIdProvider(String userId) {
        return sqLiteDatabase.query(DATABASE_TABLE_USER,
                null,
                ID + " = ?",
                new String[]{userId},
                null,
                null,
                null,
                null);
    }

    public Cursor queryProvider() {
        return sqLiteDatabase.query(DATABASE_TABLE_USER,
                null,
                null,
                null,
                null,
                null,
                USER_NAME + " ASC");
    }

    public long insertProvider(ContentValues values) {
        return sqLiteDatabase.insert(DATABASE_TABLE_USER, null, values);
    }

    public int deleteProvider(String id) {
        return sqLiteDatabase.delete(DATABASE_TABLE_USER, ID + " = ?", new String[]{id});
    }

    public String idUser = "";
    public String nameUser = "";
    public String userLevel = "";
    public String passwordUser = "";

    public User Authenticate(User user, Context context) {
        databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER,
                new String[]{ID, USER_NAME, USER_LEVEL, PASSWORD},
                USER_NAME + "=?",
                new String[]{user.username},
                null, null, null);
        if (cursor != null && cursor.moveToFirst()&& cursor.getCount() > 0) {
            User user1 = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));

            if (user.password.equalsIgnoreCase(user1.password)) {
                idUser = cursor.getString(0);
                nameUser = cursor.getString(1);
                userLevel = cursor.getString(2);
                passwordUser = cursor.getString(3);

                return user1;
            }
            return null;
        }

        return null;
    }

    public boolean isUsernameExists(String username, Context context) {
        databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER,
                new String[]{ID, USER_NAME, USER_LEVEL, PASSWORD},
                USER_NAME + "=?",
                new String[]{username},
                null, null, null, null);

        if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
            return true;
        }
        return false;
    }

}
