package com.example.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class database_helper extends SQLiteOpenHelper {

    private static final String name = "Course";
    private static final String tablename = "Student";
    private static final String id = "ID";
    private static final String Course_Name = "Course_Name";
    public database_helper(@Nullable Context context) {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = " CREATE TABLE " + tablename + "("
                + id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + name + " TEXT, "
                + Course_Name + "TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
        Log.d("DatabaseOperation", "Table created successfully");

    }
    public void addStudent(String dbname, String Course_name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(name, dbname);
        values.put(Course_Name, Course_name);
        db.insert(tablename, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
