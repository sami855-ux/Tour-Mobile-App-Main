package com.example.assignment_gr;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class dbconnect extends SQLiteOpenHelper {

    private static String dbname = "myDatabase";
    private static String tablename = "users";
    private static String hotelTable = "hotels";
    private static int version = 1;

    public static String id = "id";
    private static String username = "username";
    private static String email = "email";
    private static String password = "password";
    // Hotel table columns
    private static String hotelId = "hotel_id";
    private static String hotelName = "hotel_name";
    private static String positionName = "position_name";
    private static String userIdFk = "user_id";
    public dbconnect(@Nullable Context context) {
        super(context, dbname, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create users table
        String query = "CREATE TABLE " + tablename + " (" +
                id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                username + " TEXT, " +
                email + " TEXT, " +
                password + " TEXT);";
        db.execSQL(query);

        // Create hotels table (linked to users via user_id)
        String hotelQuery = "CREATE TABLE " + hotelTable + " (" +
                hotelId + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                hotelName + " TEXT, " +
                positionName + " TEXT, " +
                userIdFk + " INTEGER, " +
                "FOREIGN KEY(" + userIdFk + ") REFERENCES " + tablename + "(" + id + "));";
        db.execSQL(hotelQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tablename);
        db.execSQL("DROP TABLE IF EXISTS " + hotelTable);
        onCreate(db);
    }

    // Add a new hotel for a user
    public boolean addHotel(int userId, String hotelName, String positionName) {
        // Check for valid inputs
        if (userId <= 0) {
            Log.e("ADD_HOTEL_ERROR", "Invalid input data. User ID: "
                    + userId + ", Hotel Name: " + hotelName + ", Position Name: "
                    + positionName);
            return false;
        }

        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();

            // Check if the user exists
            Cursor cursor = db.rawQuery("SELECT * FROM " + tablename + " WHERE " + id + " = ?", new String[]{String.valueOf(userId)});
            boolean userExists = cursor.moveToFirst();
            cursor.close();

            if (!userExists) {
                Log.e("ADD_HOTEL_ERROR", "User ID does not exist: " + userId);
                return false; // User does not exist
            }

            // Insert the hotel
            ContentValues values = new ContentValues();
            values.put(userIdFk, userId);
            values.put(hotelName, hotelName);
            values.put(positionName, positionName);

            long result = db.insert(hotelTable, null, values);

            if (result == -1) {
                Log.e("ADD_HOTEL_ERROR", "Failed to insert hotel. User ID: " + userId + ", Hotel Name: " + hotelName + ", Position Name: " + positionName);
                return false; // Insertion failed
            }

            return true;
        } catch (Exception e) {
            Log.e("ADD_HOTEL_ERROR", "Exception occurred while adding hotel: " + e.getMessage());
            return false;
        } finally {
            if (db != null && db.isOpen()) {
                db.close(); // Ensure database connection is closed
            }
        }
    }

    // List all hotels for a specific user
    public List<Hotel> getHotelsListForUser(int userId) {
        List<Hotel> hotelsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + hotelTable + " WHERE " + userIdFk + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int hotelNameIndex = cursor.getColumnIndex("hotelName"); // Adjust column names
                int positionNameIndex = cursor.getColumnIndex("positionName");

                String hotelName = cursor.getString(hotelNameIndex);
                String positionName = cursor.getString(positionNameIndex);

                hotelsList.add(new Hotel(hotelName, positionName)); // Store as Hotel object
            } while (cursor.moveToNext());
            cursor.close();
        }

        return hotelsList;
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
    public boolean deleteUser(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            int rowsDeleted = db.delete(tablename, id + " = ?", new String[]{String.valueOf(userId)});
            return rowsDeleted > 0;
        } finally {
            db.close();
        }
    }

    public boolean updateUser(int userId, String newUsername, String newEmail, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username", newUsername);
        values.put("email", newEmail);
        values.put("password", newPassword);

        // Update query
        int rowsAffected = db.update(tablename, values, id + " = ?", new String[]{String.valueOf(userId)});

        db.close();
        return rowsAffected > 0; // Returns true if update was successful
    }

    public User getUserById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        User user = null;

        try {
            cursor = db.rawQuery("SELECT * FROM " + tablename + " WHERE " + id + " = ?",
                    new String[]{String.valueOf(userId)});

            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));

                user = new User(id, username, email, password); // Create User object
            }
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return user; // Returns user if found, otherwise null
    }

}
