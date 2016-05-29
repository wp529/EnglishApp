package com.pzhu.english.manage;

import android.database.sqlite.SQLiteDatabase;

public class DatabaseManage {
    public static final String DICTIONARY_PATH = "/data/data/com.pzhu.english/database/dictionary.db";
    public static final String DICTIONARY_ENGLISH = "Word";
    public static final String DICTIONARY_CHINESE = "meaning";
    public static final String DICTIONARY_EXAMPLE = "lx";
    public static final String DICTIONARY_TABLE = "words";

    public static SQLiteDatabase getDatabase(){
        return SQLiteDatabase.openOrCreateDatabase(DICTIONARY_PATH,null);
    }
}
