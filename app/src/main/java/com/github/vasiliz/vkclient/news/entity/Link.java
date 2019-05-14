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
public class Link implements Parcelable {
    @Id
    private long idLink;
    @Field
    @SerializedName("url")
    private String mUrlLink;
    @Field
    @SerializedName("title")
    private String mLinkTitle;
    @Field
    @SerializedName("description")
    private String mDescription;
    @Field
    @SerializedName("photo")
    private Photo mPhoto;
    @Field
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

    public Link(final String pUrlLink, final String pLinkTitle, final String pDescription, final Photo pPhoto, final String pCaption) {
        mUrlLink = pUrlLink;
        mLinkTitle = pLinkTitle;
        mDescription = pDescription;
        mPhoto = pPhoto;
        mCaption = pCaption;
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

    public ContentValues getContentValues(final long pIdLink) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantStrings.DB.LinkTable.ID_LINK, pIdLink);
        contentValues.put(ConstantStrings.DB.LinkTable.URL_LINK, mUrlLink);
        contentValues.put(ConstantStrings.DB.LinkTable.TITLE, mLinkTitle);
        contentValues.put(ConstantStrings.DB.LinkTable.DESCRIPTION, mDescription);
        if (mPhoto != null) {
            contentValues.put(ConstantStrings.DB.LinkTable.PHOTO, hashCode());
        }
        contentValues.put(ConstantStrings.DB.LinkTable.CAPTION, mCaption);
        return contentValues;
    }

    @Override
    public boolean equals(final Object pO) {
        if (this == pO) return true;
        if (pO == null || getClass() != pO.getClass()) return false;

        final Link link = (Link) pO;

        if (idLink != link.idLink) return false;
        if (mUrlLink != null ? !mUrlLink.equals(link.mUrlLink) : link.mUrlLink != null)
            return false;
        if (mLinkTitle != null ? !mLinkTitle.equals(link.mLinkTitle) : link.mLinkTitle != null)
            return false;
        if (mDescription != null ? !mDescription.equals(link.mDescription) : link.mDescription != null)
            return false;
        if (mPhoto != null ? !mPhoto.equals(link.mPhoto) : link.mPhoto != null) return false;
        return mCaption != null ? mCaption.equals(link.mCaption) : link.mCaption == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (idLink ^ (idLink >>> 32));
        result = 31 * result + (mUrlLink != null ? mUrlLink.hashCode() : 0);
        result = 31 * result + (mLinkTitle != null ? mLinkTitle.hashCode() : 0);
        result = 31 * result + (mDescription != null ? mDescription.hashCode() : 0);
        result = 31 * result + (mPhoto != null ? mPhoto.hashCode() : 0);
        result = 31 * result + (mCaption != null ? mCaption.hashCode() : 0);
        return result;
    }
}
