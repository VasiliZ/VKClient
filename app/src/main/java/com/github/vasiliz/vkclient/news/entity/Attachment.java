package com.github.vasiliz.vkclient.news.entity;

import com.google.gson.annotations.SerializedName;

public class Attachment {

    @SerializedName("type")
    private String mTypeAttachments;

    @SerializedName("photo")
    private Photo mPhoto;

    @SerializedName("audio")
    private Audio mAudio;

    @SerializedName("video")
    private Video mVideo;

    @SerializedName("doc")
    private Doc mDoc;

    public Doc getDoc() {
        return mDoc;
    }

    public String getTypeAttachments() {
        return mTypeAttachments;
    }

    public Photo getPhoto() {
        return mPhoto;
    }

    public Audio getAudio() {
        return mAudio;
    }

    public Video getVideo() {
        return mVideo;
    }

}
