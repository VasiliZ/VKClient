package com.github.vasiliz.vkclient.news;

import com.github.vasiliz.vkclient.base.services.ExecutorDataServiceImpl;
import com.github.vasiliz.vkclient.mymvp.VkPresenter;
import com.github.vasiliz.vkclient.news.entity.Response;
import com.github.vasiliz.vkclient.news.ui.IMainView;

public class MainPresenterImpl extends VkPresenter<IMainView> implements IMainPresenter {

    private static final String TAG = MainPresenterImpl.class.getSimpleName();
    private IMainView mINewsView;
    private NewsModel mNewsModel;
    private Response mResponse;

    public MainPresenterImpl(final IMainView pINewsView) {
        mINewsView = pINewsView;
        mNewsModel = new NewsModel(ExecutorDataServiceImpl.getInstance(), this);
        mNewsModel.registerObserver(this);

    }

    @Override
    public void loadNews() {
        mINewsView.showProgress();
        mNewsModel.doTask();
    }

    @Override
    public void notification(final Response pMessage) {
        mINewsView.hideProgress();
        mINewsView.setDataToAdapter(pMessage);
    }
}
