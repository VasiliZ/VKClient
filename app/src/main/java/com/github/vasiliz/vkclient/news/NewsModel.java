package com.github.vasiliz.vkclient.news;

import com.github.vasiliz.vkclient.VkApplication;
import com.github.vasiliz.vkclient.base.api.CreateRequest;
import com.github.vasiliz.vkclient.base.db.config.AppDB;
import com.github.vasiliz.vkclient.base.services.IAbstractTask;
import com.github.vasiliz.vkclient.base.services.IDataExecutorService;
import com.github.vasiliz.vkclient.base.streams.HttpInputStreamProvider;
import com.github.vasiliz.vkclient.base.utils.ConstantStrings;
import com.github.vasiliz.vkclient.base.utils.IOUtils;
import com.github.vasiliz.vkclient.news.entity.Response;
import com.github.vasiliz.vkclient.news.observer.Observable;
import com.github.vasiliz.vkclient.news.observer.Observer;
import com.github.vasiliz.vkclient.news.ui.INewsModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

public class NewsModel extends IAbstractTask<Object, Response> implements Observable<Response>, INewsModel {

    private final Collection<Observer> mObservers = new ArrayList<Observer>();

    private static final String TAG = NewsModel.class.getSimpleName();
    private final AppDB mAppDB = AppDB.getAppDBInstance();
    private final String mAccessToken;
    private String mNextFromNews;
    private Response mResponse;

    private final IMainPresenter mIMainPresenter;

    private final CreateRequest mCreateRequest = VkApplication.getCreateRequest();
    private final CreateRequest mLoadMoreNews = VkApplication.getmLoadMoreNewsApiTemplate();

    public NewsModel(final IDataExecutorService pIDataExecutorService, final String pAccessToken, final IMainPresenter pMainPresenter) {
        super(pIDataExecutorService);
        mIMainPresenter = pMainPresenter;
        mAccessToken = pAccessToken;
    }

    @Override
    public Response executeLocal() {
        return mAppDB.getSaveData();
    }



    @SuppressWarnings("BooleanParameter")
    @Override
    public Response executeNetwork(final boolean pLoadMore) {
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            if (pLoadMore) {
                final String templateApi = mLoadMoreNews.getTamplateApiString();
                inputStream = new HttpInputStreamProvider()
                        .get(String.format(templateApi,
                                ConstantStrings.ApiVK.FILTER_FOR_REQUEST,
                                mAccessToken,
                                mNextFromNews,
                                ConstantStrings.ApiVK.API_VERSION));
            } else {
                final String tamplateApi = mCreateRequest.getTamplateApiString();
                inputStream = new HttpInputStreamProvider()
                        .get(String.format(tamplateApi,
                                ConstantStrings.ApiVK.FILTER_FOR_REQUEST,
                                mAccessToken,
                                ConstantStrings.ApiVK.API_VERSION));
            }
            byteArrayOutputStream = new ByteArrayOutputStream();
            int res = inputStream.read();
            while (res != -1) {
                byteArrayOutputStream.write(res);
                res = inputStream.read();
            }
            final String ERROR = "error";
            if (byteArrayOutputStream.toString().contains(ERROR)) {
                mIMainPresenter.goToLogin();
                return null;
            }
        } catch (final IOException pE) {
            pE.fillInStackTrace();
        } finally {
            IOUtils.closeStream(inputStream);
        }


        return jsonToObject(byteArrayOutputStream != null ? byteArrayOutputStream.toString() : null);
    }

    void getStringForNextScope(final String pForNextNews) {
        mNextFromNews = pForNextNews;
    }

    @Override
    public Object merge(final Response pResponse) {
        return null;
    }

    @Override
    public void saveToCache(final Response data) {

        mAppDB.writeData(data);
    }

    @Override
    public void postExecute(final Response pResponse) {
        if (pResponse != null) {
            notifyObservers(pResponse);
        }
    }

    @Override
    public void postDataBaseExecute(final Response pResponse) {
        notifyObservers(pResponse);
    }

    @Override
    public void registerObserver(final Observer pObserver) {
        mObservers.add(pObserver);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void notifyObservers(final Response message) {
        for (final Observer observer : mObservers) {
            observer.notification(message);
        }
    }

    private Response jsonToObject(final String pResponseNews) {
        final Gson gson = new GsonBuilder().create();
        return gson.fromJson(pResponseNews, Response.class);
    }

    Response getSaveNews() {
        return mResponse;
    }

    void saveData(final Response pResponse) {
        mResponse = pResponse;
    }

    @Override
    public void errorDoLike() {
        mIMainPresenter.showToast();
    }
}
