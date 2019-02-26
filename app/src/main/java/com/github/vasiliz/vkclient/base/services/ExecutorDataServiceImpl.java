package com.github.vasiliz.vkclient.base.services;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public final class ExecutorDataServiceImpl implements IDataExecutorService {

    private static ExecutorDataServiceImpl mExecutorDataServiceImpl;
    private final ScheduledThreadPoolExecutor mNetworkExecutor;
    private ScheduledFuture<?> mScheduledFuture;
    private final Executor mDataBaseTask;

    private ExecutorDataServiceImpl() {
        mDataBaseTask = Executors.newSingleThreadExecutor();
        mNetworkExecutor = new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors());
    }

    public static ExecutorDataServiceImpl getInstance() {
        if (mExecutorDataServiceImpl == null) {
            mExecutorDataServiceImpl = new ExecutorDataServiceImpl();
        }
        return mExecutorDataServiceImpl;
    }

    @Override
    public <T> void doNetworkTask(final INetworkTask<T> pTask) {
        mScheduledFuture = mNetworkExecutor.scheduleAtFixedRate(
                new Runnable() {

                    @Override
                    public void run() {
                        pTask.runNetwork();
                    }
                },
                0,
                pTask.getPeriod(),
                pTask.getTimeUnit());
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

    @Override
    public void cancelNetworkTask() {
        if (mScheduledFuture != null) {
            mScheduledFuture.cancel(true);
        }
    }

}
