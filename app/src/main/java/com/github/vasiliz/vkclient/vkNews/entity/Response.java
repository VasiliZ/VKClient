package com.github.vasiliz.vkclient.vkNews.entity;

import com.google.gson.annotations.SerializedName;

public class Response {
    @SerializedName("response")
    private ResponseNews mResponseNews;

    public Response() {
    }

    public ResponseNews getResponseNews() {
        return mResponseNews;
    }

    public void setResponseNews(final ResponseNews pResponseNews) {
        mResponseNews = pResponseNews;
    }


}
