package com.github.vasiliz.vkclient.base.services;

import android.support.annotation.WorkerThread;

public interface IDataBaseTask<T> {

    @WorkerThread
    T executeLocal();

    @WorkerThread
    void runLocal();

    void saveToCache(T pData);

}
