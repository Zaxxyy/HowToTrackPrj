package com.example.howtotrackprj;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class MembersTableDataGateway extends SQLiteOpenHelper {
    private Context context;
    private SQLiteDatabase db;

    public MembersTableDataGateway(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    public MembersTableDataGateway(Context context) {
        super(context, "members", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS members( username text primary key, password text,register int)";
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
        this.db = this.getWritableDatabase();
    }

    public void Close() {
        this.db.close();
    }

    public void SeedStudentDataBase() {
        this.db.execSQL("INSERT INTO members(username,password)values('val','123')");
        this.db.execSQL("INSERT INTO members(username,password)values('zaxxy','123')");
        this.db.execSQL("INSERT INTO members(username,password)values('test','123')");
        this.db.execSQL("INSERT INTO members(username,password,register)values('test2','123',0)");
db.close();
    }

   public long InsertMember(String username, String password) {

       ContentValues values = new ContentValues();
       values.put("username", username);
       values.put("password", password);
       values.put("register",0);

       long result = db.insert("members", null, values);
       db.close();
       return result;
   }
    public void deleteUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("members", "username  = ?", new String[]{username});
        db.close();
    }


    public boolean isUserAndPasswordMatch(String username, String password) {
        this.Open();
        String query = "SELECT * FROM members WHERE username = ? AND password = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = this.db.rawQuery(query, selectionArgs);

        boolean isMatch = cursor != null && cursor.moveToFirst();
        if (cursor != null) {
            cursor.close();
            db.close();
            return isMatch;
        }
        db.close();
        return false;




    }

    public boolean isUserRegistered(String username,String password) {
            this.Open();
        String query = "SELECT register FROM  members WHERE username = ? AND password = ?";


        String[] selectionArgs = {username,password};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor.moveToFirst()) {
            int registerValue = cursor.getInt(0);


            if (registerValue == 0) {
                cursor.close();
                db.close();
                return false;
            }
        }

        cursor.close();
        db.close();
        return true;
    }

    public void updateUserRegistrationStatus(String username, boolean newStatus) {

        this.Open();

        ContentValues values = new ContentValues();
        values.put("register", newStatus ? 1 : 0);

        String whereClause = "username = ?";
        String[] whereArgs = {username};

        int rowsUpdated = this.db.update("members", values, whereClause, whereArgs);



        db.close();
    }

    public void updateDatabase(){
        String query="ALTER TABLE members ADD COLUMN register int;";
        db.execSQL(query);


    }


    public boolean isMemberExists(String username, String password) {



        String query = "SELECT * FROM members WHERE username = ? AND password = ?";

        String[] selectionArgs = {username, password};


        Cursor cursor = this.db.rawQuery(query, selectionArgs);


        boolean exists = cursor.getCount() > 0;




        if(exists==true){
            System.out.println("l'utilisateur a été trouvé");

        }
        cursor.close();
        db.close();
        return exists;

    }


    public void logAllRecords(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM members" , null);

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


    public boolean isUserExist(String username) {
        this.Open();
        String query = "SELECT * FROM members WHERE username = ?";
        String[] selectionArgs = {username};

        Cursor cursor = this.db.rawQuery(query, selectionArgs);

        boolean isMatch = cursor != null && cursor.moveToFirst();
        if (cursor != null) {
            cursor.close();
            db.close();
            return isMatch;
        }
        db.close();
        return false;




    }







}
