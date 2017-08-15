package de.htwberlin.f4.ai.ma.persistence;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by Johann Winter
 */

public class ContentProviderDB extends ContentProvider {
    DatabaseHandler databaseHandler;
    public static final String AUTHORITY = "ourContentProviderAuthorities"; //specific for our app, will be specified in maninfest
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    @Override
    public boolean onCreate() {
        databaseHandler = new DatabaseHandlerImplementation(getContext());
        return true;
    }

    @Override
    public String getType(Uri arg0) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
        String table = getTableName(uri);
        SQLiteDatabase database = databaseHandler.getWritableDatabase();
        long value = database.insert(table, null, initialValues);
        return Uri.withAppendedPath(CONTENT_URI, String.valueOf(value));
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        String table =getTableName(uri);
        SQLiteDatabase database = databaseHandler.getReadableDatabase();
        Cursor cursor =database.query(table,  projection, selection, selectionArgs, null, null, sortOrder);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String whereClause,
                      String[] whereArgs) {
        String table = getTableName(uri);
        SQLiteDatabase database = databaseHandler.getWritableDatabase();
        return database.update(table, values, whereClause, whereArgs);
    }

    @Override
    public int delete(Uri uri, String where, String[] args) {
        String table = getTableName(uri);
        SQLiteDatabase dataBase = databaseHandler.getWritableDatabase();
        return dataBase.delete(table, where, args);
    }

    public static String getTableName(Uri uri){
        String value = uri.getPath();
        value = value.replace("/", ""); //we need to remove '/'
        return value;
    }
}