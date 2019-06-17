package com.github.vasiliz.vkclient.news;

import com.github.vasiliz.vkclient.VkApplication;
import com.github.vasiliz.vkclient.base.db.config.AppDB;
import com.github.vasiliz.vkclient.base.services.IAbstractTask;
import com.github.vasiliz.vkclient.base.services.IDataExecutorService;
import com.github.vasiliz.vkclient.base.streams.HttpInputStreamProvider;
import com.github.vasiliz.vkclient.base.utils.ConstantStrings;
import com.github.vasiliz.vkclient.base.utils.IOUtils;
import com.github.vasiliz.vkclient.news.entity.Item;
import com.github.vasiliz.vkclient.news.entity.Response;
import com.github.vasiliz.vkclient.news.observer.LikeObservable;
import com.github.vasiliz.vkclient.news.observer.LikeObserver;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

class LikeItemModel extends IAbstractTask<Item, Response> implements LikeObservable {
    private final String mAccessToken;

    private final Collection<LikeObserver> mObservers = new ArrayList<LikeObserver>();
    private final Item mItem;
    private final AppDB mAppDB = AppDB.getAppDBInstance();


    public LikeItemModel(final IDataExecutorService pIDataExecutorService, final String pAccessToken, final Item pItem) {
        super(pIDataExecutorService);
        mAccessToken = pAccessToken;
        mItem = pItem;

    }

    @Override
    public Response executeLocal() {
        return mAppDB.getSaveData();
    }

    @Override
    public Response executeNetwork(final boolean isLoadMore) {
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        final String templateApi = VkApplication.getmDoLike().getTamplateApiString();

        try {
            inputStream = new HttpInputStreamProvider()
                    .get(String.format(templateApi,
                            String.valueOf(mItem.getPostType()),
                            String.valueOf(mItem.getPostId()),
                            String.valueOf(mItem.getSourseId()),
                            mAccessToken,
                            ConstantStrings.ApiVK.API_VERSION));

            byteArrayOutputStream = new ByteArrayOutputStream();
            int res = inputStream.read();
            while (res != -1) {
                byteArrayOutputStream.write(res);
                res = inputStream.read();
            }

            if (byteArrayOutputStream.toString().contains(ConstantStrings.ApiVK.ERROR)) {
                //todo handle error
            }
        } catch (final Exception pE) {
            pE.fillInStackTrace();
        } finally {
            IOUtils.closeStream(inputStream);
        }
        return jsonToObject(byteArrayOutputStream != null ? byteArrayOutputStream.toString() : null);
    }

    @Override
    public void postExecute(final Response pResponse) {
        if (pResponse != null) {
            notifyObservers(pResponse);
        }
    }

    @Override
    public void postDataBaseExecute(final Response pResponse) {
        if (pResponse.getResponseNews() != null) {
            notifyAfterDBTask(pResponse);
        }
    }


    private Response jsonToObject(final String pJson) {
        final Gson gson = new GsonBuilder().create();
        return gson.fromJson(pJson, Response.class);

    }

    @Override
    public void registerObserver(final LikeObserver pObserver) {
        mObservers.add(pObserver);
    }

    @Override
    public void notifyObservers(final Response pResponse) {
        for (final LikeObserver likeObserver : mObservers) {
            likeObserver.notifyAfterLikedItem();
        }
    }

    @Override
    public void notifyAfterDBTask(final Response pResponse) {
        for (final LikeObserver observer : mObservers) {
            observer.notifyAfterComplitedDatabaseTask(pResponse);
        }
    }

    @Override
    public Item merge(final Response pResponse) {
        final Item item = mItem;
        item.getLikes().setCanLike(0);
        item.getLikes().setUserLike(1);
        item.getLikes().setCountLike(pResponse.getResponseNews().likes);
        return item;
    }

    @Override
    public void saveToCache(final Response pData) {
        mAppDB.updateItem(merge(pData));
    }


}
