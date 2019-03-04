package com.github.vasiliz.vkclient.base.services;

import android.os.Handler;
import android.os.Looper;

public abstract class IAbstractCacheTask<T>
        implements
        ITask<T>,
        INetworkTask<T>,
        IDataBaseTask<T>,
        ISaveToCacheTask<T> {

    private final IDataExecutorService mIDataExecutorService;

    public IAbstractCacheTask(final IDataExecutorService pIDataExecutorService) {
        mIDataExecutorService = pIDataExecutorService;

    }

    @Override
    public void runNetwork() {
        try {
            final T result = executeNetwork();
            if (result != null) {
                updateCache(result);
                runOnUIThread(result);
            }else {
                runLocal();
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

    private void updateCache(final T result) {
        mIDataExecutorService.saveToCache(this, result);
    }

    @Override
    public void cancelTask() {
        //stub
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
