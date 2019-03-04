package com.github.vasiliz.vkclient.main;

import com.github.vasiliz.vkclient.main.entity.Response;

public interface IMainPresenter  {

    void loadNews();

    void getData(Response pResponse);
}
