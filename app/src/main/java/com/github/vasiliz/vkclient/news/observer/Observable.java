package com.github.vasiliz.vkclient.news.observer;

public interface Observable<T> {

    void registerObserver(Observer pObserver);
    void notifyObservers(T message);

}
