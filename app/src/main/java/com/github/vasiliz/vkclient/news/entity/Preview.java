package com.github.vasiliz.vkclient.news.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Preview implements Parcelable {

    @SerializedName("photo")
    private PhotoPreview mPhotoPreview;
    @SerializedName("video")
    private VideoDocPreview mVideoDocPreview;

    public PhotoPreview getPhotoPreview() {
        return mPhotoPreview;
    }

    public VideoDocPreview getVideoDocPreview() {
        return mVideoDocPreview;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mPhotoPreview, flags);
        dest.writeParcelable(this.mVideoDocPreview, flags);
    }

    public Preview() {
    }

    protected Preview(Parcel in) {
        this.mPhotoPreview = in.readParcelable(PhotoPreview.class.getClassLoader());
        this.mVideoDocPreview = in.readParcelable(VideoDocPreview.class.getClassLoader());
    }

    public static final Creator<Preview> CREATOR = new Creator<Preview>() {
        @Override
        public Preview createFromParcel(Parcel source) {
            return new Preview(source);
        }

        @Override
        public Preview[] newArray(int size) {
            return new Preview[size];
        }
    };
}
