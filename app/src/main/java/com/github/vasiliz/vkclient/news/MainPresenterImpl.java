package com.github.vasiliz.vkclient.news;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import com.github.vasiliz.vkclient.base.services.ExecutorDataServiceImpl;
import com.github.vasiliz.vkclient.mymvp.VkPresenter;
import com.github.vasiliz.vkclient.news.entity.Item;
import com.github.vasiliz.vkclient.news.entity.Response;
import com.github.vasiliz.vkclient.news.ui.IMainView;
import com.github.vasiliz.vkclient.news.ui.adapters.NewsAdapter;
import com.github.vasiliz.vkclient.news.ui.adapters.NewsViewHolder;

public class MainPresenterImpl extends VkPresenter<IMainView> implements IMainPresenter, LifecycleObserver {

    private static final String TAG = MainPresenterImpl.class.getSimpleName();
    private IMainView mINewsView;
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

    @Override
    public void doLike(Item pItem) {
        mNewsModel.doLike(pItem);
    }

    @Override
    public void showToast() {
        mINewsView.showNotify();
    }

    @Override
    public void registredObsertver(final NewsViewHolder pNewsViewHolder) {
        mNewsModel.registerLikeOnserver(pNewsViewHolder);
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void destroy() {
        if (mINewsView != null) {
            mINewsView.getLifecycle().removeObserver(this);
        }
        mINewsView = null;
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
