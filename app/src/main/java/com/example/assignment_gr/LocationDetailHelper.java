package com.example.assignment_gr;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class LocationDetailHelper extends SQLiteOpenHelper {

    private static String dbname = "myDatabase";
    private static String tablename = "location";
    private static int version = 1;

    public static String id = "id";
    private static String placename = "placename";
    private static String placecountry = "placecountry";
    private static String placedesc = "placedesc";
    public LocationDetailHelper(@Nullable Context context) {
        super(context, dbname, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table "+tablename+"("+id+" integer primary key autoincrement,"+placename+" text,"+placecountry+" text,"+placedesc+" text);";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+tablename);

        onCreate(db);
    }

    public boolean addLocation(LocationDetail location) {
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = this.getWritableDatabase();

            // Insert new user
            ContentValues values = new ContentValues();
            values.put("placename", location.getPlacename());
            values.put("placecountry", location.getPlacecountry());
            values.put("placedesc", location.getPlacedesc());

            long result = db.insert(tablename, null, values);
            return result != -1; // Return true if insertion is successful

        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return false if any error occurs
        } finally {
            // Close the cursor and database to prevent memory leaks
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }

    public LocationDetail getLocationByName(String placename) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        LocationDetail LocationDetail = null;

        try {
            cursor = db.rawQuery("SELECT * FROM " + tablename + " WHERE " + placename + " = ?",
                    new String[]{String.valueOf(placename)});

            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String placename1 = cursor.getString(cursor.getColumnIndexOrThrow("placename"));
                String placecountry = cursor.getString(cursor.getColumnIndexOrThrow("placecountry"));
                String placedesc = cursor.getString(cursor.getColumnIndexOrThrow("placedesc"));

                LocationDetail = new LocationDetail(id, placename1, placecountry, placedesc); // Create User object
            }
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return LocationDetail; // Returns user if found, otherwise null
    }

}
