package com.example.vocabulary.sqlite;

import android.database.Cursor;
import com.example.vocabulary.domain.WordsContent;

import java.util.ArrayList;

public interface OperationInterface {
    ArrayList<WordsContent.Word> convertCursorToWordList(Cursor cursor);  //光标指向列表

    WordsContent.Word getWord(int id); //获取单个单词

    ArrayList<WordsContent.Word> getAllWord(); //获取全部单词

    void insert(String word, String meaning, String sample); //插入单词

    void delete(String id); //删除单词

    void update(String id, String word, String meaning, String sample); //更新单词

    ArrayList<WordsContent.Word> search(String searchStr); //搜索
}
