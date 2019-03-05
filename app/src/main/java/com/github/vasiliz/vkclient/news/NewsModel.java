package com.github.vasiliz.vkclient.news;

import com.github.vasiliz.vkclient.VkApplication;
import com.github.vasiliz.vkclient.base.services.IAbstractTask;
import com.github.vasiliz.vkclient.base.services.IDataExecutorService;
import com.github.vasiliz.vkclient.base.streams.HttpInputStreamProvider;
import com.github.vasiliz.vkclient.base.utils.IOUtils;
import com.github.vasiliz.vkclient.news.entity.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class NewsModel extends IAbstractTask<Response> {

    private static final String TAG = NewsModel.class.getSimpleName();
    private IMainPresenter mIMainPresenter;


    public NewsModel(final IDataExecutorService pIDataExecutorService, final IMainPresenter pIMainPresenter) {
        super(pIDataExecutorService);
        mIMainPresenter = pIMainPresenter;

    }

    @Override
    public Response executeLocal() {
        return VkApplication.getAppDB().getSaveData();
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
        VkApplication.getAppDB().writeData(data);
    }

    @Override
    public void postExecute(final Response pResponse) {
        mIMainPresenter.getData(pResponse);
    }


    private Response jsonToObject(final String pResponseNews) {
        final Gson gson = new GsonBuilder().create();
        return gson.fromJson(pResponseNews, Response.class);
    }
}
