package com.github.vasiliz.vkclient.news.ui.listeners;

import com.github.vasiliz.vkclient.news.entity.Groups;
import com.github.vasiliz.vkclient.news.entity.Item;
import com.github.vasiliz.vkclient.news.entity.Profile;

import java.util.List;

public interface OnClickListener {
    void onItemClick(Item pItem, List<Groups> pGroups, List<Profile> pProfiles);
}
