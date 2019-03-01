package com.github.vasiliz.vkclient.vkNews.entity;

import com.github.vasiliz.vkclient.base.db.Field;
import com.github.vasiliz.vkclient.base.db.Id;
import com.github.vasiliz.vkclient.base.db.Table;
import com.google.gson.annotations.SerializedName;

@Table
public class Profile {

    @Id
    private long id;
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
}
