package com.github.vasiliz.vkclient.news.observer;

import com.github.vasiliz.vkclient.news.entity.Response;

public interface LikeObservable {

    void registerObserver(LikeObserver pObserver);
    void notifyObservers(Response pT);

    void notifyAfterDBTask(Response pResponse);
}
