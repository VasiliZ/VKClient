package com.github.vasiliz.vkclient.news.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

public class ResponseNews implements Parcelable {
    @SerializedName("items")
    private List<Item> mItemList;
    @SerializedName("profiles")
    private List<Profile> mProfileList;
    @SerializedName("groups")
    private List<Groups> mGroupsList;
    @SerializedName("next_from")
    private String mNextNews;

    public ResponseNews() {
    }

    public String getNextNews() {
        return mNextNews;
    }

    public List<Item> getItemList() {
        return Collections.unmodifiableList(mItemList);
    }

    public void setItemList(final List<Item> pItemList) {
        mItemList = pItemList;
    }

    public List<Profile> getProfileList() {
        return Collections.unmodifiableList(mProfileList);
    }

    public void setProfileList(final List<Profile> pProfileList) {
        mProfileList = pProfileList;
    }

    public List<Groups> getGroupsList() {
        return Collections.unmodifiableList(mGroupsList);
    }

    public void setGroupsList(final List<Groups> pGroupsList) {
        mGroupsList = pGroupsList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.mItemList);
        dest.writeTypedList(this.mProfileList);
        dest.writeTypedList(this.mGroupsList);
        dest.writeString(this.mNextNews);
    }

    protected ResponseNews(Parcel in) {
        this.mItemList = in.createTypedArrayList(Item.CREATOR);
        this.mProfileList = in.createTypedArrayList(Profile.CREATOR);
        this.mGroupsList = in.createTypedArrayList(Groups.CREATOR);
        this.mNextNews = in.readString();
    }

    public static final Creator<ResponseNews> CREATOR = new Creator<ResponseNews>() {
        @Override
        public ResponseNews createFromParcel(Parcel source) {
            return new ResponseNews(source);
        }

        @Override
        public ResponseNews[] newArray(int size) {
            return new ResponseNews[size];
        }
    };
}
