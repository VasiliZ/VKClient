package com.github.vasiliz.vkclient.news.entity;

import com.google.gson.annotations.SerializedName;

public class Preview {

    @SerializedName("photo")
    private PhotoDocPreview mPhotoDocPreview;
    @SerializedName("video")
    private VideoDocPreview mVideoDocPreview;

    public PhotoDocPreview getPhotoDocPreview() {
        return mPhotoDocPreview;
    }

    public VideoDocPreview getVideoDocPreview() {
        return mVideoDocPreview;
    }
}
