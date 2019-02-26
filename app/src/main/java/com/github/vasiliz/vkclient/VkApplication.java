package com.github.vasiliz.vkclient;

import android.app.Application;

import com.github.vasiliz.vkclient.base.services.ExecutorDataServiceImpl;

public class VkApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        ExecutorDataServiceImpl.getInstance();
    }
}
