package com.github.vasiliz.vkclient.main.ui;

import com.github.vasiliz.vkclient.mymvp.VkActivity;
import com.github.vasiliz.vkclient.mymvp.VkBaseView;
import com.github.vasiliz.vkclient.mymvp.VkPresenter;

public class VkMainActivity extends VkActivity implements VkBaseView {

    @Override
    protected VkPresenter<VkBaseView> initPresenter() {
        return null;
    }

    @Override
    public void showProgress() {
        super.showProgress();
    }

    @Override
    public void hideProgress() {
        super.hideProgress();
    }
}
