package com.example.howtotrackprj;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class userGoals extends SQLiteOpenHelper {
    private Context context;
    private SQLiteDatabase db;

    public userGoals(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    public userGoals(Context context) {
        super(context, "userGoals", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS userGoals( username text primary key, proteins float, calories int,todayDate date)";
        db.execSQL((query));


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String query = "DROP TABLE IF EXISTS userGoals;";
        db.execSQL(query);
        onCreate(db);
        db.close();
    }

    public void Open() {
        this.db = this.getWritableDatabase();
    }

    public void Close() {
        this.db.close();
    }


    public long InsertGoals(String username, Integer cals, Double prots, String todayDate) {
        // Assuming 'db' is an instance variable or a parameter passed to this method
        // SQLiteDatabase db = ...; // Initialize your SQLiteDatabase object
Open();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("calories", cals);
        values.put("proteins", prots);
        values.put("todayDate", todayDate);

        long result = db.insert("userGoals", null, values);

        // Note: Closing the database here may not be necessary, depending on your application flow
        // db.close();

        return result;
    }



    public List<ProtsCals> fetchDataForUser(String userId) {
        List<ProtsCals> protssCalss = new ArrayList<>();


        Cursor cursor = null;

        try {
            cursor = db.query("userGoals", null, "username = ?", new String[]{userId}, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Extract data from the cursor and create ProtsCals objects
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("username"));
                    @SuppressLint("Range") int calories = cursor.getInt(cursor.getColumnIndex("calories"));
                    @SuppressLint("Range") int protein = cursor.getInt(cursor.getColumnIndex("proteins")); // Corrected column name
                    @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("todayDate")); // Corrected column name

                    ProtsCals protsCals = new ProtsCals(name, calories, protein, date);
                    protssCalss.add(protsCals);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return protssCalss;

    }


    public void deleteDB() {

        db.execSQL("DROP TABLE IF EXISTS userGoals;");
        db.close();
    }

    public void createdata(){
        db.execSQL("create table userGoals(user text primary key,nom text)");
    }

}
