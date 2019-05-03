package com.github.vasiliz.vkclient.news.ui;

import android.support.v4.widget.SwipeRefreshLayout;

import com.github.vasiliz.vkclient.mymvp.VkBaseView;
import com.github.vasiliz.vkclient.news.entity.Response;

public interface IMainView extends VkBaseView {


    void setDataToAdapter(Response pResponse);

    void dataLastPage(boolean pIsReady);

    void loadMoreData(boolean pIsLoading);

    SwipeRefreshLayout handleSwipe();

    void onLoadError();

    void goToLogin();

}
