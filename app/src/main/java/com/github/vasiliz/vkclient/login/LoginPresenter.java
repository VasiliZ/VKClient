package com.github.vasiliz.vkclient.login;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.github.vasiliz.vkclient.login.ui.LoginActivity;
import com.github.vasiliz.vkclient.mymvp.VkBaseView;
import com.github.vasiliz.vkclient.mymvp.VkPresenter;

public class LoginPresenter extends VkPresenter<VkBaseView> implements ILoginView {

    private final LoginModel mLoginModel;
    private final LoginActivity mLoginActivity;

    public LoginPresenter(final SharedPreferences pSharedPreferences, final LoginActivity pLoginActivity) {
        mLoginModel = new LoginModel(pSharedPreferences, this);
        mLoginActivity = pLoginActivity;
    }

    @Override
    public void saveToken(final String pUrl) {
        mLoginModel.saveToken(pUrl);
    }

    @Override
    public void checkLogin() {
        if (mLoginModel.checkLogin()) {
            mLoginActivity.goToMainScreen();
        } else {
            mLoginActivity.init();
        }
    }

    @Override
    public String getToken() {
        return mLoginModel.getToken();
    }

    @Override
    public void reAuth() {
        mLoginModel.removePreference();
        mLoginActivity.init();
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLoginActivity.getLifecycle();
    }
}
