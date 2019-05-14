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
public class Comments implements Parcelable {

    @Id
    private long mCommentsId;
    @Field
    @SerializedName("count")
    private long mCountComments;
    @Field
    @SerializedName("can_post")
    private int mCanPost;

    public Comments() {
    }

    public Comments(final long pCountComments, final int pCanPost) {
        mCountComments = pCountComments;
        mCanPost = pCanPost;
    }

    public long getCountComments() {
        return mCountComments;
    }

    public int getCanPost() {
        return mCanPost;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeLong(this.mCountComments);
        dest.writeInt(this.mCanPost);
    }

    protected Comments(final Parcel in) {
        this.mCountComments = in.readLong();
        this.mCanPost = in.readInt();
    }

    public static final Creator<Comments> CREATOR = new Creator<Comments>() {
        @Override
        public Comments createFromParcel(final Parcel source) {
            return new Comments(source);
        }

        @Override
        public Comments[] newArray(final int size) {
            return new Comments[size];
        }
    };

    public ContentValues getContentValue(final int pPostId) {

        final ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantStrings.DB.CommnetTable.COMMENTS_ID, pPostId);
        contentValues.put(ConstantStrings.DB.CommnetTable.COUNT_COMMENTS, mCountComments);
        contentValues.put(ConstantStrings.DB.CommnetTable.CAN_POST, mCanPost);
        return contentValues;
    }
}
