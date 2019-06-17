package com.github.vasiliz.vkclient.news.observer;

public interface LikeObserver<T> {
    void notifyAfterLikedItem();
    void notifyAfterComplitedDatabaseTask(T pT);
}
