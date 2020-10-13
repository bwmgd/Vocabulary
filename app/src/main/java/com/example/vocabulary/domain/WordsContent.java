package com.example.vocabulary.domain;

import android.net.Uri;
import android.provider.BaseColumns;

public class WordsContent {
    public static class Word {
        public int id;
        public String word;
        public String meaning;
        public String sample;

        public int getId() {
            return id;
        }

        public String getWord() {
            return word;
        }

        public String getMeaning() {
            return meaning;
        }

        public String getSample() {
            return sample;
        }

        public void setSample(String sample) {
            this.sample = sample;
        }

        public void setMeaning(String meaning) {
            this.meaning = meaning;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static final String AUTHORITY = "com.example.vocabulary.domain.Words";//URI授权

    public static abstract class WordBase implements BaseColumns {

        public static final String TABLE_NAME = "words";//表名
        // _ID字段：主键，从接口BaseColumn而来
        public static final String COLUMN_NAME_WORD = "word";//字段：单词
        public static final String COLUMN_NAME_MEANING = "meaning";//字段：单词含义
        public static final String COLUMN_NAME_SAMPLE = "sample";//字段：单词示例

        //MIME类型
        public static final String MIME_DIR_PREFIX = "vnd.android.cursor.dir";
        public static final String MIME_ITEM_PREFIX = "vnd.android.cursor.item";
        public static final String MINE_ITEM = "vnd.example.vocabulary.domain.Word";

        public static final String MINE_TYPE_SINGLE = MIME_ITEM_PREFIX + "/" + MINE_ITEM;
        public static final String MINE_TYPE_MULTIPLE = MIME_DIR_PREFIX + "/" + MINE_ITEM;

        public static final String PATH_SINGLE = "word/#";//单条数据的路径
        public static final String PATH_MULTIPLE = "word";//多条数据的路径

        //Content Uri
        public static final String CONTENT_URI_STRING = "content://" + AUTHORITY + "/" + PATH_MULTIPLE;
        public static final Uri CONTENT_URI = Uri.parse(CONTENT_URI_STRING);
    }

}
