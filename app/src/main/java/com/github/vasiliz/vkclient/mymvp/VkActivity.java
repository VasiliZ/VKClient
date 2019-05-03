package com.github.vasiliz.vkclient.mymvp;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleRegistry;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class VkActivity extends AppCompatActivity implements VkBaseView {

    private VkPresenter<? super VkBaseView> mVkPresenter;
    private LifecycleRegistry mLifecycleRegistry;


    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLifecycleRegistry = new LifecycleRegistry(this);
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);
        mVkPresenter = initPresenter();
        mVkPresenter.attachView(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLifecycleRegistry.markState(Lifecycle.State.STARTED);
    }

    protected abstract VkPresenter initPresenter();

    @Override
    protected void onResume() {
        super.onResume();
        mLifecycleRegistry.markState(Lifecycle.State.RESUMED);
    }


    @Override
    protected void onDestroy() {
        mLifecycleRegistry.markState(Lifecycle.State.DESTROYED);
        super.onDestroy();

    }

    public LifecycleRegistry getLifecycleRegistry() {
        return mLifecycleRegistry;
    }
}
