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
public class Preview implements Parcelable {
    @Id
    private long idPreview;
    @Field
    @SerializedName("photo")
    private PhotoPreview mPhotoPreview;
    @Field
    @SerializedName("video")
    private VideoDocPreview mVideoDocPreview;

    public PhotoPreview getPhotoPreview() {
        return mPhotoPreview;
    }

    public VideoDocPreview getVideoDocPreview() {
        return mVideoDocPreview;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeParcelable(this.mPhotoPreview, flags);
        dest.writeParcelable(this.mVideoDocPreview, flags);
    }

    public Preview() {
    }

    protected Preview(final Parcel in) {
        this.mPhotoPreview = in.readParcelable(PhotoPreview.class.getClassLoader());
        this.mVideoDocPreview = in.readParcelable(VideoDocPreview.class.getClassLoader());
    }

    public static final Creator<Preview> CREATOR = new Creator<Preview>() {
        @Override
        public Preview createFromParcel(final Parcel source) {
            return new Preview(source);
        }

        @Override
        public Preview[] newArray(final int size) {
            return new Preview[size];
        }
    };

    public ContentValues getContentValues(final long pIdPreview) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantStrings.DB.PreviewTable.ID_PREVIEW, pIdPreview);
        contentValues.put(ConstantStrings.DB.PreviewTable.PHOTO_PREVIEW, hashCode());
        contentValues.put(ConstantStrings.DB.PreviewTable.VIDEO_PREVIEW, hashCode());
        return contentValues;
    }

    @Override
    public boolean equals(final Object pO) {
        if (this == pO) return true;
        if (pO == null || getClass() != pO.getClass()) return false;

        final Preview preview = (Preview) pO;

        if (idPreview != preview.idPreview) return false;
        if (mPhotoPreview != null ? !mPhotoPreview.equals(preview.mPhotoPreview) : preview.mPhotoPreview != null)
            return false;
        return mVideoDocPreview != null ? mVideoDocPreview.equals(preview.mVideoDocPreview) : preview.mVideoDocPreview == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (idPreview ^ (idPreview >>> 32));
        result = 31 * result + (mPhotoPreview != null ? mPhotoPreview.hashCode() : 0);
        result = 31 * result + (mVideoDocPreview != null ? mVideoDocPreview.hashCode() : 0);
        return result;
    }
}
