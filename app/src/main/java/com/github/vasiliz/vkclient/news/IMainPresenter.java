package com.github.vasiliz.vkclient.news;

import com.github.vasiliz.vkclient.news.entity.Response;

public interface IMainPresenter  {

    void loadNews();

    void getData(Response pResponse);
}
