package com.github.vasiliz.vkclient.news.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.github.vasiliz.vkclient.R;
import com.github.vasiliz.vkclient.base.utils.ConstantStrings;
import com.github.vasiliz.vkclient.login.ui.LoginActivity;
import com.github.vasiliz.vkclient.mymvp.VkActivity;
import com.github.vasiliz.vkclient.mymvp.VkPresenter;
import com.github.vasiliz.vkclient.news.MainPresenterImpl;
import com.github.vasiliz.vkclient.news.entity.Groups;
import com.github.vasiliz.vkclient.news.entity.Item;
import com.github.vasiliz.vkclient.news.entity.Profile;
import com.github.vasiliz.vkclient.news.entity.Response;
import com.github.vasiliz.vkclient.news.newsItem.NewsItemActivity;
import com.github.vasiliz.vkclient.news.ui.adapters.NewsAdapter;
import com.github.vasiliz.vkclient.news.ui.listeners.OnClickListener;
import com.github.vasiliz.vkclient.news.ui.listeners.PaginationScrollListener;

import java.util.ArrayList;
import java.util.List;

public class VkMainActivity extends VkActivity implements IMainView, SwipeRefreshLayout.OnRefreshListener {

    private MainPresenterImpl mIMainPresenter;
    private RecyclerView mRecyclerView;
    private NewsAdapter mNewsAdapter;
    private boolean isLastPage;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String mAccessKey;
    private boolean isLoading;

    @Override
    public void onRefresh() {
        isLastPage = false;
        mNewsAdapter.clear();
        mIMainPresenter.loadNews();
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vk_main_layout);
        getLifecycleRegistry().addObserver(mIMainPresenter);
        mRecyclerView = findViewById(R.id.news_recycler_view);
        mSwipeRefreshLayout = findViewById(R.id.swipe_to_refresh);
        initRecyclerView();
        mSwipeRefreshLayout.setOnRefreshListener(this);
        if (savedInstanceState != null) {
            final Response response
                    = savedInstanceState.getParcelable(ConstantStrings.AppConst.SAVE_NEWS);
            mIMainPresenter.notification(response);
        } else {
            mIMainPresenter.loadNews();
        }
    }

    @Override
    protected VkPresenter initPresenter() {
        final Intent intent = getIntent();
        mAccessKey = intent.getStringExtra(ConstantStrings.ApiVK.TOKEN_NAME);
        return mIMainPresenter =
                new MainPresenterImpl(this, mAccessKey);

    }

    public void initRecyclerView() {
        mNewsAdapter = new NewsAdapter(this, new OnClickListener() {
            @Override
            public void onItemClick(final Item pItem, final List<Groups> pGroups, final List<Profile> pProfiles) {
                final Intent intent = new Intent(VkMainActivity.this, NewsItemActivity.class);
                final Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("groups", new ArrayList<Parcelable>(pGroups));
                bundle.putParcelable("item", pItem);
                bundle.putParcelableArrayList("profiles", new ArrayList<Parcelable>(pProfiles));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mNewsAdapter);

        mRecyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {

            @Override
            protected void loadMoreItems() {
                isLoading = true;
                //    currentPage++;
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

    @Override
    public void onLoadError() {
        Toast.makeText(this, R.string.error_load, Toast.LENGTH_LONG).show();
    }

    @Override
    public void goToLogin() {
        final SharedPreferences sharedPreferences = getSharedPreferences(ConstantStrings.Preferences.ACCESS_TOKEN_PREFERENCE, MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(ConstantStrings.ApiVK.TOKEN_NAME);
        editor.apply();
        final Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        outState.putParcelable(ConstantStrings.AppConst.SAVE_NEWS, mIMainPresenter.getSavingData());
        super.onSaveInstanceState(outState);
    }
}
