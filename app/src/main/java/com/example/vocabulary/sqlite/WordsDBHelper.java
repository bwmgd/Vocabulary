package com.example.vocabulary.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.vocabulary.domain.WordsContent;

public class WordsDBHelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "words.db";//数据库名字
    private final static int DATABASE_VERSION = 1;//数据库版本
    //建表语句
    private final static String SQL_CREATE_DATABASE = "CREATE TABLE "
            + WordsContent.WordBase.TABLE_NAME + " (" +
            WordsContent.WordBase._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            WordsContent.WordBase.COLUMN_NAME_WORD + " TEXT" + "," +
            WordsContent.WordBase.COLUMN_NAME_MEANING + " TEXT" + ","
            + WordsContent.WordBase.COLUMN_NAME_SAMPLE + " TEXT" + "," +
            WordsContent.WordBase.COLUMN_NAME_NEW_WORD + " INTEGER DEFAULT 0" + " )";

    //删表语句
    private final static String SQL_DELETE_DATABASE = "DROP TABLE IF EXISTS " + WordsContent.WordBase.TABLE_NAME;


    public WordsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    //建表
    public void onCreate(SQLiteDatabase db) {
        Log.v("test", "建表成功");
        db.execSQL(SQL_CREATE_DATABASE);
    }

    @Override
    //更新，先删后建
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_DATABASE);
        onCreate(db);
    }
}
