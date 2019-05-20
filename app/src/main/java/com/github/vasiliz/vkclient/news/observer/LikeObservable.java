package com.github.vasiliz.vkclient.news.observer;

public interface LikeObservable<T> {

    void registerObserver(SetLikeObserver pObserver);
    void notifyObservers(T message);
}
