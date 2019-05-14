package com.github.vasiliz.vkclient.news.entity;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.github.vasiliz.vkclient.base.db.config.Field;
import com.github.vasiliz.vkclient.base.db.config.Table;
import com.github.vasiliz.vkclient.base.utils.ConstantStrings;
import com.google.gson.annotations.SerializedName;

@Table
public class Likes implements Parcelable {

    @Field
    private long mIdLike;
    @Field
    @SerializedName("count")
    private long mCountLike;
    @Field
    @SerializedName("user_likes")
    private int mUserLike;
    @Field
    @SerializedName("can_like")
    private int mCanLike;

    public Likes() {
    }

    public Likes(final long pCountLike, final int pUserLike, final int pCanLike) {
        mCountLike = pCountLike;
        mUserLike = pUserLike;
        mCanLike = pCanLike;
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
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeLong(this.mCountLike);
        dest.writeInt(this.mUserLike);
        dest.writeInt(this.mCanLike);
    }

    protected Likes(final Parcel in) {
        this.mCountLike = in.readLong();
        this.mUserLike = in.readInt();
        this.mCanLike = in.readInt();
    }

    public static final Creator<Likes> CREATOR = new Creator<Likes>() {
        @Override
        public Likes createFromParcel(final Parcel source) {
            return new Likes(source);
        }

        @Override
        public Likes[] newArray(final int size) {
            return new Likes[size];
        }
    };

    public ContentValues getContentValues(final long pIdLike) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantStrings.DB.LikesTable.COUNT_LIKE, mCountLike);
        contentValues.put(ConstantStrings.DB.LikesTable.ID_LIKE, pIdLike);
        contentValues.put(ConstantStrings.DB.LikesTable.USER_LIKE, mUserLike);
        contentValues.put(ConstantStrings.DB.LikesTable.CAN_LIKE, mCanLike);
        return contentValues;
    }
}
