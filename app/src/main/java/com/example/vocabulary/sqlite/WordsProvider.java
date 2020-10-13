package com.example.vocabulary.sqlite;

import android.content.*;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import com.example.vocabulary.domain.WordsContent;

public class WordsProvider extends ContentProvider {
    private static final int MULTIPLE_WORDS = 1; //UriMatcher匹配结果码
    private static final int SINGLE_WORD = 2;

    WordsDBHelper helper;
    SQLiteDatabase database;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(WordsContent.AUTHORITY, WordsContent.WordBase.PATH_SINGLE, SINGLE_WORD);
        uriMatcher.addURI(WordsContent.AUTHORITY, WordsContent.WordBase.PATH_MULTIPLE, MULTIPLE_WORDS);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        int count;
        switch (uriMatcher.match(uri)) {
            case MULTIPLE_WORDS:
                count = database.delete(WordsContent.WordBase.TABLE_NAME, selection, selectionArgs);
                break;
            case SINGLE_WORD:
                String whereClause = WordsContent.WordBase._ID + "=" + uri.getPathSegments().get(1);
                count = database.delete(WordsContent.WordBase.TABLE_NAME, whereClause, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unkonwn Uri:" + uri);
        }
        //通知ContentResolver,数据已经发生改变
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        // at the given URI.
        switch (uriMatcher.match(uri)) {
            case MULTIPLE_WORDS://多条数据记录
                return WordsContent.WordBase.MINE_TYPE_MULTIPLE;
            case SINGLE_WORD://单条数据记录
                return WordsContent.WordBase.MINE_TYPE_SINGLE;
            default:
                throw new IllegalArgumentException("Unkonwn Uri:" + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = database.insert(WordsContent.WordBase.TABLE_NAME, null, values);
        if (id > 0) {
            //在已有的Uri后面添加id
            Uri newUri = ContentUris.withAppendedId(WordsContent.WordBase.CONTENT_URI, id);
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        }
        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        helper = new WordsDBHelper(context);
        database = helper.getWritableDatabase();
        return database != null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(WordsContent.WordBase.TABLE_NAME);
        switch (uriMatcher.match(uri)) {
            case MULTIPLE_WORDS:
                return database.query(WordsContent.WordBase.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
            case SINGLE_WORD:
                queryBuilder.appendWhere(WordsContent.WordBase._ID + "=" + uri.getPathSegments().get(1));
                return queryBuilder.query(database, projection, selection, selectionArgs, null, null, sortOrder);
            default:
                throw new IllegalArgumentException("Unknown Uri:" + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int count;
        switch (uriMatcher.match(uri)) {
            case MULTIPLE_WORDS:
                count = database.update(WordsContent.WordBase.TABLE_NAME, values, selection, selectionArgs);
                break;
            case SINGLE_WORD:
                String segment = uri.getPathSegments().get(1);
                count = database.update(WordsContent.WordBase.TABLE_NAME, values, WordsContent.WordBase._ID + "=" + segment, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown Uri:" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
