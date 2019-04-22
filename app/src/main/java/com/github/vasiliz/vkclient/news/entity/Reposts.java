package com.github.vasiliz.vkclient.news.entity;

import com.google.gson.annotations.SerializedName;

public class Reposts {
    @SerializedName("count")
    private long mCountReposts;
    @SerializedName("user_reposted")
    private int mUserReposted;

    public Reposts() {
    }

    public long getCountReposts() {
        return mCountReposts;
    }

    public int getUserReposted() {
        return mUserReposted;
    }
}
