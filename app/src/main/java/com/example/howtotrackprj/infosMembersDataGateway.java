package com.example.howtotrackprj;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.Nullable;

public class infosMembersDataGateway extends SQLiteOpenHelper {


    private Context context;
    private SQLiteDatabase db;

    public infosMembersDataGateway(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context=context;
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS infosMembers( idName text primary key , weight double, height double,gymDays integer,objective text)";
        db.execSQL((query));


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String query = "DROP TABLE IF EXISTS members;";
        db.execSQL(query);
        onCreate(db);
        db.close();
    }

    public void Open() {
        this.db= this.getWritableDatabase();
    }

    public void Close(){
        this.db.close();
    }



    public void InsertInfos(String id, Integer weight,Integer height,Integer days,String objective){

        ContentValues cv = new ContentValues();
        cv.put("idName",id);
        cv.put("weight",weight);
        cv.put("height",height);
        cv.put("objective",objective);


        this.db.insert("infosMembers",null,cv);

        System.out.println("Enregistrer");


    }



    public Pair<Integer, Double> calculateProteinCals(String userName) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the columns you want to retrieve
        String[] columns = {"weight", "height", "objective"};

        // Specify the WHERE clause to find the user by name
        String selection = "idName = ?";
        String[] selectionArgs = {userName};

        Cursor cursor = db.query("infosMembers", columns, selection, selectionArgs, null, null, null);


        if (cursor.moveToFirst()) {

            @SuppressLint("Range") int weight = cursor.getInt(cursor.getColumnIndex("weight"));
            @SuppressLint("Range") int height = cursor.getInt(cursor.getColumnIndex("height"));
            @SuppressLint("Range") String objective = cursor.getString(cursor.getColumnIndex("objective"));

            // Calculate recommended protein and calorie intake based on weight, height, and objective
            double recommendedProtein = calculateProteinIntake(weight, height, objective);
            int recommendedCalories = calculateCalorieIntake(weight, height, objective);

            // Format the result
            return new Pair<>(recommendedCalories, recommendedProtein);
        }
        return new Pair<>(0, 0.0);
    }

    private Double calculateProteinIntake(int weight, int height, String objective) {

        if("GainWeight".equals(objective.trim())){


            return weight * 1.5;
        }
        else{
            return Double.valueOf(weight);
        }

    }

    private int calculateCalorieIntake(int weight, int height, String objective) {
        if("GainWeight".equals(objective.trim())){


            return (int) (weight * 20);
        }
        else{
            return (int) (weight *10);
        }



    }


    public ContentValues getInfoForUser(String idName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM infosMembers WHERE idName = ?";
        Cursor cursor = db.rawQuery(query, new String[]{idName});

        ContentValues contentValues = new ContentValues();

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") double weight = cursor.getDouble(cursor.getColumnIndex("weight"));
            @SuppressLint("Range") double height = cursor.getDouble(cursor.getColumnIndex("height"));
            @SuppressLint("Range") int gymDays = cursor.getInt(cursor.getColumnIndex("gymDays"));
            @SuppressLint("Range") String objective = cursor.getString(cursor.getColumnIndex("objective"));

            contentValues.put("idName", idName);
            contentValues.put("weight", weight);
            contentValues.put("height", height);
            contentValues.put("gymDays", gymDays);
            contentValues.put("objective", objective);

        }

        cursor.close();
        db.close();

        return contentValues;
    }












    public void logAllRecords() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM infosMembers" , null);

        if (cursor.moveToFirst()) {
            do {
                String logMessage = "";


                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    String columnName = cursor.getColumnName(i);
                    String columnValue = cursor.getString(i);

                    logMessage += columnName + ": " + columnValue + ", ";
                }


                if (!logMessage.isEmpty()) {
                    logMessage = logMessage.substring(0, logMessage.length() - 2);
                }

                Log.d("DatabaseRecord", logMessage);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
    }


}
