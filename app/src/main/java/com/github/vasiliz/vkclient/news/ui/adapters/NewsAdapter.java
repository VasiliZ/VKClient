package com.github.vasiliz.vkclient.news.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.vasiliz.vkclient.R;
import com.github.vasiliz.vkclient.news.entity.Item;
import com.github.vasiliz.vkclient.news.entity.Response;
import com.github.vasiliz.vkclient.news.ui.views.CircleView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private Response mResponse;
    private LayoutInflater mLayoutInflater;


    public NewsAdapter(Context pContext, Response pResponse) {
        mLayoutInflater = LayoutInflater.from(pContext);
        mResponse = pResponse;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull final ViewGroup pViewGroup, final int pI) {
        View view = mLayoutInflater.inflate(R.layout.item_news, pViewGroup, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsViewHolder pViewHolder, final int pI) {
        Item item = mResponse.getResponseNews().getItemList().get(pI);
        pViewHolder.mTextNews.setText(item.getText());
    }

    @Override
    public int getItemCount() {
        if (mResponse!=null) {
            return mResponse.getResponseNews().getItemList().size();
        }
        return 0;
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        private CircleView mCircleView;
        private TextView mTextNews;

        public NewsViewHolder(@NonNull final View itemView) {
            super(itemView);
            mCircleView = itemView.findViewById(R.id.avatar_image_view);
            mTextNews = itemView.findViewById(R.id.news_text_view);
        }
    }

    public void setItems(final Response pResponseNews) {

    }



}
