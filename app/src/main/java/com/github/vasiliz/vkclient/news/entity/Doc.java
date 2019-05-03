package com.github.vasiliz.vkclient.news.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Doc implements Parcelable {

    @SerializedName("title")
    private String mTitle;
    @SerializedName("url")
    private String mUrl;
    @SerializedName("ext")
    private String mExt;

    @SerializedName("size")
    private int mSize;

    @SerializedName("preview")
    private Preview mPreview;

    public Preview getPreview() {
        return mPreview;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getExt() {
        return mExt;
    }

    public int getSize() {
        return mSize;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTitle);
        dest.writeString(this.mUrl);
        dest.writeString(this.mExt);
        dest.writeInt(this.mSize);
        dest.writeParcelable(this.mPreview, flags);
    }

    public Doc() {
    }

    protected Doc(Parcel in) {
        this.mTitle = in.readString();
        this.mUrl = in.readString();
        this.mExt = in.readString();
        this.mSize = in.readInt();
        this.mPreview = in.readParcelable(Preview.class.getClassLoader());
    }

    public static final Creator<Doc> CREATOR = new Creator<Doc>() {
        @Override
        public Doc createFromParcel(Parcel source) {
            return new Doc(source);
        }

        @Override
        public Doc[] newArray(int size) {
            return new Doc[size];
        }
    };
}
