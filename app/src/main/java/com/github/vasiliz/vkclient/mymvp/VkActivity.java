package com.github.vasiliz.vkclient.mymvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public abstract class VkActivity extends AppCompatActivity implements VkBaseView {

     private VkPresenter<? super VkBaseView> mVkPresenter;


    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVkPresenter = initPresenter();
        mVkPresenter.attachView(this);
    }

    @Override
    public void showProgress(final View pView) {
        if (!isVisible(pView)) {
            pView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress(final View pView) {
        if (isVisible(pView)) {
            pView.setVisibility(View.GONE);
        }
    }

    protected abstract VkPresenter initPresenter();

    @Override
    protected void onResume() {
        super.onResume();
        mVkPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVkPresenter.onPause();
    }

    @Override
    protected void onDestroy() {
        mVkPresenter.detachView();
        super.onDestroy();

    }

    public boolean isVisible(final View pView) {
        return pView.getVisibility() == View.VISIBLE;
    }
}
