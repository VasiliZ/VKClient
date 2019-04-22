package com.github.vasiliz.vkclient.news.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

public class ResponseNews {
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

}
