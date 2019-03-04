package com.github.vasiliz.vkclient;

import android.app.Application;
import android.util.Log;

import com.github.vasiliz.vkclient.base.db.config.AppDB;
import com.github.vasiliz.vkclient.base.db.config.VkDBConfig;
import com.github.vasiliz.vkclient.base.db.config.VkDatabaseCreator;
import com.github.vasiliz.vkclient.base.services.ExecutorDataServiceImpl;

public class VkApplication extends Application {

    private String TAG = VkApplication.class.getSimpleName();
    private  static AppDB mAppDB;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        ExecutorDataServiceImpl.getInstance();

        final VkDatabaseCreator vkDatabaseCreator = new VkDatabaseCreator();
        Log.d(TAG, "init: " + vkDatabaseCreator.getDatabaseName(AppDB.class));
        final VkDBConfig vkDBConfig = new VkDBConfig.Builder()
                .setContext(this)
                .setDbName(vkDatabaseCreator.getDatabaseName(AppDB.class))
                .setVersion(1)
                .setInnerCursorFactory(null)
                .build();

        mAppDB = AppDB.getInstanceDB(vkDBConfig, vkDatabaseCreator.getListQueryForCreateTable());
    }

    public static AppDB getAppDB(){
        return mAppDB;
    }
}
