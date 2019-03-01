package com.github.vasiliz.vkclient.main.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.vasiliz.vkclient.R;
import com.github.vasiliz.vkclient.main.MainPresenterImpl;
import com.github.vasiliz.vkclient.mymvp.VkActivity;
import com.github.vasiliz.vkclient.mymvp.VkPresenter;

public class VkMainActivity extends VkActivity implements IMainView {
    private MainPresenterImpl mIMainPresenter;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vk_main_layout);
    }

    @Override
    protected VkPresenter initPresenter() {
        return mIMainPresenter = new MainPresenterImpl();
    }

}
