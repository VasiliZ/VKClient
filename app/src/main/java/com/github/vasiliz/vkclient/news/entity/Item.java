package com.github.vasiliz.vkclient.news.entity;

import com.github.vasiliz.vkclient.base.db.config.Field;
import com.github.vasiliz.vkclient.base.db.config.Id;
import com.github.vasiliz.vkclient.base.db.config.Table;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Table
public class Item {

    @Id
    private long idItem;

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
        return mAttachments;
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
}
