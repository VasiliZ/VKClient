package com.github.vasiliz.vkclient.base.db.config;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public final class VkDBConfig {
    private String mDbName;
    private List<String> listQueryForCreateTables;
    private SQLiteDatabase.CursorFactory mCursorFactory;
    private int mVersion;
    private Context mContext;

    public Context getContext() {
        return mContext;
    }

    String getDbName() {
        return mDbName;
    }

    public int getVersion() {
        return mVersion;
    }

    SQLiteDatabase.CursorFactory getCursorFactory() {
        return mCursorFactory;
    }

    private VkDBConfig() {

    }

    public static class Builder {

        Context mContext;
        String mDbName;
        int mVersion;
        List<String> listOfQuery;
        SQLiteDatabase.CursorFactory mInnerCursorFactory;

        public Builder setContext(final Context pContext) {
            mContext = pContext;
            return this;
        }

        public Builder setDbName(final String pDbName) {
            mDbName = pDbName;
            return this;
        }

        public Builder setVersion(final int pVersion) {
            mVersion = pVersion;
            return this;
        }

        public Builder setInnerCursorFactory(final SQLiteDatabase.CursorFactory pInnerCursorFactory) {
            mInnerCursorFactory = pInnerCursorFactory;
            return this;
        }

        public Builder setListOfQuery(final List<String> pListOfQuery) {
            listOfQuery = pListOfQuery;
            return this;
        }

        public VkDBConfig build() {
            final VkDBConfig vkDBConfig = new VkDBConfig();
            vkDBConfig.mContext = this.mContext;
            vkDBConfig.mDbName = this.mDbName;
            vkDBConfig.mVersion = this.mVersion;
            vkDBConfig.mCursorFactory = this.mInnerCursorFactory;
            vkDBConfig.listQueryForCreateTables = this.listOfQuery;
            return vkDBConfig;
        }
    }

}