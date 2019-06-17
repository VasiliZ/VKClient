package com.github.vasiliz.vkclient.news.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.github.vasiliz.vkclient.news.ui.adapters.ItemsAdapter;
import com.github.vasiliz.vkclient.news.ui.adapters.NewsAdapter;
import com.github.vasiliz.vkclient.news.ui.listeners.OnClickListener;
import com.github.vasiliz.vkclient.news.ui.listeners.PaginationScrollListener;

import java.util.ArrayList;
import java.util.List;

public class VkMainActivity extends VkActivity implements IMainView, SwipeRefreshLayout.OnRefreshListener {

    private MainPresenterImpl mIMainPresenter;
    private RecyclerView mRecyclerView;
    private boolean isLastPage;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean isLoading;
    private ItemsAdapter mItemsAdapter;
    private String TAG = VkMainActivity.class.getSimpleName();

    @Override
    public void onRefresh() {
        isLastPage = false;

        mIMainPresenter.loadNews();
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vk_main_layout);
        getLifecycleRegistry().addObserver(mIMainPresenter);

        final Toolbar toolbar = findViewById(R.id.tool_bar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(R.string.news_toolbar_title);
        setSupportActionBar(toolbar);

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
        final String accessKey = intent.getStringExtra(ConstantStrings.ApiVK.TOKEN_NAME);
        return mIMainPresenter =
                new MainPresenterImpl(this, accessKey);

    }

    public void initRecyclerView() {
        mItemsAdapter = new ItemsAdapter(this, new OnClickListener() {
            @Override
            public void onItemClick(final Item pItem, final List<Groups> pGroups, final List<Profile> pProfiles) {
                final Intent intent = new Intent(VkMainActivity.this, NewsItemActivity.class);
                final Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(ConstantStrings.AppConst.BUNDLE_GROUPS, new ArrayList<Parcelable>(pGroups));
                bundle.putParcelable(ConstantStrings.AppConst.ITEMS, pItem);
                bundle.putParcelableArrayList(ConstantStrings.AppConst.PROFILES, new ArrayList<Parcelable>(pProfiles));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }, this);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mItemsAdapter);

        mRecyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {

            @Override
            protected void loadMoreItems() {
                isLoading = true;
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
    public void showNotify() {
        Toast.makeText(this, "error set like", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setDataToAdapter(final Response pMessage) {
        mItemsAdapter.submitOtherInfo(pMessage.getResponseNews().getProfileList(),
                pMessage.getResponseNews().getGroupsList());
        mItemsAdapter.submitList(pMessage.getResponseNews().getItemList());
        mItemsAdapter.notifyDataSetChanged();
    }

    @Override
    public void clickLikeOnItem(final Item pItem) {
        Log.d(TAG, "clickLikeOnItem: " + pItem.getPostId());
        mIMainPresenter.doLike(pItem);
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        outState.putParcelable(ConstantStrings.AppConst.SAVE_NEWS, mIMainPresenter.getSavingData());
        super.onSaveInstanceState(outState);
    }
}
