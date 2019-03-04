package com.github.vasiliz.vkclient.base.db.config;

public interface IDbContract<T> {

    <T> void setData(T pData);

}
