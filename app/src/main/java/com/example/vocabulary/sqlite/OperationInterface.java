package com.example.vocabulary.sqlite;

import com.example.vocabulary.domain.WordsContent;

import java.util.ArrayList;

public interface OperationInterface {
    /**
     * @return 获取全部单词
     */
    ArrayList<WordsContent.Word> getAllWord();

    /**
     * 插入单词
     *
     * @param word        单词
     * @param meaning     词义
     * @param sample      样例
     * @param newWordFlag 是否为生词,1为生词
     * @return 新插入单词id
     */
    int insert(String word, String meaning, String sample, int newWordFlag);

    /**
     * 删除单词
     *
     * @param id 单词id
     */
    void delete(int id);

    /**
     * 更新单词
     *
     * @param id      单词id
     * @param word    单词
     * @param meaning 词义
     * @param sample  样例
     */
    void update(int id, String word, String meaning, String sample);

    /**
     * 更新生词本标识
     *
     * @param newWordFlag 是否为生词,1为生词
     * @param id          单词id
     */
    void addNewWord(int newWordFlag, int id);

    /**
     * 搜索
     *
     * @param searchStr 模糊搜索字符串
     * @return 搜索结果列表
     */
    ArrayList<WordsContent.Word> search(String searchStr);

    /**
     * 清空
     */
    void clear();

    /**
     * @return 获取全部生词
     */
    ArrayList<WordsContent.Word> getAllNewWords();
}
