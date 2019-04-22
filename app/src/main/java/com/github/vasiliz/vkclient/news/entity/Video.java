package com.github.vasiliz.vkclient.news.entity;

import com.google.gson.annotations.SerializedName;

public class Video {

    @SerializedName("src")
    private String mSource;
    @SerializedName("photo_800")
    private String mUrlPhotoVideo;

    public String getSource() {
        return mSource;
    }

    public String getUrlPhotoVideo() {
        return mUrlPhotoVideo;
    }

}
