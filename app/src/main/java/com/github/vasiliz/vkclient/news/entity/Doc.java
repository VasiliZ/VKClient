package com.github.vasiliz.vkclient.news.entity;

import com.google.gson.annotations.SerializedName;

public class Doc {

    @SerializedName("title")
    private String mTitle;
    @SerializedName("url")
    private String mUrl;

    @SerializedName("preview")
    private Preview mPreview;

    public Preview getPreview() {
        return mPreview;
    }

    public Doc setPreview(Preview pPreview) {
        mPreview = pPreview;
        return this;
    }

    public String getTitle() {
        return mTitle;
    }

    public Doc setTitle(String pTitle) {
        mTitle = pTitle;
        return this;
    }

    public String getUrl() {
        return mUrl;
    }

    public Doc setUrl(String pUrl) {
        mUrl = pUrl;
        return this;
    }
}
