package com.github.vasiliz.vkclient.main.entity;

import com.github.vasiliz.vkclient.base.db.config.Field;
import com.github.vasiliz.vkclient.base.db.config.Id;
import com.github.vasiliz.vkclient.base.db.config.Table;
import com.google.gson.annotations.SerializedName;

@Table
public class Attachment {

    @Id
    private long idAttachment;

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
