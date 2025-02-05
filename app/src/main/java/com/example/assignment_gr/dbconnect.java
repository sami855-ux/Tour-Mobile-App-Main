package com.example.assignment_gr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbconnect extends SQLiteOpenHelper {

    private static String dbname = "myDatabase";
    private static String tablename = "users";
    private static int version = 1;

    private static String id = "id";
    private static String username = "username";
    private static String email = "email";
    private static String password = "password";
    public dbconnect(@Nullable Context context) {
        super(context, dbname, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table "+tablename+"("+id+" integer primary key autoincrement,"+username+" text,"+email+" text,"+password+" text);";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("drop table if exists "+tablename);

    onCreate(db);
    }

    public boolean addUser(User user) {
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = this.getWritableDatabase();

            // Check if username already exists
            cursor = db.rawQuery("SELECT * FROM " + tablename + " WHERE username = ?",
                    new String[]{user.getUsername()});

            if (cursor.getCount() > 0) {
                return false; // Username already taken
            }

            // Insert new user
            ContentValues values = new ContentValues();
            values.put("username", user.getUsername());
            values.put("email", user.getEmail());
            values.put("password", user.getPassword());

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



    public User authenticateUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email = ? AND password = ?", new String[]{email, password});

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            String dbEmail = cursor.getString(cursor.getColumnIndexOrThrow("email"));
            String dbPassword = cursor.getString(cursor.getColumnIndexOrThrow("password"));

            cursor.close();
            db.close();

            return new User(id, username, dbEmail, dbPassword); // Return User object if found
        }

        cursor.close();
        db.close();
        return null; // Return null if no user is found
    }

}
