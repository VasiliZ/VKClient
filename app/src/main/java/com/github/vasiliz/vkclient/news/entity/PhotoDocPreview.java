package com.github.vasiliz.vkclient.news.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhotoDocPreview {

    public List<SizesPhotoPreview> getSizesPhotoPreviews() {
        return mSizesPhotoPreviews;
    }

    @SerializedName("sizes")
    private List<SizesPhotoPreview> mSizesPhotoPreviews;
}
