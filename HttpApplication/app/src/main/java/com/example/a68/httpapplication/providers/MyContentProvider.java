package com.example.a68.httpapplication.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.a68.httpapplication.databases.MyDataBase;

import static com.example.a68.httpapplication.databases.MyDataBase.COLUMN_ID;
import static com.example.a68.httpapplication.databases.MyDataBase.COLUMN_NAME;
import static com.example.a68.httpapplication.databases.MyDataBase.TABLE_NAME;

public class MyContentProvider extends ContentProvider {

    private SQLiteDatabase database;

    public static final String AUTHORITY = "com.example.a68.httpapplication.MyContentProvider";

    private static final UriMatcher sUriMatcher;
    private static final int DATUM = 1;
    private static final int DATUM_ID = 2;
    private static final int DATUM_BULK_INSERT = 3;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, DATUM);
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME + "/#", DATUM_ID);
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME + "/bulk-insert/", DATUM_BULK_INSERT);
    }

    private MyDataBase dbHelper;


    @Override
    public boolean onCreate() {
        dbHelper = new MyDataBase(getContext());
        // permissions to be writable
        database = dbHelper.getWritableDatabase();

        return database != null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,
                        @Nullable String[] projection,
                        @Nullable String selection,
                        @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        // the TABLE_NAME to query on
        queryBuilder.setTables(TABLE_NAME);

        switch (sUriMatcher.match(uri)) {
            case DATUM:
                break;
            case DATUM_ID:
                queryBuilder.appendWhere(COLUMN_ID + "=" + uri.getLastPathSegment());

                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        if (sortOrder == null || sortOrder.equals("")) {
            // No sorting-> sort on names by default
            sortOrder = COLUMN_NAME;
        }
        Cursor cursor = queryBuilder.query(database, projection, selection,
                selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        switch (sUriMatcher.match(uri)) {
            case DATUM:
                return "vnd.android.cursor.dir/vnd." + AUTHORITY;
            case DATUM_ID:
                return "vnd.android.cursor.item/vnd." + AUTHORITY;
            case DATUM_BULK_INSERT:
                return "vnd.android.cursor.item/vnd." + AUTHORITY + "/bulk-insert";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        int count = 0;
        switch (sUriMatcher.match(uri)) {
            case DATUM_BULK_INSERT:
                database.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        database.insert(TABLE_NAME, null, value);
                        count++;
                    }
                    database.setTransactionSuccessful();
                } finally {
                    database.endTransaction();
                }
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        return count;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return database.delete(TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
