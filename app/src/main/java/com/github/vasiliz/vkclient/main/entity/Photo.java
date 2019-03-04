package com.github.vasiliz.vkclient.main.entity;

import com.google.gson.annotations.SerializedName;

public class Photo {
    @SerializedName("photo_604")
    private String mPhoto604;
    @SerializedName("id")
    private long mId;
    @SerializedName("user_id")
    private long mUserId;
    @SerializedName("width")
    private int mWidth;
    @SerializedName("height")
    private int mHeight;
    @SerializedName("text")
    private String mText;
    @SerializedName("post_id")
    private String mPostId;
    @SerializedName("access_key")
    private String mAccessPhotoKey;

    public Photo() {
    }

    public String getPhoto604() {
        return mPhoto604;
    }

    public void setPhoto604(final String pPhoto604) {
        mPhoto604 = pPhoto604;
    }

    public long getId() {
        return mId;
    }

    public void setId(final long pId) {
        mId = pId;
    }

    public long getUserId() {
        return mUserId;
    }

    public void setUserId(final long pUserId) {
        mUserId = pUserId;
    }

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(final int pWidth) {
        mWidth = pWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setHeight(final int pHeight) {
        mHeight = pHeight;
    }

    public String getText() {
        return mText;
    }

    public void setText(final String pText) {
        mText = pText;
    }

    public String getPostId() {
        return mPostId;
    }

    public void setPostId(final String pPostId) {
        mPostId = pPostId;
    }

    public String getAccessPhotoKey() {
        return mAccessPhotoKey;
    }

    public void setAccessPhotoKey(final String pAccessPhotoKey) {
        mAccessPhotoKey = pAccessPhotoKey;
    }
}
