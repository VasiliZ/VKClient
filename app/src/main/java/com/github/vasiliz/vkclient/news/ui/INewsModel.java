package com.github.vasiliz.vkclient.news.ui;

import com.github.vasiliz.vkclient.news.entity.Item;

public interface INewsModel {

    void doLike(Item pItem);

    void errorDoLike();

}
