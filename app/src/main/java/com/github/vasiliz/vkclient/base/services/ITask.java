package com.github.vasiliz.vkclient.base.services;

import android.support.annotation.WorkerThread;

public interface ITask<T> {

    @WorkerThread
    void onError();

    @WorkerThread
    void doTask();

    void postExecute(T pT);

    void cancelTask();

}
