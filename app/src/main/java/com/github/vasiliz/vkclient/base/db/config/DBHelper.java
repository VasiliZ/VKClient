package com.github.vasiliz.vkclient.base.db.config;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

public class DBHelper extends SQLiteOpenHelper {


    private List<String> mListOfQuery;
    private static DBHelper INSTANCE;
    private String TAG = DBHelper.class.getSimpleName();

    private DBHelper(final Context pContext, final VkDBConfig pVkDBConfig, final List<String> pListOfQuery) {
        super(pContext, pVkDBConfig.getDbName(), pVkDBConfig.getCursorFactory(), pVkDBConfig.getVersion());
        mListOfQuery = pListOfQuery;
    }

    public static DBHelper getInstance(final Context pContext, final VkDBConfig pVkDBConfig, final List<String> pListOfQuery) {
        if (INSTANCE == null) {
            INSTANCE = new DBHelper(pContext, pVkDBConfig, pListOfQuery);
        }
        return INSTANCE;
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        try {
            db.beginTransaction();
            for (int i = 0; i < mListOfQuery.size(); i++) {
                Log.d(TAG, "onCreate: " + mListOfQuery.get(i));
                db.execSQL(mListOfQuery.get(i));
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (final SQLException e) {
            e.fillInStackTrace();
        }
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {

    }
}

