package com.github.vasiliz.vkclient.main.entity;

import com.github.vasiliz.vkclient.base.db.config.Field;
import com.github.vasiliz.vkclient.base.db.config.Id;
import com.github.vasiliz.vkclient.base.db.config.Table;
import com.google.gson.annotations.SerializedName;
@Table
public class Groups {

    @Id
    private long idGroups;

    @Field
    @SerializedName("id")
    private long mId;
    @Field
    @SerializedName("name")
    private String mNameGroup;
    @Field
    @SerializedName("screen_name")
    private String mScreenName;
    @Field
    @SerializedName("is_closed")
    private int mIsClosed;
    @Field
    @SerializedName("type")
    private String mType;
    @Field
    @SerializedName("photo_50")
    private String mUrlGroupPhoto50;
    @Field
    @SerializedName("photo_100")
    private String mUrlGroupPhoto100;
    @Field
    @SerializedName("photo_200")
    private String mUrlGroupPhoto200;

    public Groups() {
    }

    public long getId() {
        return mId;
    }

    public void setId(final long pId) {
        mId = pId;
    }

    public String getNameGroup() {
        return mNameGroup;
    }

    public void setNameGroup(final String pNameGroup) {
        mNameGroup = pNameGroup;
    }

    public String getScreenName() {
        return mScreenName;
    }

    public void setScreenName(final String pScreenName) {
        mScreenName = pScreenName;
    }

    public int getIsClosed() {
        return mIsClosed;
    }

    public void setIsClosed(final int pIsClosed) {
        mIsClosed = pIsClosed;
    }

    public String getType() {
        return mType;
    }

    public void setType(final String pType) {
        mType = pType;
    }

    public String getUrlGroupPhoto50() {
        return mUrlGroupPhoto50;
    }

    public void setUrlGroupPhoto50(final String pUrlGroupPhoto50) {
        mUrlGroupPhoto50 = pUrlGroupPhoto50;
    }

    public String getUrlGroupPhoto100() {
        return mUrlGroupPhoto100;
    }

    public void setUrlGroupPhoto100(final String pUrlGroupPhoto100) {
        mUrlGroupPhoto100 = pUrlGroupPhoto100;
    }

    public String getUrlGroupPhoto200() {
        return mUrlGroupPhoto200;
    }

    public void setUrlGroupPhoto200(final String pUrlGroupPhoto200) {
        mUrlGroupPhoto200 = pUrlGroupPhoto200;
    }
}
