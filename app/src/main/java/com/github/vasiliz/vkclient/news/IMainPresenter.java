package com.github.vasiliz.vkclient.news;

import com.github.vasiliz.vkclient.news.entity.Response;

public interface IMainPresenter extends com.github.vasiliz.vkclient.news.observer.Observer<Response> {

    void loadNews();

    void  loadMoreNews();
}
