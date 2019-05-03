package com.github.vasiliz.vkclient.news.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Views implements Parcelable {

    @SerializedName("views")
    private long mCountViews;

    public Views() {
    }

    public long getCountViews() {
        return mCountViews;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeLong(this.mCountViews);
    }

    protected Views(final Parcel in) {
        this.mCountViews = in.readLong();
    }

    public static final Creator<Views> CREATOR = new Creator<Views>() {
        @Override
        public Views createFromParcel(final Parcel source) {
            return new Views(source);
        }

        @Override
        public Views[] newArray(final int size) {
            return new Views[size];
        }
    };
}
