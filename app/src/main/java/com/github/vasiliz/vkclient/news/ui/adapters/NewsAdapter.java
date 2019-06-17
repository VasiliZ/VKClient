package com.github.vasiliz.vkclient.news.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.vasiliz.myimageloader.ImageLoader;
import com.github.vasiliz.vkclient.R;
import com.github.vasiliz.vkclient.VkApplication;
import com.github.vasiliz.vkclient.news.MainPresenterImpl;
import com.github.vasiliz.vkclient.news.entity.Groups;
import com.github.vasiliz.vkclient.news.entity.Item;
import com.github.vasiliz.vkclient.news.entity.Profile;
import com.github.vasiliz.vkclient.news.ui.adapters.holders.NewsViewHolder;
import com.github.vasiliz.vkclient.news.ui.listeners.OnClickListener;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {

    private final LayoutInflater mLayoutInflater;
    private final List<Item> mItems;
    private final List<Groups> mGroups;
    private final List<Profile> mProfiles;
    private final ImageLoader mImageLoader;
    private final OnClickListener mOnClickListener;
    private final String TAG = NewsAdapter.class.getSimpleName();


    public NewsAdapter(final Context pContext, final OnClickListener pOnClickListener, final MainPresenterImpl pMainPresenter) {
        mLayoutInflater = LayoutInflater.from(pContext);
        mItems = new ArrayList<Item>();
        mGroups = new ArrayList<Groups>();
        mProfiles = new ArrayList<Profile>();
        mImageLoader = VkApplication.getmImageLoader();
        mOnClickListener = pOnClickListener;

    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull final ViewGroup pViewGroup, final int pI) {
        final View view = mLayoutInflater.inflate(R.layout.item_news, pViewGroup, false);
        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull final NewsViewHolder pViewHolder, final int pPosition) {

    }

    @Override
    public int getItemCount() {
        if (mItems != null) {
            return mItems.size();
        }
        return 0;
    }

    public void clear() {

    }
}


