package com.sd.lab3_a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "student";
    private static final String TAG = "DatabaseHelper";

    private static final String idCol = "id";
    private static final String fullNameCol = "full_name";
    private static final String dateAddedCol = "date_added";

    private Context context;
    DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" + idCol + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                            + fullNameCol + " TEXT, "
                                                            + dateAddedCol + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;

        return db.rawQuery(query, null);
    }

    public Cursor getLastID(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT MAX(" + idCol + ") FROM " + TABLE_NAME;
        return db.rawQuery(query, null);
    }

    public void addData(Student s) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(fullNameCol, s.getFullName());
        contentValues.put(dateAddedCol, s.getDateAdded());

        db.insert(TABLE_NAME, null, contentValues);
        context.getContentResolver().insert(StudentProvider.CONTENT_URI, contentValues);
    }

    public void updateLastAddedName(String newName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + fullNameCol + " = '" + newName + "' " +
                        "WHERE id = (SELECT MAX(" + idCol + ") FROM " + TABLE_NAME + ")";
        db.execSQL(query);
    }

    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME;
        db.execSQL(query);
    }
}
