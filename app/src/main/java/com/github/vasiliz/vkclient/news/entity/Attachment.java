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
public class Attachment implements Parcelable {

    @Id
    private int mHash;

    @Field
    private long idAttachment;
    @Field
    @SerializedName("type")
    private String mTypeAttachments;
    @Field
    @SerializedName("photo")
    private Photo mPhoto;
    @Field
    @SerializedName("audio")
    private Audio mAudio;
    @Field
    @SerializedName("video")
    private Video mVideo;
    @Field
    @SerializedName("doc")
    private Doc mDoc;
    @Field
    @SerializedName("link")
    private Link mLink;

    public Attachment(final String pTypeAttachments, final Photo pPhoto, final Audio pAudio, final Video pVideo, final Doc pDoc, final Link pLink) {
        mTypeAttachments = pTypeAttachments;
        mPhoto = pPhoto;
        mAudio = pAudio;
        mVideo = pVideo;
        mDoc = pDoc;
        mLink = pLink;
    }

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
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(this.mTypeAttachments);
        dest.writeParcelable(this.mPhoto, flags);
        dest.writeParcelable(this.mAudio, flags);
        dest.writeParcelable(this.mVideo, flags);
        dest.writeParcelable(this.mDoc, flags);
        dest.writeParcelable(this.mLink, flags);
    }

    public Attachment() {
    }

    Attachment(final Parcel in) {
        this.mTypeAttachments = in.readString();
        this.mPhoto = in.readParcelable(Photo.class.getClassLoader());
        this.mAudio = in.readParcelable(Audio.class.getClassLoader());
        this.mVideo = in.readParcelable(Video.class.getClassLoader());
        this.mDoc = in.readParcelable(Doc.class.getClassLoader());
        this.mLink = in.readParcelable(Link.class.getClassLoader());
    }

    public static final Creator<Attachment> CREATOR = new Creator<Attachment>() {
        @Override
        public Attachment createFromParcel(final Parcel source) {
            return new Attachment(source);
        }

        @Override
        public Attachment[] newArray(final int size) {
            return new Attachment[size];
        }
    };

    public ContentValues getContentValues(final long pIdAttachment, final long identifier) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put("mHash", identifier);
        contentValues.put(ConstantStrings.DB.AttathmentTable.ID_ATTACHMENT, pIdAttachment);
        contentValues.put(ConstantStrings.DB.AttathmentTable.TYPE_ATTACHMENT, mTypeAttachments);
        if (mTypeAttachments.contains(ConstantStrings.TypesAttachment.AUDIO)) {
            contentValues.put(ConstantStrings.DB.AttathmentTable.AUDIO, hashCode());
        } else if (mTypeAttachments.contains(ConstantStrings.TypesAttachment.VIDEO)) {
            contentValues.put(ConstantStrings.DB.AttathmentTable.VIDEO, hashCode());
        } else if (mTypeAttachments.contains(ConstantStrings.TypesAttachment.DOC)) {
            contentValues.put(ConstantStrings.DB.AttathmentTable.DOC, hashCode());
        } else if (mTypeAttachments.contains(ConstantStrings.TypesAttachment.LINK)) {
            contentValues.put(ConstantStrings.DB.AttathmentTable.LINK, hashCode());
        } else {
            contentValues.put(ConstantStrings.DB.AttathmentTable.PHOTO, hashCode());
        }
        return contentValues;
    }

    @Override
    public boolean equals(final Object pO) {
        if (this == pO) return true;
        if (pO == null || getClass() != pO.getClass()) return false;

        final Attachment that = (Attachment) pO;

        if (idAttachment != that.idAttachment) return false;
        if (mTypeAttachments != null ? !mTypeAttachments.equals(that.mTypeAttachments) : that.mTypeAttachments != null)
            return false;
        if (mPhoto != null ? !mPhoto.equals(that.mPhoto) : that.mPhoto != null) return false;
        if (mAudio != null ? !mAudio.equals(that.mAudio) : that.mAudio != null) return false;
        if (mVideo != null ? !mVideo.equals(that.mVideo) : that.mVideo != null) return false;
        if (mDoc != null ? !mDoc.equals(that.mDoc) : that.mDoc != null) return false;
        return mLink != null ? mLink.equals(that.mLink) : that.mLink == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (idAttachment ^ (idAttachment >>> 32));
        result += 31 * result + (mTypeAttachments != null ? mTypeAttachments.hashCode() : 0);
        result += 31 * result + (mPhoto != null ? mPhoto.hashCode() : 0);
        result += 31 * result + (mAudio != null ? mAudio.hashCode() : 0);
        result += 31 * result + (mVideo != null ? mVideo.hashCode() : 0);
        result += 31 * result + (mDoc != null ? mDoc.hashCode() : 0);
        result += 31 * result + (mLink != null ? mLink.hashCode() : 0);
        return result;
    }
}
