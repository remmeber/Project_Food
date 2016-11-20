package com.rhg.qf.datebase;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * desc:数据库内容提供
 * author：remember
 * time：2016/10/23 20:30
 * email：1013773046@qq.com
 */


public class DBProvider extends ContentProvider {
    private SQLiteDatabase sqLiteDatabase;
    public static final String AUTHORITY = "com.rhg.qf.DBProvider";
    public static final int ACCOUNT_CODE = 0;
    public static final int SHOPPING_CART_CODE = 1;
    public static final int ADDRESS_CODE = 2;
    public static final int SEARCH_HISTORY_CODE = 2;

    public static final Uri SEARCH_HISTORY_URI = Uri.parse("content://" + AUTHORITY + "/search_history");

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, "user", ACCOUNT_CODE);
        uriMatcher.addURI(AUTHORITY, "shopping_cart", SHOPPING_CART_CODE);
        uriMatcher.addURI(AUTHORITY, "address", ADDRESS_CODE);
        uriMatcher.addURI(AUTHORITY, "search_history", SEARCH_HISTORY_CODE);
    }

    @Override
    public boolean onCreate() {
        AccountDBHelper.init(this.getContext());
        sqLiteDatabase = AccountDBHelper.getInstance().getReadableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String table = getTableName(uri);
        if (table == null)
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        return sqLiteDatabase.query(table, projection, selection, selectionArgs, null, sortOrder, null);
    }

    private String getTableName(Uri uri) {
        String tableName = null;
        switch (uriMatcher.match(uri)) {
            case SEARCH_HISTORY_CODE:
                tableName = AccountDBHelper.Q_SEARCH_HISTORY_TABLE;
                break;
            default:
                throw new IllegalArgumentException("no such table");
        }
        return tableName;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        Log.i("RHG", uri.getPath());
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String table = getTableName(uri);
        if (table == null)
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        sqLiteDatabase.insert(table, null, values);
        if (getContext() == null)
            throw new NullPointerException("Context can not be null object");
        getContext().getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String table = getTableName(uri);
        if (table == null)
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        int count = sqLiteDatabase.delete(table, selection, selectionArgs);
        if (count > 0) {
            if (getContext() == null)
                throw new NullPointerException("Context can not be null object");
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        String table = getTableName(uri);
        if (table == null)
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        int row = sqLiteDatabase.update(table, values, selection, selectionArgs);
        if (row > 0) {
            if (getContext() == null)
                throw new NullPointerException("Context can not be null object");
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return row;
    }
}
