package com.example.blackjack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String USERS_TABLE = "USERS_TABLE";
    public static final String USER_ID = "USER_ID";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_POINTS = "USER_POINTS";


    public DBHelper (@Nullable Context context) {
        super(context, "blackjack.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + USERS_TABLE + " (" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_NAME + " TEXT, " + USER_POINTS + " INTEGER)";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addToDB (User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(USER_NAME, user.getUserName());
        cv.put(USER_POINTS, user.getPoints());

        long insert = db.insert(USERS_TABLE, null, cv);
        if (insert == -1){
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<User> getList() {
        ArrayList<User> userlist = new ArrayList<>();
        String query = "SELECT * FROM " + USERS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int points = cursor.getInt(2);

                User user = new User(id, name, points);
                userlist.add(user);
            }while (cursor.moveToNext());
        }else {
           userlist = new ArrayList<>();
        }
        cursor.close();
        db.close();
        return userlist;
    }

    public boolean checkDB(String userName) {
        String queryCheck = "SELECT EXISTS(SELECT 1 FROM " + USERS_TABLE + " WHERE " + USER_NAME + " = " + "'" + userName + "'"+ " LIMIT 1)";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryCheck, null);

        if (cursor.moveToFirst()){
            // cursor.getInt(0) is 1 if column with value exists
            if (cursor.getInt(0) == 1) {
                cursor.close();
                db.close();
                return true;
            } else {
                cursor.close();
                db.close();
                return false;
            }
        }else {
            cursor.close();
            db.close();
            return false;
        }
    }

    public int getPointsFromDB (int id) {
        int points;
        String query = "SELECT " + USER_POINTS + " FROM " + USERS_TABLE + " WHERE " + USER_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                points = cursor.getInt(0);
            }while (cursor.moveToNext());
        }
        else {
            points = 0;
        }
        cursor.close();
        db.close();
        return points;
    }

    public void savePointsToDB (int points, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + USERS_TABLE + " SET " + USER_POINTS + " = " + points + " WHERE " + USER_ID + " = " + id;
        db.execSQL(query);
    }
}
