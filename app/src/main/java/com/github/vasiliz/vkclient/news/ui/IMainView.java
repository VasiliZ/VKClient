package com.github.vasiliz.vkclient.news.ui;

import com.github.vasiliz.vkclient.mymvp.VkBaseView;
import com.github.vasiliz.vkclient.news.entity.Response;

public interface IMainView extends VkBaseView {

    void showProgress();

    void hideProgress();
    void setDataToAdapter(Response pResponse);

    void dataLastPage(boolean pIsReady);

    void loadMoreData(boolean pIsLoading);
}
