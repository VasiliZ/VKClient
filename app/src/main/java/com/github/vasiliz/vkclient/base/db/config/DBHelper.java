package com.github.vasiliz.vkclient.base.db.config;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private VkDBConfig mVkDBConfig;
    private String mTableName;
    private List<String> mListOfQuery;

    public DBHelper(final VkDBConfig pVkDBConfig, final List<String> pListOfQuery) {
        super(pVkDBConfig.getContext(), pVkDBConfig.getDbName(), pVkDBConfig.getCursorFactory(), pVkDBConfig.getVersion());
        mVkDBConfig = pVkDBConfig;
        mTableName = pVkDBConfig.getDbName();
        mListOfQuery = pListOfQuery;

    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        for (int i = 0; i < mListOfQuery.size(); i++) {
            db.execSQL(mListOfQuery.get(i));
        }
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {

    }
}

