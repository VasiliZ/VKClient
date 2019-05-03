package com.github.vasiliz.vkclient.news.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Likes implements Parcelable {
    @SerializedName("count")
    private long mCountLike;
    @SerializedName("user_likes")
    private int mUserLike;
    @SerializedName("can_like")
    private int mCanLike;

    public Likes() {
    }

    public long getCountLike() {
        return mCountLike;
    }

    public void setCountLike(final long pCountLike) {
        mCountLike = pCountLike;
    }

    public int getUserLike() {
        return mUserLike;
    }

    public void setUserLike(final int pUserLike) {
        mUserLike = pUserLike;
    }

    public int getCanLike() {
        return mCanLike;
    }

    public void setCanLike(final int pCanLike) {
        mCanLike = pCanLike;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mCountLike);
        dest.writeInt(this.mUserLike);
        dest.writeInt(this.mCanLike);
    }

    protected Likes(Parcel in) {
        this.mCountLike = in.readLong();
        this.mUserLike = in.readInt();
        this.mCanLike = in.readInt();
    }

    public static final Creator<Likes> CREATOR = new Creator<Likes>() {
        @Override
        public Likes createFromParcel(Parcel source) {
            return new Likes(source);
        }

        @Override
        public Likes[] newArray(int size) {
            return new Likes[size];
        }
    };
}
