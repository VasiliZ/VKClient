package com.github.vasiliz.vkclient.news;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import com.github.vasiliz.vkclient.base.services.ExecutorDataServiceImpl;
import com.github.vasiliz.vkclient.mymvp.VkPresenter;
import com.github.vasiliz.vkclient.news.entity.Response;
import com.github.vasiliz.vkclient.news.ui.IMainView;

public class MainPresenterImpl extends VkPresenter<IMainView> implements IMainPresenter, LifecycleObserver {

    private static final String TAG = MainPresenterImpl.class.getSimpleName();
    private final IMainView mINewsView;
    private final NewsModel mNewsModel;
    private String mNexttNews;

    public MainPresenterImpl(final IMainView pINewsView, final String pAccessKey) {
        mINewsView = pINewsView;
        mNewsModel = new NewsModel(ExecutorDataServiceImpl.getInstance(), pAccessKey, this);
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
    public Response getSavingData() {
        return mNewsModel.getSaveNews();
    }

    @Override
    public void goToLogin() {
        mINewsView.goToLogin();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void destroy() {
        if (mINewsView != null) {
            mINewsView.getLifecycle().removeObserver(this);
        }
    }


    @Override
    public void notification(final Response pMessage) {
        if (pMessage != null) {
            mNewsModel.saveData(pMessage);
            mNexttNews = pMessage.getResponseNews().getNextNews();
            mINewsView.setDataToAdapter(pMessage);
            mINewsView.dataLastPage(true);
            mINewsView.loadMoreData(false);
            if (mINewsView.handleSwipe() != null) {
                mINewsView.handleSwipe().setRefreshing(false);
            }
        } else {
            mINewsView.onLoadError();
        }
    }


}
