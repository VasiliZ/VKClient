package com.github.vasiliz.vkclient.news;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import com.github.vasiliz.vkclient.base.db.config.AppDB;
import com.github.vasiliz.vkclient.base.services.ExecutorDataServiceImpl;
import com.github.vasiliz.vkclient.mymvp.VkPresenter;
import com.github.vasiliz.vkclient.news.entity.Item;
import com.github.vasiliz.vkclient.news.entity.Response;
import com.github.vasiliz.vkclient.news.observer.LikeObserver;
import com.github.vasiliz.vkclient.news.ui.IMainView;

public class MainPresenterImpl extends VkPresenter<IMainView> implements IMainPresenter, LifecycleObserver, LikeObserver<Response> {

    private static final String TAG = MainPresenterImpl.class.getSimpleName();
    private IMainView mINewsView;
    private final NewsModel mNewsModel;
    private String mNexttNews;
    private LikeItemModel mLikeItemModel;
    private final String mAccessKey;

    public MainPresenterImpl(final IMainView pINewsView, final String pAccessKey) {
        mINewsView = pINewsView;
        mNewsModel = new NewsModel(ExecutorDataServiceImpl.getInstance(), pAccessKey, this);
        mNewsModel.registerObserver(this);

        mAccessKey = pAccessKey;


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
    public void doLike(final Item pItem) {

        mLikeItemModel = new LikeItemModel(ExecutorDataServiceImpl.getInstance(), mAccessKey, pItem);
        mLikeItemModel.registerObserver(this);
        mLikeItemModel.doTask();
    }

    @Override
    public void showToast() {
        mINewsView.showNotify();
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


    @Override
    public void notifyAfterLikedItem() {
        mLikeItemModel.databaseTask();
    }

    @Override
    public void notifyAfterComplitedDatabaseTask(final Response pResponse) {
        mINewsView.setDataToAdapter(pResponse);
    }
}
