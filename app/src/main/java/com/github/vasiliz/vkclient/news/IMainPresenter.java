package com.github.vasiliz.vkclient.news;

import com.github.vasiliz.vkclient.news.entity.Item;
import com.github.vasiliz.vkclient.news.entity.Response;
import com.github.vasiliz.vkclient.news.ui.adapters.holders.NewsViewHolder;

public interface IMainPresenter extends com.github.vasiliz.vkclient.news.observer.Observer<Response> {

    void loadNews();

    void loadMoreNews();

    Response getSavingData();

    void goToLogin();

    void doLike(Item pItem);

    void showToast();

}
