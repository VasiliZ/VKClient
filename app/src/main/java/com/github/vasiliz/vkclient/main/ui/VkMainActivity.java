package com.github.vasiliz.vkclient.main.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;

import com.github.vasiliz.vkclient.R;
import com.github.vasiliz.vkclient.main.MainPresenterImpl;
import com.github.vasiliz.vkclient.mymvp.VkActivity;
import com.github.vasiliz.vkclient.mymvp.VkPresenter;

public class VkMainActivity extends VkActivity implements IMainView {
    private MainPresenterImpl mIMainPresenter;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vk_main_layout);
        mProgressBar = findViewById(R.id.main_progress);
        mIMainPresenter.loadNews();
    }

    @Override
    protected VkPresenter initPresenter() {
        return mIMainPresenter = new MainPresenterImpl(this);
    }

    @Override
    public void showProgress() {
        if (!isVisible(mProgressBar)){
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        if (isVisible(mProgressBar)){
            mProgressBar.setVisibility(View.GONE);
        }
    }
}
