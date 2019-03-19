package com.github.vasiliz.vkclient.news;

import android.support.v7.widget.RecyclerView;

import com.github.vasiliz.vkclient.base.services.ExecutorDataServiceImpl;
import com.github.vasiliz.vkclient.mymvp.VkPresenter;
import com.github.vasiliz.vkclient.news.entity.Response;
import com.github.vasiliz.vkclient.news.ui.IMainView;

public class MainPresenterImpl extends VkPresenter<IMainView> implements IMainPresenter {

    private static final String TAG = MainPresenterImpl.class.getSimpleName();
    private IMainView mINewsView;
    private NewsModel mNewsModel;
    private String mNexttNews;

    public MainPresenterImpl(final IMainView pINewsView) {
        mINewsView = pINewsView;
        mNewsModel = new NewsModel(ExecutorDataServiceImpl.getInstance());
        mNewsModel.registerObserver(this);

    }

    @Override
    public void loadNews() {
        mNewsModel.doTask();
    }

    @Override
    public void loadMoreNews() {
        mNewsModel.getStringForNextScope(mNexttNews);
        mNewsModel.doTask(true);
    }

    @Override
    public void notification(final Response pMessage) {
        mNexttNews = pMessage.getResponseNews().getNextNews();
        mINewsView.setDataToAdapter(pMessage);
        mINewsView.dataLastPage(true);
        mINewsView.loadMoreData(false);
    }
}
