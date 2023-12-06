package com.example.howtotrackprj;

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

public class foodItemsDataGateway extends SQLiteOpenHelper {


    private Context context;
    private SQLiteDatabase db;


    public foodItemsDataGateway(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS foodItems( id INTEGER primary key autoincrement,name text, calories int,protein double,usernameID text)";
        db.execSQL((query));

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String query = "DROP TABLE IF EXISTS food;";
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


    public void InsertInfos(String name, Integer calories, double protein,String idName) {
        this.Open();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("calories", calories);
        cv.put("protein", protein);
        cv.put("usernameID",idName);
        cv.put("quantity",1);


        this.db.insert("foodItems", null, cv);

        System.out.println("Enregistrer");


    }


    public Integer calculateCaloriesForUser(String username) {
        this.Open();
        int totalCalories = 0;


        String query = "SELECT SUM(calories), SUM(protein) FROM foodItems WHERE usernameID = ?";
        Cursor cursor = db.rawQuery(query, new String[] { username });

        if (cursor.moveToFirst()) {
            totalCalories = cursor.getInt(0);

        }

        cursor.close();
        db.close();
        return totalCalories;

    }


    public Double calculateProteinForUser(String username) {
        this.Open();
        double protein=0;


        String query = "SELECT SUM(calories), SUM(protein) FROM foodItems WHERE usernameID = ?";
        Cursor cursor = db.rawQuery(query, new String[] { username });

        if (cursor.moveToFirst()) {
            protein = cursor.getDouble(1);

        }

        cursor.close();
        db.close();
        return protein;

    }


    public void updateDatabase(){
        String query="ALTER TABLE foodItems ADD COLUMN quantity int;";
        db.execSQL(query);


    }

    public void InsertOrUpdateFoodItem(String foodName, double calories, double protein,String User) {

this.Open();
                // Check if the food item already exists
                Cursor cursor = db.rawQuery("SELECT * FROM foodItems WHERE name=? AND usernameID=?" , new String[]{foodName,User});

        if (cursor.getCount() > 0) {
            // The item exists, update the values
            cursor.moveToFirst();

            @SuppressLint("Range") int quantity = cursor.getInt(cursor.getColumnIndex("quantity")) + 1;
            @SuppressLint("Range") double existingCalories = cursor.getDouble(cursor.getColumnIndex("calories"));
            @SuppressLint("Range") double existingProtein = cursor.getDouble(cursor.getColumnIndex("protein"));

            // Update the values
            ContentValues values = new ContentValues();
            values.put("quantity", quantity);
            values.put("calories", existingCalories + calories);
            values.put("protein", existingProtein + protein);

            db.update("foodItems", values, "name=?", new String[]{foodName});
        } else {

            ContentValues values = new ContentValues();
            values.put("name", foodName);
            values.put("calories", calories);
            values.put("protein", protein);
            values.put("usernameID",User);
            values.put("quantity", 1);

            db.insert("foodItems", null, values);
        }

        cursor.close();
    }


    public void dropTable(String tableName) {
        SQLiteDatabase db = getWritableDatabase();

        // Drop the table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + tableName);

        db.close();
    }


    public List<foodItem> fetchDataForUser(String userId) {
        List<foodItem> foodItems = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Example query: SELECT * FROM food_items WHERE user_id = 'userId'
            cursor = db.query("foodItems", null, "usernameID = ?", new String[]{userId}, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Extract data from the cursor and create FoodItem objects
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                    @SuppressLint("Range") int calories = cursor.getInt(cursor.getColumnIndex("calories"));
                    @SuppressLint("Range") int protein = cursor.getInt(cursor.getColumnIndex("protein"));
                    @SuppressLint("Range") int quantity = cursor.getInt(cursor.getColumnIndex("quantity"));

                    foodItem foodItem = new foodItem(name, calories, protein, quantity);

                    foodItems.add(foodItem);
                } while (cursor.moveToNext());
            }
        } finally {

            if (cursor != null) {
                cursor.close();
               ;
            }
        }

        return foodItems;
    }


    public void logAllRecords(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM foodItems" , null);

        if (cursor.moveToFirst()) {
            do {
                String logMessage = "";

                // Loop through all columns in the cursor
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    String columnName = cursor.getColumnName(i);
                    String columnValue = cursor.getString(i);

                    logMessage += columnName + ": " + columnValue + ", ";
                }

                // Remove the trailing comma and space
                if (!logMessage.isEmpty()) {
                    logMessage = logMessage.substring(0, logMessage.length() - 2);
                }

                Log.d("DatabaseRecord", logMessage);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
    }


    public void deleteUserData(String userName) {
        SQLiteDatabase db = this.getWritableDatabase();

        if (db != null) {
            // Define the WHERE clause to specify the user to delete
            String whereClause = "usernameID=?";
            String[] whereArgs = {userName};

            // Perform the delete operation
            db.delete("foodItems", whereClause, whereArgs);

            // Close the database
            db.close();
        }
    }


}
