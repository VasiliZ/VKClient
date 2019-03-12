package com.github.vasiliz.vkclient.news.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.vasiliz.vkclient.R;
import com.github.vasiliz.vkclient.base.utils.StringUtils;
import com.github.vasiliz.vkclient.news.entity.Groups;
import com.github.vasiliz.vkclient.news.entity.Item;
import com.github.vasiliz.vkclient.news.entity.Profile;
import com.github.vasiliz.vkclient.news.entity.Response;
import com.github.vasiliz.vkclient.news.ui.views.CircleImage;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<Item> mItems;
    private List<Groups> mGroups;
    private List<Profile> mProfiles;

    public NewsAdapter(final Context pContext) {
        mLayoutInflater = LayoutInflater.from(pContext);
        mItems = new ArrayList<>();
        mGroups = new ArrayList<>();
        mProfiles = new ArrayList<>();

    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull final ViewGroup pViewGroup, final int pI) {
        final View view = mLayoutInflater.inflate(R.layout.item_news, pViewGroup, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsViewHolder pViewHolder, final int pPosition) {
        final Item item = mItems.get(pPosition);
        pViewHolder.mTextNews.setText(item.getText());
        pViewHolder.mCircleView.setImageResource(R.drawable.test_drawable);
        pViewHolder.mName.setText(findName(item));
        pViewHolder.mDate.setText(StringUtils.getDateFromLong(item.getDate()));
    }

    @Override
    public int getItemCount() {
        if (mItems != null) {
            return mItems.size();
        }
        return 0;
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {

        private CircleImage mCircleView;
        private TextView mTextNews;
        private TextView mName;
        private TextView mDate;

        public NewsViewHolder(@NonNull final View itemView) {
            super(itemView);
            mCircleView = itemView.findViewById(R.id.avatar_image_view);
            mTextNews = itemView.findViewById(R.id.news_text_view);
            mName = itemView.findViewById(R.id.name_group_or_profile_text_view);
            mDate = itemView.findViewById(R.id.time_post_text_view);

        }
    }

    public void setItems(final Response pResponseNews) {
        final List<Item> items = pResponseNews.getResponseNews().getItemList();
        final List<Groups> groups = pResponseNews.getResponseNews().getGroupsList();
        final List<Profile> profiles = pResponseNews.getResponseNews().getProfileList();

        mItems.addAll(items);
        mGroups.addAll(groups);
        mProfiles.addAll(profiles);

        items.clear();
        groups.clear();
        profiles.clear();

        notifyDataSetChanged();
    }

    private String findName(final Item pItem) {
        for (final Groups groups : mGroups) {
            if (pItem.getSourseId() == groups.getId() * -1) {
                return groups.getNameGroup();
            }
        }

        for (final Profile profile : mProfiles) {
            if (pItem.getSourseId() == profile.getId()) {
                return profile.getFirstName() + " " + profile.getLastName();
            }
        }
        return "";
    }

}
