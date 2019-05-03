package com.github.vasiliz.vkclient.news.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Attachment implements Parcelable {

    @SerializedName("type")
    private String mTypeAttachments;

    @SerializedName("photo")
    private Photo mPhoto;

    @SerializedName("audio")
    private Audio mAudio;

    @SerializedName("video")
    private Video mVideo;

    @SerializedName("doc")
    private Doc mDoc;

    @SerializedName("link")
    private Link mLink;

    public Doc getDoc() {
        return mDoc;
    }

    public String getTypeAttachments() {
        return mTypeAttachments;
    }

    public Photo getPhoto() {
        return mPhoto;
    }

    public Audio getAudio() {
        return mAudio;
    }

    public Video getVideo() {
        return mVideo;
    }

    public Link getLink() {
        return mLink;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTypeAttachments);
        dest.writeParcelable(this.mPhoto, flags);
        dest.writeParcelable(this.mAudio, flags);
        dest.writeParcelable(this.mVideo, flags);
        dest.writeParcelable(this.mDoc, flags);
        dest.writeParcelable(this.mLink, flags);
    }

    public Attachment() {
    }

    protected Attachment(Parcel in) {
        this.mTypeAttachments = in.readString();
        this.mPhoto = in.readParcelable(Photo.class.getClassLoader());
        this.mAudio = in.readParcelable(Audio.class.getClassLoader());
        this.mVideo = in.readParcelable(Video.class.getClassLoader());
        this.mDoc = in.readParcelable(Doc.class.getClassLoader());
        this.mLink = in.readParcelable(Link.class.getClassLoader());
    }

    public static final Creator<Attachment> CREATOR = new Creator<Attachment>() {
        @Override
        public Attachment createFromParcel(Parcel source) {
            return new Attachment(source);
        }

        @Override
        public Attachment[] newArray(int size) {
            return new Attachment[size];
        }
    };
}
