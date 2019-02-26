package com.github.vasiliz.vkclient.login;

import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.vasiliz.vkclient.base.utils.ConstantStrings;

public class LoginWebView extends WebViewClient {

    private static final String TAG = LoginWebView.class.getSimpleName();
    private final LoginActivity mLoginActivity;

    LoginWebView(final LoginActivity pLoginActivity) {
        mLoginActivity = pLoginActivity;
    }

    @Override
    public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
        if (url.contains(ConstantStrings.ApiVK.CHECK_ACCESS_TOKEN)) {
            Log.d(TAG, "shouldOverrideUrlLoading: "+ url);
            mLoginActivity.saveToken(url);
        }
        return false;
    }
}
