package com.github.vasiliz.vkclient.news.entity;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.github.vasiliz.vkclient.base.db.config.Field;
import com.github.vasiliz.vkclient.base.db.config.Id;
import com.github.vasiliz.vkclient.base.utils.ConstantStrings;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PhotoPreview implements Parcelable {
    @Id
    private long idPhotoPreview;
    @Field
    @SerializedName("sizes")
    private List<SizesPhotoPreview> mSizesPhotoPreviews;

    public List<SizesPhotoPreview> getSizesPhotoPreviews() {
        return Collections.unmodifiableList(mSizesPhotoPreviews);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeList(this.mSizesPhotoPreviews);
    }

    public PhotoPreview() {
    }

    protected PhotoPreview(final Parcel in) {
        this.mSizesPhotoPreviews = new ArrayList<SizesPhotoPreview>();
        in.readList(this.mSizesPhotoPreviews, SizesPhotoPreview.class.getClassLoader());
    }

    public static final Creator<PhotoPreview> CREATOR = new Creator<PhotoPreview>() {
        @Override
        public PhotoPreview createFromParcel(final Parcel source) {
            return new PhotoPreview(source);
        }

        @Override
        public PhotoPreview[] newArray(final int size) {
            return new PhotoPreview[size];
        }
    };

    public ContentValues getContentValues(final long pidPhotoPreview) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantStrings.DB.PhotoPreviewTable.ID, pidPhotoPreview);
        contentValues.put(ConstantStrings.DB.PhotoPreviewTable.SIZES_PHOTO_PREVIEW, hashCode());
        return contentValues;
    }

    @Override
    public boolean equals(final Object pO) {
        if (this == pO) return true;
        if (pO == null || getClass() != pO.getClass()) return false;

        final PhotoPreview that = (PhotoPreview) pO;

        if (idPhotoPreview != that.idPhotoPreview) return false;
        return mSizesPhotoPreviews != null ? mSizesPhotoPreviews.equals(that.mSizesPhotoPreviews) : that.mSizesPhotoPreviews == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (idPhotoPreview ^ (idPhotoPreview >>> 32));
        result = 31 * result + (mSizesPhotoPreviews != null ? mSizesPhotoPreviews.hashCode() : 0);
        return result;
    }
}
