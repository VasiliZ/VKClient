package com.github.vasiliz.vkclient.base.services;

import android.os.Handler;
import android.os.Looper;

public abstract class IAbstractTask<T>
        implements
        ITask<T>,
        INetworkTask<T>,
        IDataBaseTask<T>,
        ISaveToCacheTask<T> {

    private final IDataExecutorService mIDataExecutorService;
    private T mResult;
    private boolean isLoadMoreData;

    public IAbstractTask(final IDataExecutorService pIDataExecutorService) {
        mIDataExecutorService = pIDataExecutorService;

    }

    @Override
    public void runNetwork() {
        try {
            mResult = executeNetwork(isLoadMoreData);
            if (mResult != null) {
                saveToCache(mResult);
                runOnUIThread(mResult);
            } else {
                runLocal();
            }
        } catch (final Exception e) {
            e.fillInStackTrace();
            onError(e);
        }
    }

    @Override
    public void runLocal() {
        try {
            final T result = executeLocal();
            if (result != null) {
                runOnUIThread(result);
            }
        } catch (final Exception e) {
            onError(e);
        }
    }

    @Override
    public void onError(final Throwable pThrowable) {
        pThrowable.getLocalizedMessage();
    }

    @Override
    public void doTask() {
        mIDataExecutorService.doNetworkTask(this);
    }

    @Override
    public void saveToCache(final T pData) {
        mIDataExecutorService.doSaveToCacheTask(this, pData);
    }

    public void doTask(boolean pLoadMore){
        isLoadMoreData = pLoadMore;
        mIDataExecutorService.doNetworkTask(this);
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
