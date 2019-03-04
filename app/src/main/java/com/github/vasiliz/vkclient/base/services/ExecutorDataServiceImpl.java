package com.github.vasiliz.vkclient.base.services;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

public final class ExecutorDataServiceImpl implements IDataExecutorService {

    private static ExecutorDataServiceImpl mExecutorDataServiceImpl;
    private final ThreadPoolExecutor mNetworkExecutor;
    private final Executor mDataBaseTask;

    private ExecutorDataServiceImpl() {
        mDataBaseTask = Executors.newSingleThreadExecutor();
        mNetworkExecutor = (ThreadPoolExecutor)
                Executors.newFixedThreadPool((int) (Runtime.getRuntime().availableProcessors()*1.5));
    }

    public static ExecutorDataServiceImpl getInstance() {
        if (mExecutorDataServiceImpl == null) {
            mExecutorDataServiceImpl = new ExecutorDataServiceImpl();
        }
        return mExecutorDataServiceImpl;
    }

    @Override
    public <T> void doNetworkTask(final INetworkTask<T> pTask) {
         mNetworkExecutor.execute(new Runnable() {

             @Override
             public void run() {
                 pTask.runNetwork();
             }
         });
    }

    @Override
    public <T> void doDatabaseTask(final IDataBaseTask<T> pDataBaseTask) {
        mDataBaseTask.execute(new Runnable() {

            @Override
            public void run() {
                pDataBaseTask.runLocal();
            }
        });
    }

    @Override
    public <T> void saveToCache(final ISaveToCacheTask<T> pSaveToCacheTask, final T data) {
        mDataBaseTask.execute(new Runnable() {

            @Override
            public void run() {
                pSaveToCacheTask.saveToCache(data);
            }
        });

    }

}
