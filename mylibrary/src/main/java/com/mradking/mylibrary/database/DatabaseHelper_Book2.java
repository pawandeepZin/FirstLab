package com.mradking.mylibrary.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mradking.mylibrary.modal.Modal;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper_Book2 extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "book2.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "mytable";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_STATUS = "status";
    public DatabaseHelper_Book2(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_EMAIL + " TEXT,"
                +COLUMN_STATUS + " TEXT"

                + ")";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableQuery);
        onCreate(db);
    }

    public boolean insertData(Modal modal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, modal.getName());
        contentValues.put(COLUMN_EMAIL, modal.getPaths());
        contentValues.put(COLUMN_STATUS,modal.getStatus());

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public List<Modal> getAllContacts() {
        List<Modal> contactList = new ArrayList<Modal>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Modal contact = new Modal();
                contact.set_id(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPaths(cursor.getString(2));
                contact.setStatus(cursor.getString(3));


                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    public void deleteContact(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?",
                new String[] { id });
        db.close();
    }

    public int updateData(String id, String path, String status) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, path);
        values.put(COLUMN_STATUS, status);

        // updating row
        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?",
                new String[] { id });
    }
    public void deleteAllData() {
        SQLiteDatabase database = getWritableDatabase(); // Replace with your database reference

        // Delete all rows from the table
        database.delete(TABLE_NAME, null, null);

        // Close the database
        database.close();
    }


}
