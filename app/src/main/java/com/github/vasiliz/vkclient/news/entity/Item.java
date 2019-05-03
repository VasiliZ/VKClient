package com.github.vasiliz.vkclient.news.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.github.vasiliz.vkclient.base.db.config.Field;
import com.github.vasiliz.vkclient.base.db.config.Id;
import com.github.vasiliz.vkclient.base.db.config.Table;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Table
public class Item implements Parcelable {

    @Id
    private Long mLong;
    @Field
    @SerializedName("type")
    private String mType;
    @Field
    @SerializedName("source_id")
    private int mSourseId;
    @Field
    @SerializedName("date")
    private long mDate;
    @Field
    @SerializedName("post_id")
    private int mPostId;
    @Field
    @SerializedName("post_type")
    private String mPostType;
    @Field
    @SerializedName("text")
    private String mText;
    @Field
    @SerializedName("attachments")
    private List<Attachment> mAttachments;
    @Field
    @SerializedName("comments")
    private Comments mComments;
    @Field
    @SerializedName("likes")
    private Likes mLikes;
    @Field
    @SerializedName("reposts")
    private Reposts mReposts;
    @Field
    @SerializedName("views")
    private Views mViews;

    public Item() {
    }

    public String getType() {
        return mType;
    }

    public void setType(final String pType) {
        mType = pType;
    }

    public int getSourseId() {
        return mSourseId;
    }

    public void setSourseId(final int pSourseId) {
        mSourseId = pSourseId;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(final long pDate) {
        mDate = pDate;
    }

    public int getPostId() {
        return mPostId;
    }

    public void setPostId(final int pPostId) {
        mPostId = pPostId;
    }

    public String getPostType() {
        return mPostType;
    }

    public void setPostType(final String pPostType) {
        mPostType = pPostType;
    }

    public String getText() {
        return mText;
    }

    public void setText(final String pText) {
        mText = pText;
    }

    public List<Attachment> getAttachments() {
        if (mAttachments != null) {
            return Collections.unmodifiableList(mAttachments);
        }
        return null;
    }

    public void setAttachments(final List<Attachment> pAttachments) {
        mAttachments = pAttachments;
    }

    public Comments getComments() {
        return mComments;
    }

    public void setComments(final Comments pComments) {
        mComments = pComments;
    }

    public Likes getLikes() {
        return mLikes;
    }

    public void setLikes(final Likes pLikes) {
        mLikes = pLikes;
    }

    public Reposts getReposts() {
        return mReposts;
    }

    public void setReposts(final Reposts pReposts) {
        mReposts = pReposts;
    }

    public Views getViews() {
        return mViews;
    }

    public void setViews(final Views pViews) {
        mViews = pViews;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeValue(this.mLong);
        dest.writeString(this.mType);
        dest.writeInt(this.mSourseId);
        dest.writeLong(this.mDate);
        dest.writeInt(this.mPostId);
        dest.writeString(this.mPostType);
        dest.writeString(this.mText);
        dest.writeList(this.mAttachments);
        dest.writeParcelable(this.mComments, flags);
        dest.writeParcelable(this.mLikes, flags);
        dest.writeParcelable(this.mReposts, flags);
        dest.writeParcelable(this.mViews, flags);
    }

    private Item(final Parcel in) {
        this.mLong = (Long) in.readValue(Long.class.getClassLoader());
        this.mType = in.readString();
        this.mSourseId = in.readInt();
        this.mDate = in.readLong();
        this.mPostId = in.readInt();
        this.mPostType = in.readString();
        this.mText = in.readString();
        this.mAttachments = new ArrayList<Attachment>();
        in.readList(this.mAttachments, Attachment.class.getClassLoader());
        this.mComments = in.readParcelable(Comments.class.getClassLoader());
        this.mLikes = in.readParcelable(Likes.class.getClassLoader());
        this.mReposts = in.readParcelable(Reposts.class.getClassLoader());
        this.mViews = in.readParcelable(Views.class.getClassLoader());
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(final Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(final int size) {
            return new Item[size];
        }
    };
}
