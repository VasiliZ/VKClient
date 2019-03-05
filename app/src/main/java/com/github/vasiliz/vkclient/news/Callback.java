package com.github.vasiliz.vkclient.news;

public interface Callback<T> {
    T returnResult(T pResult);
}
