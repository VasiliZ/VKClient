package com.github.vasiliz.vkclient.main;

public interface Callback<T> {
    T returnResult(T pResult);
}
