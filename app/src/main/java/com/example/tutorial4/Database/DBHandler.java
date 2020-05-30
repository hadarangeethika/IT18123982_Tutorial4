package com.example.tutorial4.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "UserInfo.db3";

    public DBHandler(Context context){
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES = "CREATE TABLE " + UsersMaster.Users.TABLE_NAME + " (" +
                                    UsersMaster.Users._ID + " INTEGER PRIMARY KEY," +
                                    UsersMaster.Users.COLUMN_NAME_USERNAME + " TEXT," +
                                    UsersMaster.Users.COLUMN_NAME_PASSWORD + " TEXT)";
        //use the details from the UserMaster and Users classes we created. Specify the primary key from the BaseColumns

        db.execSQL(SQL_CREATE_ENTRIES); //this will execute the contents of SQL_CREATE_ENTRIES
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UsersMaster.Users.TABLE_NAME);
        onCreate(db);
    }

    public void addInfo(String userName, String password){
        //get the data repository in the writable mode
        SQLiteDatabase db = getWritableDatabase();

        //create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(UsersMaster.Users.COLUMN_NAME_USERNAME, userName);
        values.put(UsersMaster.Users.COLUMN_NAME_PASSWORD, password);

        //insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(UsersMaster.Users.TABLE_NAME,null,values);
    }

    public List readAllInfo(){
        SQLiteDatabase db = getReadableDatabase();

        //define a projection specifies that which columns from the database you will actually use after this query
        String[] projection = {
                UsersMaster.Users._ID,
                UsersMaster.Users.COLUMN_NAME_USERNAME,
                UsersMaster.Users.COLUMN_NAME_PASSWORD
        };

        //how you want the results sorted in the resulting cursor
        String sortOrder = UsersMaster.Users.COLUMN_NAME_USERNAME + "DESC";

        Cursor cursor = db.query(UsersMaster.Users.TABLE_NAME,projection,null,null,null,null,sortOrder);

        List userNames = new ArrayList<>();
        List passwords = new ArrayList<>();

        while (cursor.moveToNext()){
            String username = cursor.getString(cursor.getColumnIndex(UsersMaster.Users.COLUMN_NAME_USERNAME));
            String password = cursor.getString(cursor.getColumnIndex(UsersMaster.Users.COLUMN_NAME_PASSWORD));
            userNames.add(username);
            passwords.add(password);
        }
        cursor.close();
        return userNames;
    }

    public boolean readInfo(String userName,String password){
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                UsersMaster.Users._ID,
                UsersMaster.Users.COLUMN_NAME_USERNAME,
                UsersMaster.Users.COLUMN_NAME_PASSWORD
        };

        String sortOrder = UsersMaster.Users.COLUMN_NAME_USERNAME + "DESC";

        Cursor cursor = db.query(UsersMaster.Users.TABLE_NAME,projection,null,null,null,null,sortOrder);

        while (cursor.moveToNext()){
            String u = cursor.getString(cursor.getColumnIndex(UsersMaster.Users.COLUMN_NAME_USERNAME));
            String p = cursor.getString(cursor.getColumnIndex(UsersMaster.Users.COLUMN_NAME_PASSWORD));
            if (u.equals(userName) && p.equals(password)){
                return true;
            }
        }

        cursor.close();
        return false;
    }

    //this will delete a particular user from the table
    public boolean deleteInfo(String userName){
        SQLiteDatabase db = getReadableDatabase();

        //define where part f query
        String selection = UsersMaster.Users.COLUMN_NAME_USERNAME + "LIKE ?";
        //specify arguments and placeholder order
        String[] selectionArgs = {userName};
        //issue SQL statement
        int count = db.delete(UsersMaster.Users.TABLE_NAME,selection,selectionArgs);
        if (count==1){
            return true;
        }else {
            return false;
        }
    }

    //update user details for a given username
    public void updateInfo(String userName,String password){
        SQLiteDatabase db = getReadableDatabase();

        //new value for one column
        ContentValues values = new ContentValues();
        values.put(UsersMaster.Users.COLUMN_NAME_PASSWORD,password);

        //which row to update, based on the title
        String selection = UsersMaster.Users.COLUMN_NAME_USERNAME + "LIKE ?";
        String[] selectionArgs = {userName};

        db.update(UsersMaster.Users.TABLE_NAME,values,selection,selectionArgs);
    }
}
