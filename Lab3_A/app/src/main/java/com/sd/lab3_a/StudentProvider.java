package com.sd.lab3_a;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import java.util.HashMap;

public class StudentProvider extends ContentProvider {

    static final String PROVIDER_NAME = "com.sd.lab3_a.StudentProvider";
    static final String URL = "content://" + PROVIDER_NAME + "/cpstudents";

    static final Uri CONTENT_URI = Uri.parse(URL);
    static final int uriCode = 1;

    static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "student.db";

    static final String TABLE_NAME = "student";


    private static HashMap<String,String> values;

    static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "cpstudents", uriCode);
    }

    private SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        DatabaseHelper dbHandler = new DatabaseHelper(getContext());
        database = dbHandler.getWritableDatabase();

        if (database != null)
            return true;

        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case uriCode:
                queryBuilder.setProjectionMap(values);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI" + uri);
        }

        Cursor cursor = queryBuilder.query(database,projection, selection,
                selectionArgs, null, null, sortOrder);

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case uriCode:
                return "vnd.android.cursor.dir/cpstudents";

            default:
                throw new IllegalArgumentException("Unsupported URI" + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        //Not Supported
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        //Not Supported
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        //Not Supported
        return 0;
    }
}
