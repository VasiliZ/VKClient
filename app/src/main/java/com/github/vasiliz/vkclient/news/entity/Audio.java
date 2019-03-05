package com.github.vasiliz.vkclient.news.entity;

import com.google.gson.annotations.SerializedName;

class Audio {
    @SerializedName("artist")
    private String mArtist;

    public Audio() {
    }

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(final String pArtist) {
        mArtist = pArtist;
    }

}
