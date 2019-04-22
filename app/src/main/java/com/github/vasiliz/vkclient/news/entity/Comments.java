package com.github.vasiliz.vkclient.news.entity;

import com.google.gson.annotations.SerializedName;

public class Comments {

    @SerializedName("count")
    private long mCountComments;
    @SerializedName("can_post")
    private int mCanPost;

    public Comments() {
    }

    public long getCountComments() {
        return mCountComments;
    }

    public int getCanPost() {
        return mCanPost;
    }

}
