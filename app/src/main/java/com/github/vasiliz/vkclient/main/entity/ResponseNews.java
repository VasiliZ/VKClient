package com.github.vasiliz.vkclient.main.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseNews {
    @SerializedName("items")
    private List<Item> mItemList;
    @SerializedName("profiles")
    private List<Profile> mProfileList;
    @SerializedName("groups")
    private List<Groups> mGroupsList;

    public ResponseNews() {
    }

    public ResponseNews(final List<Item> pItemList) {
        mItemList = pItemList;
    }

    public List<Item> getItemList() {
        return mItemList;
    }

    public void setItemList(final List<Item> pItemList) {
        mItemList = pItemList;
    }

    public List<Profile> getProfileList() {
        return mProfileList;
    }

    public void setProfileList(final List<Profile> pProfileList) {
        mProfileList = pProfileList;
    }

    public List<Groups> getGroupsList() {
        return mGroupsList;
    }

    public void setGroupsList(final List<Groups> pGroupsList) {
        mGroupsList = pGroupsList;
    }

}
