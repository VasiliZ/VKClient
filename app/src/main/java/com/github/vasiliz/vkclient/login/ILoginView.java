package com.github.vasiliz.vkclient.login;

import com.github.vasiliz.vkclient.mymvp.VkBaseView;

public interface ILoginView extends VkBaseView {

    void saveToken(String pUrl);

    void checkLogin();

    String getToken();

    void reAuth();

}
