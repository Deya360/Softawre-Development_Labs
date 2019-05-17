package com.sd.lab3_b;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static int DB_VERSION = 1;
    private static final String TABLE_NAME = "student";
    private static final String TAG = "DatabaseHelper";

    private static final String idCol = "id";
    private static final String dateAddedCol = "date_added";

    private static final String lastNameCol = "last_name";
    private static final String firstNameCol = "first_name";
    private static final String middleNameCol = "middle_name";

    //Support:
    private static final String fullNameCol = "full_name";


    DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" + idCol + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                            + lastNameCol + " TEXT, "
                                                            + firstNameCol + " TEXT, "
                                                            + middleNameCol + " TEXT, "
                                                            + dateAddedCol + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if (i1 > i) {
            try {
                /* Here, I could've of course had just saved the current database table into a temporary variable
                *  and then loaded it back again after adding the necessary columns,
                *  however, doing it via sql was more challenging and interesting */

                db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + "_old ");
                db.execSQL("ALTER TABLE " + TABLE_NAME + " RENAME TO " + TABLE_NAME + "_old " + ";");

                onCreate(db);

                db.execSQL("INSERT INTO student(" + idCol + ", " + dateAddedCol + ") SELECT " + idCol + ", " + dateAddedCol + " FROM " + TABLE_NAME + "_old " + ";");


                String fnlCol = fullNameCol;
                db.execSQL("UPDATE " + TABLE_NAME + " SET "
                        + firstNameCol + " = SUBSTR(" + fnlCol + ", 1, INSTR(" + fnlCol + ", ' ')-1),"
                        + middleNameCol + " = SUBSTR(" + fnlCol + ", INSTR(" + fnlCol + ", ' ')+1, INSTR(SUBSTR(" + fnlCol + ", INSTR(" + fnlCol + ", ' ')+1), ' ')-1),"
                        + lastNameCol + " = SUBSTR(" + fnlCol + ", INSTR(SUBSTR(" + fnlCol + ", INSTR(" + fnlCol + ", ' ')+1), ' ')+ INSTR(" + fnlCol + ", ' ')+1)");

                db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + "_old ");

            } catch (SQLiteException ex) {
                Log.w(TAG, "Altering " + TABLE_NAME + ": " + ex.getMessage());
            }
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        String query = "CREATE TABLE " + TABLE_NAME + " (" + idCol + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + fullNameCol + " TEXT, "
                + dateAddedCol + " INTEGER);";
        db.execSQL(query);
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
        contentValues.put(lastNameCol, s.getLastName());
        contentValues.put(firstNameCol, s.getFirstName());
        contentValues.put(middleNameCol, s.getMiddleName());
        contentValues.put(dateAddedCol, s.getDateAdded());

        db.insert(TABLE_NAME, null, contentValues);
    }

    public void addDataSupport(Student s) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(fullNameCol, s.getFullName());
        contentValues.put(dateAddedCol, s.getDateAdded());

        db.insert(TABLE_NAME, null, contentValues);
    }

    public void updateLastAddedName(String newLastName, String newFirstName, String newMiddleName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + lastNameCol + " = '" + newLastName + "'," +
                                                          firstNameCol + " = '" + newFirstName + "'," +
                                                          middleNameCol + " = '" + newMiddleName + "' " +
                        "WHERE id = (SELECT MAX(" + idCol + ") FROM " + TABLE_NAME + ")";
        db.execSQL(query);
    }

    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME;
        db.execSQL(query);
    }

    public static int getDbVersion() {
        return DB_VERSION;
    }
    public static void upgrade() {
        DB_VERSION=2;
    }
}
