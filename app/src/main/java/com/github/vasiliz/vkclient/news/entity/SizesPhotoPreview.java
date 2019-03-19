package com.github.vasiliz.vkclient.news.entity;

import com.google.gson.annotations.SerializedName;

public class SizesPhotoPreview {

    public String getSourse() {
        return mSourse;
    }

    @SerializedName("src")
    private String mSourse;

}
