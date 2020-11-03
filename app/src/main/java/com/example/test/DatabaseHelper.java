package com.example.test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_MOBILE = "mobile";
    public static final String COLUMN_PASSWORD = "password";
    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
     private String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_USERS + "(" + COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_NAME + " text not null, " + COLUMN_EMAIL +
            " text not null, " + COLUMN_MOBILE + " integer, " +
            COLUMN_PASSWORD + " text not null);";

    // Initialize the database object.

    /**
     * Initialises the database object.
     * @param context the context passed on to the super.
     */


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    /**
     * Converts the show table in the database to a JSON string.
     * @param database the database to get the data from.
     * @return a string in JSON format containing all the show data.
     */
    private String getJSONExportString(SQLiteDatabase database) {
        String selectQuery = "SELECT * FROM " + TABLE_USERS;
        Cursor cursor = database.rawQuery(selectQuery, null);

        // Convert the database to JSON
        JSONArray databaseSet = new JSONArray();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        if (cursor.getString(i) != null) {
                            rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                        } else {
                            rowObject.put(cursor.getColumnName(i), "");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            databaseSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();

        // Convert databaseSet to string and put in file
        return databaseSet.toString();
    }


    /**
     * Returns the database name.
     * @return the database name.
     */
    public static String getDatabaseFileName() {
        return DATABASE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        // Create the database with the database creation statement.
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_USERS);

    }
    public boolean addUser(String name, String email, String mobile, String password){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues c=new ContentValues();
        c.put(COLUMN_NAME,name);
        c.put(COLUMN_EMAIL,email);
        c.put(COLUMN_MOBILE,mobile);
        c.put(COLUMN_PASSWORD,password);
        long result=db.insert(TABLE_USERS,null,c);
        if (result==-1){
            return false;
        } else {
            return true;
        }

    }

    public String getLoginData(String email,String password)
    {
        SQLiteDatabase sql=this.getReadableDatabase();
        String query=" select count(*) from "+TABLE_USERS+" where email ='"+email+"' and password='"+password+"'";
        Cursor cursor =sql.rawQuery(query,null);
        cursor.moveToFirst();
        String count = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(0)));
        return count;

    }
}
