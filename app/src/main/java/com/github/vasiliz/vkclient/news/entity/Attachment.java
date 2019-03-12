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

    public Attachment() {

    }

    public String getTypeAttachments() {
        return mTypeAttachments;
    }

    public void setTypeAttachments(final String pTypeAttachments) {
        mTypeAttachments = pTypeAttachments;
    }

    public Photo getPhoto() {
        return mPhoto;
    }

    public void setPhoto(final Photo pPhoto) {
        mPhoto = pPhoto;
    }

    public Audio getAudio() {
        return mAudio;
    }

    public void setAudio(final Audio pAudio) {
        mAudio = pAudio;
    }

    public Video getVideo() {
        return mVideo;
    }

    public void setVideo(final Video pVideo) {
        mVideo = pVideo;
    }
}
