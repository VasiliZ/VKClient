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
public class Photo implements Parcelable {

    @Field
    private long idPhoto;
    @Field
    @SerializedName("photo_604")
    private String mPhoto604;
    @Field
    @SerializedName("photo_807")
    private String mPhoto807;
    @Field
    @SerializedName("photo_1280")
    private String mPhoto1280;
    @Field
    @SerializedName("photo_130")
    private String mPhoto130;
    @Id
    @SerializedName("id")
    private long mId;
    @Field
    @SerializedName("user_id")
    private long mUserId;
    @Field
    @SerializedName("width")
    private int mWidth;
    @Field
    @SerializedName("height")
    private int mHeight;
    @Field
    @SerializedName("text")
    private String mText;
    @Field
    @SerializedName("post_id")
    private int mPostId;
    @Field
    @SerializedName("access_key")
    private String mAccessPhotoKey;

    public Photo() {
    }

    public Photo(final String pPhoto604, final String pPhoto807, final String pPhoto1280, final String pPhoto130, final long pId, final long pUserId, final int pWidth, final int pHeight, final String pText, final int pPostId, final String pAccessPhotoKey) {
        mPhoto604 = pPhoto604;
        mPhoto807 = pPhoto807;
        mPhoto1280 = pPhoto1280;
        mPhoto130 = pPhoto130;
        mId = pId;
        mUserId = pUserId;
        mWidth = pWidth;
        mHeight = pHeight;
        mText = pText;
        mPostId = pPostId;
        mAccessPhotoKey = pAccessPhotoKey;
    }

    public String getPhoto807() {
        return mPhoto807;
    }

    public String getPhoto604() {
        return mPhoto604;
    }

    public String getPhoto1280() {
        return mPhoto1280;
    }

    public long getId() {
        return mId;
    }

    public void setId(final long pId) {
        mId = pId;
    }

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(final int pWidth) {
        mWidth = pWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setHeight(final int pHeight) {
        mHeight = pHeight;
    }

    public String getText() {
        return mText;
    }

    public int getPostId() {
        return mPostId;
    }

    public String getAccessPhotoKey() {
        return mAccessPhotoKey;
    }

    public String getPhoto130() {
        return mPhoto130;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(this.mPhoto604);
        dest.writeString(this.mPhoto807);
        dest.writeString(this.mPhoto1280);
        dest.writeString(this.mPhoto130);
        dest.writeLong(this.mId);
        dest.writeLong(this.mUserId);
        dest.writeInt(this.mWidth);
        dest.writeInt(this.mHeight);
        dest.writeString(this.mText);
        dest.writeInt(this.mPostId);
        dest.writeString(this.mAccessPhotoKey);
    }

    protected Photo(final Parcel in) {
        this.mPhoto604 = in.readString();
        this.mPhoto807 = in.readString();
        this.mPhoto1280 = in.readString();
        this.mPhoto130 = in.readString();
        this.mId = in.readLong();
        this.mUserId = in.readLong();
        this.mWidth = in.readInt();
        this.mHeight = in.readInt();
        this.mText = in.readString();
        this.mPostId = in.readInt();
        this.mAccessPhotoKey = in.readString();
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(final Parcel source) {
            return new Photo(source);
        }

        @Override
        public Photo[] newArray(final int size) {
            return new Photo[size];
        }
    };

    public ContentValues getContentValues(final long pIdPhoto) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantStrings.DB.PhotoTable.ID_PHOTO, pIdPhoto);
        contentValues.put(ConstantStrings.DB.PhotoTable.PHOTO_604, mPhoto604);
        contentValues.put(ConstantStrings.DB.PhotoTable.PHOTO_807, mPhoto807);
        contentValues.put(ConstantStrings.DB.PhotoTable.PHOTO_1280, mPhoto1280);
        contentValues.put(ConstantStrings.DB.PhotoTable.PHOTO_130, mPhoto130);
        contentValues.put(ConstantStrings.DB.PhotoTable.ID, mId);
        contentValues.put(ConstantStrings.DB.PhotoTable.USER_ID, mUserId);
        contentValues.put(ConstantStrings.DB.PhotoTable.WIDTH, mWidth);
        contentValues.put(ConstantStrings.DB.PhotoTable.HEIGTH, mHeight);
        contentValues.put(ConstantStrings.DB.PhotoTable.TEXT, mText);
        contentValues.put(ConstantStrings.DB.PhotoTable.POST_ID, mPostId);
        contentValues.put(ConstantStrings.DB.PhotoTable.ACCESS_PHOTO_KEY, mAccessPhotoKey);
        return contentValues;
    }
}
