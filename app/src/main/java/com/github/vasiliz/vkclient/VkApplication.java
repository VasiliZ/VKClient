package com.github.vasiliz.vkclient;

import android.app.Application;
import android.os.StrictMode;

import com.github.vasiliz.myimageloader.ImageLoader;
import com.github.vasiliz.vkclient.api.LoadMoreNews;
import com.github.vasiliz.vkclient.api.RequestNews;
import com.github.vasiliz.vkclient.base.api.CreateRequest;
import com.github.vasiliz.vkclient.base.db.config.AppDB;
import com.github.vasiliz.vkclient.base.db.config.DBHelper;
import com.github.vasiliz.vkclient.base.db.config.VkDBConfig;
import com.github.vasiliz.vkclient.base.db.config.VkDatabaseCreator;
import com.github.vasiliz.vkclient.base.services.ExecutorDataServiceImpl;

public class VkApplication extends Application {

    private String TAG = VkApplication.class.getSimpleName();
    private AppDB mAppDB;
    private static ImageLoader mImageLoader;
    private static CreateRequest mCreateRequest;
    private static CreateRequest mLoadMoreNews;
    private static DBHelper mDBHelper;

    @Override
    public void onCreate() {
        super.onCreate();

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .build());
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

        mDBHelper = DBHelper.getInstance(getApplicationContext(),
                vkDBConfig, vkDatabaseCreator.getListQueryForCreateTable());
        //init image loader
        mImageLoader = ImageLoader.getInstance();

        mCreateRequest = new CreateRequest.Builder()
                .setBaseUrl("https://api.vk.com/")
                .setServiceClass(RequestNews.class)
                .build();

        mLoadMoreNews = new CreateRequest.Builder()
                .setBaseUrl("https://api.vk.com/")
                .setServiceClass(LoadMoreNews.class)
                .build();
    }

    public static CreateRequest getmLoadMoreNewsApiTemplate() {
        return mLoadMoreNews;
    }

    public static CreateRequest getCreateRequest() {
        return mCreateRequest;
    }

    public static ImageLoader getmImageLoader() {
        return mImageLoader;
    }

    public static DBHelper getDBHelper() {
        return mDBHelper;
    }

}
