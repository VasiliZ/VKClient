package com.github.vasiliz.vkclient.login;

import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.vasiliz.vkclient.base.utils.ConstantStrings;

public class LoginWebView extends WebViewClient {

    private static final String TAG = LoginWebView.class.getSimpleName();
    private final ILoginView mLoginPresenter;

    public LoginWebView(final ILoginView pLoginPresenter) {
        mLoginPresenter = pLoginPresenter;
    }

    @Override
    public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
        if (url.contains(ConstantStrings.ApiVK.CHECK_ACCESS_TOKEN)) {
            Log.d(TAG, "shouldOverrideUrlLoading: " + url);
            mLoginPresenter.saveToken(url);
        }
        return false;
    }
}
