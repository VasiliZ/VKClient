package com.github.vasiliz.vkclient.base.services;

import android.os.Handler;
import android.os.Looper;

public abstract class IAbstractActionTask<T, V> implements INetworkTask<T>, ITask<T> {

    private final IDataExecutorService mExecutorDataService;

    public IAbstractActionTask(final IDataExecutorService pExecutorDataService) {
        mExecutorDataService = pExecutorDataService;
    }

    @Override
    public void runNetwork() {
        final T result = executeNetwork();
        try {
            if (result != null) {
                runOnUIThread(result);
            }
        } catch (Exception pE) {
            onError(pE);
        }
    }

    @Override
    public void doTask() {
        mExecutorDataService.doNetworkTask(this);
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

    @Override
    public T executeNetwork(final boolean isLoadMore) {
        return null;
    }

    @Override
    public void onError(final Throwable pThrowable) {

    }
    public abstract V merge(T pT);

    @Override
    public void doTask(boolean pLoadMore) {

    }

    @Override
    public void cancelTask() {

    }
}
