package com.github.vasiliz.vkclient.news.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Audio implements Parcelable {

    @SerializedName("artist")
    private String mArtist;
    @SerializedName("title")
    private String mNameSong;
    @SerializedName("duration")
    private int mSongDuration;
    @SerializedName("url")
    private String mSongUrl;

    public Audio() {
    }

    public String getArtist() {
        return mArtist;
    }

    public String getmNameSong() {
        return mNameSong;
    }

    public int getmSongDuration() {
        return mSongDuration;
    }

    public String getmSongUrl() {
        return mSongUrl;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mArtist);
        dest.writeString(this.mNameSong);
        dest.writeInt(this.mSongDuration);
        dest.writeString(this.mSongUrl);
    }

    protected Audio(Parcel in) {
        this.mArtist = in.readString();
        this.mNameSong = in.readString();
        this.mSongDuration = in.readInt();
        this.mSongUrl = in.readString();
    }

    public static final Creator<Audio> CREATOR = new Creator<Audio>() {
        @Override
        public Audio createFromParcel(Parcel source) {
            return new Audio(source);
        }

        @Override
        public Audio[] newArray(int size) {
            return new Audio[size];
        }
    };
}
