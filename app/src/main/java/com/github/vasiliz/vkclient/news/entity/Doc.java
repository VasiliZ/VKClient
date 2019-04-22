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

    public String getTitle() {
        return mTitle;
    }

    public String getUrl() {
        return mUrl;
    }

}
