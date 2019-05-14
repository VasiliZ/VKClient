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
public class SizesPhotoPreview implements Parcelable {
    @Id
    private long idSizesPhotoPreview;
    @Field
    @SerializedName("src")
    private String mSourse;
    @Field
    @SerializedName("type")
    private String mType;

    public String getSourse() {
        return mSourse;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(this.mSourse);
        dest.writeString(this.mType);
    }

    public SizesPhotoPreview() {
    }

    protected SizesPhotoPreview(final Parcel in) {
        this.mSourse = in.readString();
        this.mType = in.readString();
    }

    public static final Creator<SizesPhotoPreview> CREATOR = new Creator<SizesPhotoPreview>() {
        @Override
        public SizesPhotoPreview createFromParcel(final Parcel source) {
            return new SizesPhotoPreview(source);
        }

        @Override
        public SizesPhotoPreview[] newArray(final int size) {
            return new SizesPhotoPreview[size];
        }
    };

    public ContentValues getContentValues(final int pIdSizesPhotoPreview) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantStrings.DB.SizesPhotoPreviewTable.ID, pIdSizesPhotoPreview);
        contentValues.put(ConstantStrings.DB.SizesPhotoPreviewTable.SOURCE, mSourse);
        contentValues.put(ConstantStrings.DB.SizesPhotoPreviewTable.TYPE, mType);
        return contentValues;
    }
}
