package com.example.sikasir.entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static com.example.sikasir.database.DatabaseContract.getColumnString;
import static com.example.sikasir.database.DatabaseContract.userColumn.ID;
import static com.example.sikasir.database.DatabaseContract.userColumn.PASSWORD;
import static com.example.sikasir.database.DatabaseContract.userColumn.USER_LEVEL;
import static com.example.sikasir.database.DatabaseContract.userColumn.USER_NAME;

public class User implements Parcelable {
    private String id;
    public String username, userLevel, password;

    public User(String id, String username, String userLevel, String password) {
        this.id = id;
        this.username = username;
        this.userLevel = userLevel;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User() {}

    public User(Cursor cursor) {
        this.id = getColumnString(cursor, ID);
        this.username = getColumnString(cursor, USER_NAME);
        this.userLevel = getColumnString(cursor, USER_LEVEL);
        this.password = getColumnString(cursor, PASSWORD);

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.username);
        dest.writeString(this.userLevel);
        dest.writeString(this.password);
    }

    protected User(Parcel in) {
        this.id = in.readString();
        this.username = in.readString();
        this.userLevel = in.readString();
        this.password = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
