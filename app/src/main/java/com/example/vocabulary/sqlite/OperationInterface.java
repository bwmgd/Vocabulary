package com.example.vocabulary.sqlite;

import android.database.Cursor;
import com.example.vocabulary.domain.WordsContent;

import java.util.ArrayList;

public interface OperationInterface {
    ArrayList<WordsContent.Word> convertCursorToWordList(Cursor cursor);  //光标指向列表

    WordsContent.Word getWord(int id); //获取单个单词

    ArrayList<WordsContent.Word> getAllWord(); //获取全部单词

    int insert(String word, String meaning, String sample, int newWordFlag); //插入单词

    void delete(int id); //删除单词

    void update(int id, String word, String meaning, String sample); //更新单词

    void addNewWord(int newWordFlag, int id); //生词本标志

    ArrayList<WordsContent.Word> search(String searchStr); //搜索

    void clear();//清空

    ArrayList<WordsContent.Word> getAllNewWords();
}
