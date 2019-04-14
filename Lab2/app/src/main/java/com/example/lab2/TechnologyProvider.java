package com.example.lab2;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import java.util.HashMap;

public class TechnologyProvider extends ContentProvider {

    static final String PROVIDER_NAME = "com.example.lab2.TechnologyProvider";
    static final String URL = "content://" + PROVIDER_NAME +
            "/cptechnologies";

    static final Uri CONTENT_URI = Uri.parse(URL);
    static final int uriCode = 1;

    static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "technologies.db";

    static final String TABLE_NAME = "technologydata";

    static final String COLUMN_NAME = "name";
    static final String COLUMN_HELPTEXT = "helptext";
    static final String COLUMN_GRAPHIC = "graphic";

    private static HashMap<String,String> values;

    static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "cptechnologies", uriCode);
    }

    private SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        DatabaseHandler dbHandler = new DatabaseHandler(getContext());
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
                return "vnd.android.cursor.dir/cptechnologies";

            default:
                throw new IllegalArgumentException("Unsupported URI" + uri);
        }
    }
    
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID = database.insert(TABLE_NAME, null, values);

        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;

        } else {
            return null;
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int rowsDeleted = 0;

        switch (uriMatcher.match(uri)) {
            case uriCode:
                rowsDeleted = database.delete(TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI" + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        //Not Supported
        return 0;
    }


    class DatabaseHandler extends SQLiteOpenHelper {

        DatabaseHandler(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE "+ TABLE_NAME +" ( "+ COLUMN_NAME +" TEXT, " + COLUMN_HELPTEXT +" TEXT, " + COLUMN_GRAPHIC +" TEXT)");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
            onCreate(db);
        }
    }

}
