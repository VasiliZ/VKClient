package com.github.vasiliz.vkclient.news.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.vasiliz.vkclient.R;
import com.github.vasiliz.vkclient.mymvp.VkActivity;
import com.github.vasiliz.vkclient.mymvp.VkPresenter;
import com.github.vasiliz.vkclient.news.MainPresenterImpl;
import com.github.vasiliz.vkclient.news.entity.Response;
import com.github.vasiliz.vkclient.news.ui.adapters.NewsAdapter;
import com.github.vasiliz.vkclient.news.ui.listeners.PaginationScrollListener;

public class VkMainActivity extends VkActivity implements IMainView, SwipeRefreshLayout.OnRefreshListener {

    private MainPresenterImpl mIMainPresenter;
    private RecyclerView mRecyclerView;
    private NewsAdapter mNewsAdapter;
    private boolean isLastPage;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private boolean isLoading;
    public static int PAGE_START = 1;
    private int currentPage = PAGE_START;

    @Override
    public void onRefresh() {
        currentPage = PAGE_START;
        isLastPage = false;
        mNewsAdapter.clear();
        mIMainPresenter.loadNews();
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vk_main_layout);
        mRecyclerView = findViewById(R.id.news_recycler_view);
        mSwipeRefreshLayout = findViewById(R.id.swipe_to_refresh);
        mIMainPresenter.loadNews();
        initRecyclerView();
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected VkPresenter initPresenter() {
        return mIMainPresenter = new MainPresenterImpl(this);
    }

    public void initRecyclerView() {
        mNewsAdapter = new NewsAdapter(this);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mNewsAdapter);

        mRecyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {

            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                mIMainPresenter.loadMoreNews();
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    public void setDataToAdapter(final Response pData) {
        mNewsAdapter.setItems(pData);
    }

    @Override
    public void dataLastPage(final boolean pIsLastPage) {
        isLastPage = pIsLastPage;
    }

    @Override
    public void loadMoreData(final boolean pIsLoading) {
        isLoading = pIsLoading;
    }

    @Override
    public SwipeRefreshLayout handleSwipe() {
        return mSwipeRefreshLayout;
    }



}
