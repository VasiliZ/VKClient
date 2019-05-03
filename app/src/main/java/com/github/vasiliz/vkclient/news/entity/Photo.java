package com.github.vasiliz.vkclient.news.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Photo implements Parcelable {
    @SerializedName("photo_604")
    private String mPhoto604;
    @SerializedName("photo_807")
    private String mPhoto807;
    @SerializedName("photo_1280")
    private String mPhoto1280;

    @SerializedName("photo_130")
    private String mPhoto130;
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

    public String getPhoto130() {
        return mPhoto130;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mPhoto604);
        dest.writeString(this.mPhoto807);
        dest.writeString(this.mPhoto1280);
        dest.writeString(this.mPhoto130);
        dest.writeLong(this.mId);
        dest.writeLong(this.mUserId);
        dest.writeInt(this.mWidth);
        dest.writeInt(this.mHeight);
        dest.writeString(this.mText);
        dest.writeString(this.mPostId);
        dest.writeString(this.mAccessPhotoKey);
    }

    protected Photo(Parcel in) {
        this.mPhoto604 = in.readString();
        this.mPhoto807 = in.readString();
        this.mPhoto1280 = in.readString();
        this.mPhoto130 = in.readString();
        this.mId = in.readLong();
        this.mUserId = in.readLong();
        this.mWidth = in.readInt();
        this.mHeight = in.readInt();
        this.mText = in.readString();
        this.mPostId = in.readString();
        this.mAccessPhotoKey = in.readString();
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel source) {
            return new Photo(source);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
}
