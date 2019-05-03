package com.github.vasiliz.vkclient.news.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class VideoDocPreview implements Parcelable {
    @SerializedName("src")
    private String mSourse;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mSourse);
    }

    public VideoDocPreview() {
    }

    protected VideoDocPreview(Parcel in) {
        this.mSourse = in.readString();
    }

    public static final Creator<VideoDocPreview> CREATOR = new Creator<VideoDocPreview>() {
        @Override
        public VideoDocPreview createFromParcel(Parcel source) {
            return new VideoDocPreview(source);
        }

        @Override
        public VideoDocPreview[] newArray(int size) {
            return new VideoDocPreview[size];
        }
    };
}
