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
public class Doc implements Parcelable {
    @Id
    private long idDoc;
    @Field
    @SerializedName("title")
    private String mTitle;
    @Field
    @SerializedName("url")
    private String mUrl;
    @Field
    @SerializedName("ext")
    private String mExt;
    @Field
    @SerializedName("size")
    private int mSize;
    @Field
    @SerializedName("preview")
    private Preview mPreview;

    public Preview getPreview() {
        return mPreview;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getExt() {
        return mExt;
    }

    public int getSize() {
        return mSize;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(this.mTitle);
        dest.writeString(this.mUrl);
        dest.writeString(this.mExt);
        dest.writeInt(this.mSize);
        dest.writeParcelable(this.mPreview, flags);
    }

    public Doc() {
    }

    public Doc(final String pTitle, final String pUrl, final String pExt, final int pSize, final Preview pPreview) {
        mTitle = pTitle;
        mUrl = pUrl;
        mExt = pExt;
        mSize = pSize;
        mPreview = pPreview;
    }

    protected Doc(final Parcel in) {
        this.mTitle = in.readString();
        this.mUrl = in.readString();
        this.mExt = in.readString();
        this.mSize = in.readInt();
        this.mPreview = in.readParcelable(Preview.class.getClassLoader());
    }

    public static final Creator<Doc> CREATOR = new Creator<Doc>() {
        @Override
        public Doc createFromParcel(final Parcel source) {
            return new Doc(source);
        }

        @Override
        public Doc[] newArray(final int size) {
            return new Doc[size];
        }
    };

    public ContentValues getContentValues(final long pIdDoc) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantStrings.DB.DocTable.ID_DOC, pIdDoc);
        contentValues.put(ConstantStrings.DB.DocTable.TITLE, mTitle);
        contentValues.put(ConstantStrings.DB.DocTable.URL, mUrl);
        contentValues.put(ConstantStrings.DB.DocTable.EXT, mExt);
        contentValues.put(ConstantStrings.DB.DocTable.SIZE, mSize);
        contentValues.put(ConstantStrings.DB.DocTable.PREVIEW, hashCode());
        return contentValues;
    }

    @Override
    public boolean equals(final Object pO) {
        if (this == pO) return true;
        if (pO == null || getClass() != pO.getClass()) return false;

        final Doc doc = (Doc) pO;

        if (idDoc != doc.idDoc) return false;
        if (mSize != doc.mSize) return false;
        if (mTitle != null ? !mTitle.equals(doc.mTitle) : doc.mTitle != null) return false;
        if (mUrl != null ? !mUrl.equals(doc.mUrl) : doc.mUrl != null) return false;
        if (mExt != null ? !mExt.equals(doc.mExt) : doc.mExt != null) return false;
        return mPreview != null ? mPreview.equals(doc.mPreview) : doc.mPreview == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (idDoc ^ (idDoc >>> 32));
        result = 31 * result + (mTitle != null ? mTitle.hashCode() : 0);
        result = 31 * result + (mUrl != null ? mUrl.hashCode() : 0);
        result = 31 * result + (mExt != null ? mExt.hashCode() : 0);
        result = 31 * result + mSize;
        result = 31 * result + (mPreview != null ? mPreview.hashCode() : 0);
        return result;
    }
}
