package com.github.vasiliz.vkclient.news.observer;

public interface Observer<T> {
   void notification(T message);

}
