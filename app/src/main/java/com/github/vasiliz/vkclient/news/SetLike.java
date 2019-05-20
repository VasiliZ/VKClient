package com.github.vasiliz.vkclient.news;

import com.github.vasiliz.vkclient.VkApplication;
import com.github.vasiliz.vkclient.base.services.IAbstractActionTask;
import com.github.vasiliz.vkclient.base.services.IDataExecutorService;
import com.github.vasiliz.vkclient.base.streams.HttpInputStreamProvider;
import com.github.vasiliz.vkclient.base.utils.ConstantStrings;
import com.github.vasiliz.vkclient.base.utils.IOUtils;
import com.github.vasiliz.vkclient.news.entity.Item;
import com.github.vasiliz.vkclient.news.entity.Response;
import com.github.vasiliz.vkclient.news.observer.LikeObservable;
import com.github.vasiliz.vkclient.news.observer.SetLikeObserver;
import com.github.vasiliz.vkclient.news.ui.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;


public class SetLike extends IAbstractActionTask<Response, Result> implements LikeObservable<Response> {
    private final Item mItem;
    private final String mAccessToken;
    private final String TAG = "TAGAGA";
    private final NewsModel mNewsModel;
    private final Collection<SetLikeObserver> mObservers = new ArrayList<SetLikeObserver>();


    SetLike(final String pAccessToken, final Item pItem, final NewsModel pNewsModel, final IDataExecutorService pExecutorDataService) {
        super(pExecutorDataService);
        mAccessToken = pAccessToken;
        mItem = pItem;
        mNewsModel = pNewsModel;
    }

    @Override
    public Response executeNetwork() {
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
                mNewsModel.errorDoLike();
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

    private Response jsonToObject(final String pJson) {
        final Gson gson = new GsonBuilder().create();
        return gson.fromJson(pJson, Response.class);

    }


    @Override
    public void registerObserver(final SetLikeObserver pObserver) {
        mObservers.add(pObserver);
    }

    @Override
    public void notifyObservers(final Response message) {
        for (final SetLikeObserver setLikeObserver : mObservers) {
            merge(message);
            setLikeObserver.addLike(message);
        }
    }

    @Override
    public Result merge(Response pResponse) {
     /*  Result result = new Result();
       result.*/
        return null;
    }
}
