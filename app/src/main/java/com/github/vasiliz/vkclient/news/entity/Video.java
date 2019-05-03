package com.github.vasiliz.vkclient.news.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Video implements Parcelable {

    @SerializedName("title")
    private String mTitleVideo;
    @SerializedName("src")
    private String mSource;
    @SerializedName("photo_800")
    private String mUrlPhotoVideo;
    @SerializedName("duration")
    private int mDurationVideo;

    public String getSource() {
        return mSource;
    }

    public String getUrlPhotoVideo() {
        return mUrlPhotoVideo;
    }

    public int getDurationVideo() {
        return mDurationVideo;
    }

    public String getTitleVideo() {
        return mTitleVideo;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTitleVideo);
        dest.writeString(this.mSource);
        dest.writeString(this.mUrlPhotoVideo);
        dest.writeInt(this.mDurationVideo);
    }

    public Video() {
    }

    protected Video(Parcel in) {
        this.mTitleVideo = in.readString();
        this.mSource = in.readString();
        this.mUrlPhotoVideo = in.readString();
        this.mDurationVideo = in.readInt();
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel source) {
            return new Video(source);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };
}
