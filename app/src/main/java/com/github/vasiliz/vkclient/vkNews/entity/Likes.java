package com.github.vasiliz.vkclient.vkNews.entity;

import com.google.gson.annotations.SerializedName;

public class Likes {
    @SerializedName("count")
    private long mCountLike;
    @SerializedName("user_likes")
    private int mUserLike;
    @SerializedName("can_like")
    private int mCanLike;

    public Likes() {
    }

    public long getCountLike() {
        return mCountLike;
    }

    public void setCountLike(final long pCountLike) {
        mCountLike = pCountLike;
    }

    public int getUserLike() {
        return mUserLike;
    }

    public void setUserLike(final int pUserLike) {
        mUserLike = pUserLike;
    }

    public int getCanLike() {
        return mCanLike;
    }

    public void setCanLike(final int pCanLike) {
        mCanLike = pCanLike;
    }
}
