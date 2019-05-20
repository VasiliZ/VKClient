package com.github.vasiliz.vkclient.news.observer;

public interface SetLikeObserver<T> {
    void addLike(T message);
}
