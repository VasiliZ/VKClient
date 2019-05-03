package com.github.vasiliz.vkclient.news.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PhotoPreview implements Parcelable {

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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.mSizesPhotoPreviews);
    }

    public PhotoPreview() {
    }

    protected PhotoPreview(Parcel in) {
        this.mSizesPhotoPreviews = new ArrayList<SizesPhotoPreview>();
        in.readList(this.mSizesPhotoPreviews, SizesPhotoPreview.class.getClassLoader());
    }

    public static final Creator<PhotoPreview> CREATOR = new Creator<PhotoPreview>() {
        @Override
        public PhotoPreview createFromParcel(Parcel source) {
            return new PhotoPreview(source);
        }

        @Override
        public PhotoPreview[] newArray(int size) {
            return new PhotoPreview[size];
        }
    };
}
