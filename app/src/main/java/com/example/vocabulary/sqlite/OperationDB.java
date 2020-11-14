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

    private OperationDB() {
        if (helper == null) helper = new WordsDBHelper(WordsApplication.getContext());
    }

    @Override
    public ArrayList<WordsContent.Word> convertCursorToWordList(Cursor cursor) {
        ArrayList<WordsContent.Word> list = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            list.add(makeWord(cursor));
        }
        return list;
    }

    @Override
    public WordsContent.Word getWord(int id) {
        database = helper.getReadableDatabase();
        sql = "select * from words where _id = ?";
        cursor = database.rawQuery(sql, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            return makeWord(cursor);
        }
        throw new IllegalArgumentException();
    }

    private WordsContent.Word makeWord(Cursor cursor) {
        WordsContent.Word word = new WordsContent.Word();
        word.setId(cursor.getInt(cursor.getColumnIndex(WordsContent.WordBase._ID)));
        word.setWord(cursor.getString(cursor.getColumnIndex(WordsContent.WordBase.COLUMN_NAME_WORD)));
        word.setMeaning(cursor.getString(cursor.getColumnIndex(WordsContent.WordBase.COLUMN_NAME_MEANING)));
        word.setSample(cursor.getString(cursor.getColumnIndex(WordsContent.WordBase.COLUMN_NAME_SAMPLE)));
        word.setNewWord(cursor.getInt(cursor.getColumnIndex(WordsContent.WordBase.COLUMN_NAME_NEW_WORD)));
        return word;
    }

    @Override
    public ArrayList<WordsContent.Word> getAllWord() {
        database = helper.getReadableDatabase();
        sql = "select * from words";
        cursor = database.rawQuery(sql, null);
        return convertCursorToWordList(cursor);
    }


    @Override
    public int insert(String word, String meaning, String sample, int newWordFlag) {
        database = helper.getWritableDatabase();
        sql = "insert into words values(null,?,?,?,?)";
        database.execSQL(sql, new Object[]{word, meaning, sample, newWordFlag});
        sql = "select last_insert_rowid() as MaxID from words";
        cursor = database.rawQuery(sql, null);
        if (cursor.moveToFirst()) return cursor.getInt(cursor.getColumnIndex("MaxID"));
        throw new IllegalArgumentException();
    }

    @Override
    public void delete(int id) {
        database = helper.getReadableDatabase();
        sql = "delete from words where _id = ?";
        database.execSQL(sql, new Object[]{id});
    }

    @Override
    public void update(int id, String word, String meaning, String sample) {
        database = helper.getWritableDatabase();
        sql = "update words set word=?,meaning=?,sample=? where _id=?";
        database.execSQL(sql, new Object[]{word, meaning, sample, id});
    }

    @Override
    public void addNewWord(int newWordFlag, int id) {
        database = helper.getWritableDatabase();
        sql = "update words set newWord=? where _id=?";
        database.execSQL(sql, new Object[]{newWordFlag, id});
    }

    @Override
    public ArrayList<WordsContent.Word> search(String searchStr) {
        database = helper.getReadableDatabase();
        sql = "select * from words where word like ? order by word desc";
        cursor = database.rawQuery(sql, new String[]{"%" + searchStr + "%"});
        return convertCursorToWordList(cursor);
    }

    @Override
    public void clear() {
        helper.onUpgrade(database, 1, 1);
    }

    @Override
    public ArrayList<WordsContent.Word> getAllNewWords() {
        database = helper.getReadableDatabase();
        sql = "select * from words where newWord = 1";
        cursor = database.rawQuery(sql, null);
        return convertCursorToWordList(cursor);
    }
}
