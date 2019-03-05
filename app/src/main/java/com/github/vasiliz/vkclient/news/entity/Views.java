package com.github.vasiliz.vkclient.news.entity;

import com.google.gson.annotations.SerializedName;

public class Views {

    @SerializedName("views")
    private long mCountViews;

    public Views() {
    }

    public long getCountViews() {
        return mCountViews;
    }

    public void setCountViews(final long pCountViews) {
        mCountViews = pCountViews;
    }
}
