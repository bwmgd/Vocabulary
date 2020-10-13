package com.example.vocabulary.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.vocabulary.WordsApplication;
import com.example.vocabulary.domain.WordsContent;

import java.util.ArrayList;

public class OperationDB implements OperationInterface {
    static WordsDBHelper helper;
    static OperationDB ins = new OperationDB();

    private SQLiteDatabase database;
    private String sql;
    private Cursor cursor;

    public static OperationDB getInstance() {
        return OperationDB.ins;
    }

    public void close() {
        if (helper != null) helper.close();
    }

    OperationDB() {
        if (helper == null) helper = new WordsDBHelper(WordsApplication.getContext());
    }

    @Override
    public ArrayList<WordsContent.Word> convertCursorToWordList(Cursor cursor) {
        ArrayList<WordsContent.Word> list = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            WordsContent.Word word = new WordsContent.Word();
            word.setWord(cursor.getString(cursor.getColumnIndex("word")));
            word.setMeaning(cursor.getString(cursor.getColumnIndex("meaning")));
            word.setSample(cursor.getString(cursor.getColumnIndex("sample")));
            list.add(word);
        }
        return list;
    }

    @Override
    public WordsContent.Word getWord(int id) {
        WordsContent.Word word = new WordsContent.Word();
        database = helper.getReadableDatabase();
        sql = "select * from words where _id = ?";
        cursor = database.rawQuery(sql, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            word.setWord(cursor.getString(cursor.getColumnIndex("word")));
            word.setMeaning(cursor.getString(cursor.getColumnIndex("meaning")));
            word.setSample(cursor.getString(cursor.getColumnIndex("sample")));
            return word;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public ArrayList<WordsContent.Word> getAllWord() {
        database = helper.getReadableDatabase();
        sql = "select * from words";
        cursor = database.rawQuery(sql, null);
        return convertCursorToWordList(cursor);
    }

    @Override
    public void insert(String word, String meaning, String sample) {
        database = helper.getWritableDatabase();
        sql = "insert into words(word,meaning,sample) values(?,?,?)";
        database.execSQL(sql, new String[]{word, meaning, sample});
    }

    @Override
    public void delete(String id) {
        database = helper.getReadableDatabase();
        sql = "delete from words where _id = ?";
        database.execSQL(sql, new String[]{id});
    }

    @Override
    public void update(String id, String word, String meaning, String sample) {
        database = helper.getWritableDatabase();
        sql = "update words set word=?,meaning=?,sample=? where _id=?";
        database.execSQL(sql, new String[]{word, meaning, sample, id});
    }

    @Override
    public ArrayList<WordsContent.Word> search(String searchStr) {
        database = helper.getReadableDatabase();
        sql = "select * from words where word like ? order by word desc";
        cursor = database.rawQuery(sql, new String[]{"%" + searchStr + "%"});
        return convertCursorToWordList(cursor);
    }
}
