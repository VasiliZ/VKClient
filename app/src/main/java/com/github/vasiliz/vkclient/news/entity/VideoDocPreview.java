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
public class VideoDocPreview implements Parcelable {
    @Id
    private long idVideoPreview;
    @Field
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

    public ContentValues getContentValues(final long pidVideoDocPreview) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantStrings.DB.VideoDocPreviewTable.ID, pidVideoDocPreview);
        contentValues.put(ConstantStrings.DB.VideoDocPreviewTable.SOURCE, mSourse);
        return contentValues;
    }
}
