package com.github.vasiliz.vkclient.news.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Comments implements Parcelable {

    @SerializedName("count")
    private long mCountComments;
    @SerializedName("can_post")
    private int mCanPost;

    public Comments() {
    }

    public long getCountComments() {
        return mCountComments;
    }

    public int getCanPost() {
        return mCanPost;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mCountComments);
        dest.writeInt(this.mCanPost);
    }

    protected Comments(Parcel in) {
        this.mCountComments = in.readLong();
        this.mCanPost = in.readInt();
    }

    public static final Creator<Comments> CREATOR = new Creator<Comments>() {
        @Override
        public Comments createFromParcel(Parcel source) {
            return new Comments(source);
        }

        @Override
        public Comments[] newArray(int size) {
            return new Comments[size];
        }
    };
}
