package com.github.vasiliz.vkclient.base.services;

public interface IDataExecutorService {

    <T> void doNetworkTask(final INetworkTask<T> pTask);

    <T> void doDatabaseTask(final IDataBaseTask<T> pDataBaseTask);

    <T> void doSaveToCacheTask(final ISaveToCacheTask<T> pSaveToCacheTask, T data);
}
