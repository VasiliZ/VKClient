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

    public Video setSource(final String pSource) {
        mSource = pSource;
        return this;
    }

    public String getUrlPhotoVideo() {
        return mUrlPhotoVideo;
    }

    public Video setUrlPhotoVideo(final String pUrlPhotoVideo) {
        mUrlPhotoVideo = pUrlPhotoVideo;
        return this;
    }
}
