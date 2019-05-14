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
public class Reposts implements Parcelable {
    @Id
    private long mRepostId;
    @Field
    @SerializedName("count")
    private long mCountReposts;
    @Field
    @SerializedName("user_reposted")
    private int mUserReposted;

    public Reposts() {
    }

    public Reposts(final long pCountReposts, final int pUserReposted) {
        mCountReposts = pCountReposts;
        mUserReposted = pUserReposted;
    }

    public long getCountReposts() {
        return mCountReposts;
    }

    public int getUserReposted() {
        return mUserReposted;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeLong(this.mCountReposts);
        dest.writeInt(this.mUserReposted);
    }

    protected Reposts(final Parcel in) {
        this.mCountReposts = in.readLong();
        this.mUserReposted = in.readInt();
    }

    public static final Creator<Reposts> CREATOR = new Creator<Reposts>() {
        @Override
        public Reposts createFromParcel(final Parcel source) {
            return new Reposts(source);
        }

        @Override
        public Reposts[] newArray(final int size) {
            return new Reposts[size];
        }
    };

    public ContentValues getContentValues(final long pRepostId) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantStrings.DB.RepostsTable.ID, pRepostId);
        contentValues.put(ConstantStrings.DB.RepostsTable.COUNT_REPOST, mCountReposts);
        contentValues.put(ConstantStrings.DB.RepostsTable.USER_REPOST, mUserReposted);
        return contentValues;
    }
}
