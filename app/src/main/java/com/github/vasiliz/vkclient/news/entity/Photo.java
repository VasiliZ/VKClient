package com.github.vasiliz.vkclient.news.entity;

import com.google.gson.annotations.SerializedName;

public class Photo {
    @SerializedName("photo_604")
    private String mPhoto604;
    @SerializedName("photo_807")
    private String mPhoto807;
    @SerializedName("photo_1280")
    private String mPhoto1280;
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

    public String getPhoto807() {
        return mPhoto807;
    }

    public String getPhoto604() {
        return mPhoto604;
    }

    public String getPhoto1280() {
        return mPhoto1280;
    }

    public long getId() {
        return mId;
    }

    public void setId(final long pId) {
        mId = pId;
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

    public String getPostId() {
        return mPostId;
    }

    public String getAccessPhotoKey() {
        return mAccessPhotoKey;
    }
}
