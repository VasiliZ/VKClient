package com.github.vasiliz.vkclient.news.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.vasiliz.vkclient.R;
import com.github.vasiliz.vkclient.news.entity.Groups;
import com.github.vasiliz.vkclient.news.entity.Item;
import com.github.vasiliz.vkclient.news.entity.Profile;
import com.github.vasiliz.vkclient.news.ui.listeners.OnClickListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ItemsAdapter extends ListAdapter<Item, NewsViewHolder> {
    private final LayoutInflater mLayoutInflater;
    private final List<Item> mItems;
    private final List<Groups> mGroups;
    private final List<Profile> mProfiles;
    private final OnClickListener mOnClickListener;
  //  private final String TAG = NewsAdapter.class.getSimpleName();

    public ItemsAdapter(final Context pContext, final OnClickListener pOnClickListener) {
        super(Item.DIFF_CALLBACK);

        mLayoutInflater = LayoutInflater.from(pContext);
        mItems = new ArrayList<Item>();
        mGroups = new ArrayList<Groups>();
        mProfiles = new ArrayList<Profile>();
        mOnClickListener = pOnClickListener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull final ViewGroup pViewGroup, final int pI) {
        final View view = mLayoutInflater.inflate(R.layout.item_news, pViewGroup, false);
        return new NewsViewHolder(view, mLayoutInflater, mGroups, mProfiles, mOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsViewHolder pNewsAdapter, final int pI) {
        pNewsAdapter.onBind(mItems.get(pI), mLayoutInflater.getContext());
    }

    public void submitOtherInfo(final Collection<Profile> pProfileList, final Collection<Groups> pGroupsList) {
        mGroups.addAll(pGroupsList);
        mProfiles.addAll(pProfileList);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public void submitList(@Nullable final List<Item> list) {
        if (list != null) {
            mItems.clear();
            mItems.addAll(list);
        }
    }
}

