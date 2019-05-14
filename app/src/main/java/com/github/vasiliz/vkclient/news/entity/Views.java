package com.github.vasiliz.vkclient.news.entity;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.github.vasiliz.vkclient.base.db.config.Field;
import com.github.vasiliz.vkclient.base.db.config.Table;
import com.github.vasiliz.vkclient.base.utils.ConstantStrings;
import com.google.gson.annotations.SerializedName;

@Table
public class Views implements Parcelable {
    @Field
    private long mIdViews;
    @Field
    @SerializedName("count")
    private long mCountViews;

    public Views() {
    }

    public Views(final long pCountViews) {
        mCountViews = pCountViews;
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

    Views(final Parcel in) {
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

    public ContentValues getContentValues(final long pIdViews) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantStrings.DB.ViewsTable.ID, pIdViews);
        contentValues.put(ConstantStrings.DB.ViewsTable.COUNT, mCountViews);
        return contentValues;
    }
}
