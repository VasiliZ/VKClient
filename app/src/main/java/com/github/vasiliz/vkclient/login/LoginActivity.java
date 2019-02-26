package com.github.vasiliz.vkclient.login;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;

import com.github.vasiliz.vkclient.base.utils.ConstantStrings;
import com.github.vasiliz.vkclient.R;
import com.github.vasiliz.vkclient.base.utils.StringUtils;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSharedPreferences = getSharedPreferences(ConstantStrings.Preferences.ACCESS_TOKEN_PREFERENCE, MODE_PRIVATE);

        if (checkLogin()) {
            goToMainScreen();
        } else {
            init();
        }
    }

    private void goToMainScreen() {
    }

    private void init() {

        final WebView webView = findViewById(R.id.login_web_view);
        webView.loadUrl(ConstantStrings.ApiVK.BASE_URL + ConstantStrings.ApiVK.LOGIN_VK_REQUEST);
        webView.setWebViewClient(new LoginWebView(this));
    }

    void saveToken(final String pAccessToken) {
        final SharedPreferences.Editor editor = mSharedPreferences.edit();
        final String token = StringUtils.getAccessToken(pAccessToken);
        if (token != null) {
            editor.putString(ConstantStrings.ApiVK.TOKEN_NAME, token);
            editor.apply();
            Log.d(TAG, "saveToken: " + token);
        }
    }

    private boolean checkLogin() {
        final String token = mSharedPreferences.getString(ConstantStrings.Preferences.ACCESS_TOKEN_PREFERENCE, "");
        return token != null;
    }
}
