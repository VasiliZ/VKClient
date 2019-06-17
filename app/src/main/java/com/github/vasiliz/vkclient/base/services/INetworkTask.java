package com.github.vasiliz.vkclient.base.services;

import android.support.annotation.WorkerThread;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public interface INetworkTask<T> {


    @WorkerThread
    T executeNetwork(boolean isLoadMore);

    @WorkerThread
    void runNetwork();

}
