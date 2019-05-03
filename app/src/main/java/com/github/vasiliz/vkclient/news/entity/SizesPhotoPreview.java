package com.github.vasiliz.vkclient.news.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SizesPhotoPreview implements Parcelable {

    public String getSourse() {
        return mSourse;
    }

    @SerializedName("src")
    private String mSourse;
    @SerializedName("type")
    String mType;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mSourse);
        dest.writeString(this.mType);
    }

    public SizesPhotoPreview() {
    }

    protected SizesPhotoPreview(Parcel in) {
        this.mSourse = in.readString();
        this.mType = in.readString();
    }

    public static final Creator<SizesPhotoPreview> CREATOR = new Creator<SizesPhotoPreview>() {
        @Override
        public SizesPhotoPreview createFromParcel(Parcel source) {
            return new SizesPhotoPreview(source);
        }

        @Override
        public SizesPhotoPreview[] newArray(int size) {
            return new SizesPhotoPreview[size];
        }
    };
}
