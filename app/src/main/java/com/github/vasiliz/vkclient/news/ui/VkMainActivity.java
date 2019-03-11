package com.github.vasiliz.vkclient.news.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.github.vasiliz.vkclient.R;
import com.github.vasiliz.vkclient.news.MainPresenterImpl;
import com.github.vasiliz.vkclient.mymvp.VkActivity;
import com.github.vasiliz.vkclient.mymvp.VkPresenter;
import com.github.vasiliz.vkclient.news.entity.Response;
import com.github.vasiliz.vkclient.news.ui.adapters.NewsAdapter;

public class VkMainActivity extends VkActivity implements IMainView{
    private MainPresenterImpl mIMainPresenter;
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vk_main_layout);
        mProgressBar = findViewById(R.id.main_progress);
        mRecyclerView = findViewById(R.id.news_recycler_view);
        mIMainPresenter.loadNews();
    }


    @Override
    protected VkPresenter initPresenter() {
        return mIMainPresenter = new MainPresenterImpl(this);
    }

    @Override
    public void showProgress() {
        if (!isVisible(mProgressBar)){
            mRecyclerView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        if (isVisible(mProgressBar)){
            mProgressBar.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    public void initRecyclerWithData(final Response pResponse){
        final NewsAdapter newsAdapter = new NewsAdapter(this, pResponse);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(newsAdapter);
    }
}
