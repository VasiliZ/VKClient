package com.github.vasiliz.vkclient.news.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.github.vasiliz.vkclient.base.db.config.Field;
import com.github.vasiliz.vkclient.base.db.config.Table;
import com.google.gson.annotations.SerializedName;

@Table
public class Profile implements Parcelable {

    @Field
    @SerializedName("id")
    private long mId;
    @Field
    @SerializedName("first_name")
    private String mFirstName;
    @Field
    @SerializedName("last_name")
    private String mLastName;
    @Field
    @SerializedName("sex")
    private String mSex;
    @Field
    @SerializedName("screen_name")
    private String mScreenName;
    @Field
    @SerializedName("photo_50")
    private String mUrlPhoto50;
    @Field
    @SerializedName("photo_100")
    private String mUrlPhoto100;
    @Field
    @SerializedName("online")
    private int mOnline;

    public Profile() {
    }

    public long getId() {
        return mId;
    }

    public void setId(final long pId) {
        mId = pId;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(final String pFirstName) {
        mFirstName = pFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(final String pLastName) {
        mLastName = pLastName;
    }

    public String getSex() {
        return mSex;
    }

    public void setSex(final String pSex) {
        mSex = pSex;
    }

    public String getScreenName() {
        return mScreenName;
    }

    public void setScreenName(final String pScreenName) {
        mScreenName = pScreenName;
    }

    public String getUrlPhoto50() {
        return mUrlPhoto50;
    }

    public void setUrlPhoto50(final String pUrlPhoto50) {
        mUrlPhoto50 = pUrlPhoto50;
    }

    public String getUrlPhoto100() {
        return mUrlPhoto100;
    }

    public void setUrlPhoto100(final String pUrlPhoto100) {
        mUrlPhoto100 = pUrlPhoto100;
    }

    public int getOnline() {
        return mOnline;
    }

    public void setOnline(final int pOnline) {
        mOnline = pOnline;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeLong(this.mId);
        dest.writeString(this.mFirstName);
        dest.writeString(this.mLastName);
        dest.writeString(this.mSex);
        dest.writeString(this.mScreenName);
        dest.writeString(this.mUrlPhoto50);
        dest.writeString(this.mUrlPhoto100);
        dest.writeInt(this.mOnline);
    }

    private Profile(final Parcel in) {
        this.mId = in.readLong();
        this.mFirstName = in.readString();
        this.mLastName = in.readString();
        this.mSex = in.readString();
        this.mScreenName = in.readString();
        this.mUrlPhoto50 = in.readString();
        this.mUrlPhoto100 = in.readString();
        this.mOnline = in.readInt();
    }

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(final Parcel source) {
            return new Profile(source);
        }

        @Override
        public Profile[] newArray(final int size) {
            return new Profile[size];
        }
    };
}
