package com.github.vasiliz.vkclient.vkNews.entity;

import com.github.vasiliz.vkclient.base.db.Field;
import com.github.vasiliz.vkclient.base.db.Id;
import com.github.vasiliz.vkclient.base.db.Table;
import com.google.gson.annotations.SerializedName;

@Table
public class Attachment {

    @Id
    private long id;

    @SerializedName("type")
    private String mTypeAttachments;

    @Field
    @SerializedName("photo")
    private Photo mPhoto;

    @Field
    @SerializedName("audio")
    private Audio mAudio;

    @Field
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
