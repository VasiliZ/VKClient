package com.github.vasiliz.vkclient.login;

import android.content.Context;
import android.content.SharedPreferences;

import com.github.vasiliz.vkclient.base.utils.ConstantStrings;
import com.github.vasiliz.vkclient.base.utils.StringUtils;

public class LoginModel {

    private final SharedPreferences mSharedPreferences;
    private final LoginPresenter mLoginPresenter;

    LoginModel(final SharedPreferences pSharedPreferences, final LoginPresenter pLoginPresenter) {
        mLoginPresenter = pLoginPresenter;
        mSharedPreferences = pSharedPreferences;
    }

    void saveToken(final String pAccessToken) {
        final SharedPreferences.Editor editor = mSharedPreferences.edit();
        final String token = StringUtils.getAccessToken(pAccessToken);
        if (!"".equals(token)) {
            editor.putString(ConstantStrings.ApiVK.TOKEN_NAME, token);
            editor.apply();
            mLoginPresenter.checkLogin();
        } else {
            mLoginPresenter.reAuth();
        }
    }

    public boolean checkLogin() {
        final String token = mSharedPreferences.getString(ConstantStrings.ApiVK.TOKEN_NAME, "");
        return !token.equals("");
    }

    String getToken() {
        return mSharedPreferences.getString(ConstantStrings.ApiVK.TOKEN_NAME, "");
    }

    void removePreference() {

        final SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.remove(ConstantStrings.ApiVK.TOKEN_NAME);
        editor.apply();
    }
}
