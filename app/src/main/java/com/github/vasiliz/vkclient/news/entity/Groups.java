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
public class Groups implements Parcelable {
    @Id
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeLong(this.mId);
        dest.writeString(this.mNameGroup);
        dest.writeString(this.mScreenName);
        dest.writeInt(this.mIsClosed);
        dest.writeString(this.mType);
        dest.writeString(this.mUrlGroupPhoto50);
        dest.writeString(this.mUrlGroupPhoto100);
        dest.writeString(this.mUrlGroupPhoto200);

    }

    private Groups(final Parcel in) {
        this.mId = in.readLong();
        this.mNameGroup = in.readString();
        this.mScreenName = in.readString();
        this.mIsClosed = in.readInt();
        this.mType = in.readString();
        this.mUrlGroupPhoto50 = in.readString();
        this.mUrlGroupPhoto100 = in.readString();
        this.mUrlGroupPhoto200 = in.readString();
    }

    public static final Creator<Groups> CREATOR = new Creator<Groups>() {
        @Override
        public Groups createFromParcel(final Parcel source) {
            return new Groups(source);
        }

        @Override
        public Groups[] newArray(final int size) {
            return new Groups[size];
        }

        public ContentValues mContentValues(final Parcel pParcel) {
            return ContentValues.CREATOR.createFromParcel(pParcel);
        }
    };

    public ContentValues getContentValues() {
        final ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantStrings.DB.GroupsTable.ID_GROUP, mId);
        contentValues.put(ConstantStrings.DB.GroupsTable.NAME_GROUP, mNameGroup);
        contentValues.put(ConstantStrings.DB.GroupsTable.SCREEN_NAME, mScreenName);
        contentValues.put(ConstantStrings.DB.GroupsTable.IS_CLOSED, mIsClosed);
        contentValues.put(ConstantStrings.DB.GroupsTable.TYPE, mType);
        contentValues.put(ConstantStrings.DB.GroupsTable.PHOTO_50, mUrlGroupPhoto50);
        contentValues.put(ConstantStrings.DB.GroupsTable.PHOTO_100, mUrlGroupPhoto100);
        contentValues.put(ConstantStrings.DB.GroupsTable.PHOTO_200, mUrlGroupPhoto200);

        return contentValues;
    }
}
