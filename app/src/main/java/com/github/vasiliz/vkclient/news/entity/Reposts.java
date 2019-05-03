package com.github.vasiliz.vkclient.news.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Reposts implements Parcelable {
    @SerializedName("count")
    private long mCountReposts;
    @SerializedName("user_reposted")
    private int mUserReposted;

    public Reposts() {
    }

    public long getCountReposts() {
        return mCountReposts;
    }

    public int getUserReposted() {
        return mUserReposted;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeLong(this.mCountReposts);
        dest.writeInt(this.mUserReposted);
    }

    protected Reposts(final Parcel in) {
        this.mCountReposts = in.readLong();
        this.mUserReposted = in.readInt();
    }

    public static final Creator<Reposts> CREATOR = new Creator<Reposts>() {
        @Override
        public Reposts createFromParcel(final Parcel source) {
            return new Reposts(source);
        }

        @Override
        public Reposts[] newArray(final int size) {
            return new Reposts[size];
        }
    };
}
