package com.github.vasiliz.vkclient.news;

import com.github.vasiliz.vkclient.VkApplication;
import com.github.vasiliz.vkclient.base.db.config.AppDB;
import com.github.vasiliz.vkclient.base.services.IAbstractTask;
import com.github.vasiliz.vkclient.base.services.IDataExecutorService;
import com.github.vasiliz.vkclient.base.streams.HttpInputStreamProvider;
import com.github.vasiliz.vkclient.base.utils.IOUtils;
import com.github.vasiliz.vkclient.news.entity.Response;
import com.github.vasiliz.vkclient.news.observer.Observable;
import com.github.vasiliz.vkclient.news.observer.Observer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

public class NewsModel extends IAbstractTask<Response> implements Observable<Response> {
    private final Collection<Observer> mObservers = new ArrayList<>();

    private static final String TAG = NewsModel.class.getSimpleName();
    private final IMainPresenter mIMainPresenter;
    private AppDB mAppDB = VkApplication.getAppDB();


    NewsModel(final IDataExecutorService pIDataExecutorService, final IMainPresenter pIMainPresenter) {
        super(pIDataExecutorService);
        mIMainPresenter = pIMainPresenter;

    }

    @Override
    public Response executeLocal() {
        return mAppDB.getSaveData();
    }

    @Override
    public Response executeNetwork() {

        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            inputStream = new HttpInputStreamProvider().get("https://api.vk.com/method/newsfeed.get?filters=post&access_token=5401b44b25958826793dc6ca3ea87f17958cf7ba4a316af4c89acdb5c2d24559f090d8a2511f336149de6&v=5.69");
            byteArrayOutputStream = new ByteArrayOutputStream();
            int res = inputStream.read();
            while (res != -1) {
                byteArrayOutputStream.write(res);
                res = inputStream.read();
            }
        } catch (final IOException pE) {
            pE.fillInStackTrace();
        } finally {
            IOUtils.closeStream(inputStream);
        }
        return jsonToObject(byteArrayOutputStream != null ? byteArrayOutputStream.toString() : null);
    }

    @Override
    public void saveToCache(final Response data) {

        mAppDB.writeData(data);
    }

    @Override
    public void postExecute(final Response pResponse) {
        if (pResponse!=null) {
            notifyObservers(pResponse);
        }
    }

    @Override
    public void registerObserver(final Observer pObserver) {
        mObservers.add(pObserver);
    }

    @Override
    public void notifyObservers(final Response message) {
        for (final Observer observer:mObservers){
            observer.notification(message);
        }
    }

    private Response jsonToObject(final String pResponseNews) {
        final Gson gson = new GsonBuilder().create();
        return gson.fromJson(pResponseNews, Response.class);
    }
}
