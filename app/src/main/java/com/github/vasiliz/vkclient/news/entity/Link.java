package com.github.vasiliz.vkclient.news.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Link implements Parcelable {
    @SerializedName("url")
    private String mUrlLink;
    @SerializedName("title")
    private String mLinkTitle;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("photo")
    private Photo mPhoto;
    @SerializedName("caption")
    private String mCaption;

    public String getUrlLink() {
        return mUrlLink;
    }

    public String getLinkTitle() {
        return mLinkTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public Photo getPhoto() {
        return mPhoto;
    }

    public String getCaption() {
        return mCaption;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(this.mUrlLink);
        dest.writeString(this.mLinkTitle);
        dest.writeString(this.mDescription);
        dest.writeParcelable(this.mPhoto, flags);
        dest.writeString(this.mCaption);
    }

    public Link() {
    }

    protected Link(final Parcel in) {
        this.mUrlLink = in.readString();
        this.mLinkTitle = in.readString();
        this.mDescription = in.readString();
        this.mPhoto = in.readParcelable(Photo.class.getClassLoader());
        this.mCaption = in.readString();
    }

    public static final Creator<Link> CREATOR = new Creator<Link>() {
        @Override
        public Link createFromParcel(final Parcel source) {
            return new Link(source);
        }

        @Override
        public Link[] newArray(final int size) {
            return new Link[size];
        }
    };
}
