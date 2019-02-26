package com.github.vasiliz.vkclient.base.services;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.TimeUnit;

public abstract class IAbstractCacheTask<T>
        implements
        ITask<T>,
        INetworkTask<T>,
        IDataBaseTask<T>,
        ISaveToCacheTask<T> {

    private final IDataExecutorService mIDataExecutorService;
    private final int mPeriod;
    private final TimeUnit mTimeUnit;

    public IAbstractCacheTask(final IDataExecutorService pIDataExecutorService,
                              final int pPeriod,
                              final TimeUnit pTimeUnit) {
        mIDataExecutorService = pIDataExecutorService;
        mPeriod = pPeriod;
        mTimeUnit = pTimeUnit;
    }

    @Override
    public void runNetwork() {
        try {
            final T result = executeNetwork();
            if (result != null) {
                updateCache(result);
                runOnUIThread(result);
            } else {

            }
        } catch (final Exception e) {
            mIDataExecutorService.doDatabaseTask(this);
            onError();
        }
    }

    @Override
    public void runLocal() {
        try {
            final T result = executeLocal();
            if (result != null) {
                updateCache(result);
            }
        } catch (final Exception e) {
            onError();
        }
    }

    @Override
    public void onError() {

    }

    @Override
    public void doTask() {
        mIDataExecutorService.doNetworkTask(this);
    }

    @Override
    public int getPeriod() {
        return mPeriod;
    }

    @Override
    public TimeUnit getTimeUnit() {
        return mTimeUnit;
    }

    private void updateCache(final T result) {
        mIDataExecutorService.saveToCache(this, result);
    }

    @Override
    public void cancelTask() {
        mIDataExecutorService.cancelNetworkTask();
    }

    private void runOnUIThread(final T result) {
        final Handler handler = new Handler(Looper.getMainLooper());
        final Runnable runnable = new Runnable() {

            @Override
            public void run() {
                postExecute(result);
            }
        };
        handler.post(runnable);
    }

}
