package com.github.vasiliz.vkclient.news.entity;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.github.vasiliz.vkclient.base.db.config.Field;
import com.github.vasiliz.vkclient.base.db.config.Id;
import com.github.vasiliz.vkclient.base.db.config.Table;
import com.github.vasiliz.vkclient.base.utils.ConstantStrings;
import com.google.gson.annotations.SerializedName;

@Table
public class Video implements Parcelable {
    @Id
    private long idVideo;
    @Field
    @SerializedName("title")
    private String mTitleVideo;
    @Field
    @SerializedName("src")
    private String mSource;
    @Field
    @SerializedName("photo_800")
    private String mUrlPhotoVideo;
    @Field
    @SerializedName("duration")
    private int mDurationVideo;

    public Video(final String pTitleVideo, final String pSource, final String pUrlPhotoVideo, final int pDurationVideo) {
        mTitleVideo = pTitleVideo;
        mSource = pSource;
        mUrlPhotoVideo = pUrlPhotoVideo;
        mDurationVideo = pDurationVideo;
    }

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
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(this.mTitleVideo);
        dest.writeString(this.mSource);
        dest.writeString(this.mUrlPhotoVideo);
        dest.writeInt(this.mDurationVideo);
    }

    public Video() {
    }

    protected Video(final Parcel in) {
        this.mTitleVideo = in.readString();
        this.mSource = in.readString();
        this.mUrlPhotoVideo = in.readString();
        this.mDurationVideo = in.readInt();
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(final Parcel source) {
            return new Video(source);
        }

        @Override
        public Video[] newArray(final int size) {
            return new Video[size];
        }
    };

    public ContentValues getContentValues(final long pIdVideo) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantStrings.DB.VideoTable.ID, pIdVideo);
        contentValues.put(ConstantStrings.DB.VideoTable.TITLE, mTitleVideo);
        contentValues.put(ConstantStrings.DB.VideoTable.SOURCE, mSource);
        contentValues.put(ConstantStrings.DB.VideoTable.URL_PHOTO, mUrlPhotoVideo);
        contentValues.put(ConstantStrings.DB.VideoTable.DURATION, mDurationVideo);
        return contentValues;
    }
}
