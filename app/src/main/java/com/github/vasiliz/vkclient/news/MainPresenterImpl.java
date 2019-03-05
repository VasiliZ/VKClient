package com.github.vasiliz.vkclient.news;

import com.github.vasiliz.vkclient.base.services.ExecutorDataServiceImpl;
import com.github.vasiliz.vkclient.news.entity.Response;
import com.github.vasiliz.vkclient.news.ui.IMainView;
import com.github.vasiliz.vkclient.mymvp.VkPresenter;

public class MainPresenterImpl extends VkPresenter<IMainView> implements IMainPresenter {

    private static final String TAG =  MainPresenterImpl.class.getSimpleName();
    private IMainView mINewsView;
    private NewsModel mNewsModel;



    public MainPresenterImpl(final IMainView pINewsView) {
            mINewsView = pINewsView;
            mNewsModel = new NewsModel(ExecutorDataServiceImpl.getInstance(), this);
    }

    @Override
    public void loadNews() {
        mINewsView.showProgress();
        mNewsModel.doTask();
    }

    public void getData(final Response pResponse){
        if (pResponse!=null){
            mINewsView.hideProgress();
        }

    }
}
