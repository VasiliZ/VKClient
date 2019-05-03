package com.github.vasiliz.vkclient.login.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.github.vasiliz.vkclient.R;
import com.github.vasiliz.vkclient.base.utils.ConstantStrings;
import com.github.vasiliz.vkclient.login.LoginPresenter;
import com.github.vasiliz.vkclient.login.LoginWebView;
import com.github.vasiliz.vkclient.mymvp.VkActivity;
import com.github.vasiliz.vkclient.mymvp.VkPresenter;
import com.github.vasiliz.vkclient.news.ui.VkMainActivity;

public class LoginActivity extends VkActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private SharedPreferences mSharedPreferences;
    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoginPresenter.checkLogin();
    }

    @Override
    protected VkPresenter initPresenter() {
        mSharedPreferences = getSharedPreferences(ConstantStrings.Preferences.ACCESS_TOKEN_PREFERENCE, MODE_PRIVATE);
        mLoginPresenter = new LoginPresenter(mSharedPreferences, this);
        return mLoginPresenter;

    }

    public void goToMainScreen() {
        final Intent intent = new Intent(this, VkMainActivity.class);
        intent.putExtra(ConstantStrings.ApiVK.TOKEN_NAME,
                mLoginPresenter.getToken());
        startActivity(intent);

    }

    public void init() {
        final WebView webView = findViewById(R.id.login_web_view);
        webView.loadUrl(ConstantStrings.ApiVK.BASE_URL + ConstantStrings.ApiVK.LOGIN_VK_REQUEST);
        webView.setWebViewClient(new LoginWebView(mLoginPresenter));
    }

}
