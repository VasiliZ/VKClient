package com.github.vasiliz.vkclient.base.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.github.vasiliz.vkclient.vkNews.entity.Attachment;
import com.github.vasiliz.vkclient.vkNews.entity.Groups;
import com.github.vasiliz.vkclient.vkNews.entity.Item;
import com.github.vasiliz.vkclient.vkNews.entity.Profile;

import java.util.List;

@Database(databaseName = "mydbVk",
        entity = {Profile.class,
                Item.class,
                Groups.class,
                Attachment.class})
public class AppDB extends SQLiteOpenHelper {

    private VkDBConfig mVkDBConfig;
    private String mTableName;
    private List<String> mListOfQuery;

    public AppDB(final VkDBConfig pVkDBConfig, final List<String> pListOfQuery) {
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
