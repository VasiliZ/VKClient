package com.github.vasiliz.vkclient;

import android.app.Application;
import android.util.Log;

import com.github.vasiliz.myimageloader.ImageLoader;
import com.github.vasiliz.vkclient.base.db.config.AppDB;
import com.github.vasiliz.vkclient.base.db.config.VkDBConfig;
import com.github.vasiliz.vkclient.base.db.config.VkDatabaseCreator;
import com.github.vasiliz.vkclient.base.services.ExecutorDataServiceImpl;

public class VkApplication extends Application {

    private String TAG = VkApplication.class.getSimpleName();
    private  static AppDB mAppDB;
    private static ImageLoader mImageLoader;

    @Override
    public void onCreate() {
        super.onCreate();
        init();

    }

    private void init() {
        //init executors service
        ExecutorDataServiceImpl.getInstance();
        //init and create table if it possible
        final VkDatabaseCreator vkDatabaseCreator = new VkDatabaseCreator();
        final VkDBConfig vkDBConfig = new VkDBConfig.Builder()
                .setContext(this)
                .setDbName(vkDatabaseCreator.getDatabaseName(AppDB.class))
                .setVersion(1)
                .setInnerCursorFactory(null)
                .build();
        
        mAppDB = AppDB.getInstanceDB(vkDBConfig, vkDatabaseCreator.getListQueryForCreateTable());
        //init image loader
        mImageLoader = ImageLoader.getInstance();
    }

    public static AppDB getAppDB(){
        return mAppDB;
    }

    public static ImageLoader getmImageLoader(){
        return mImageLoader;
    }
}
