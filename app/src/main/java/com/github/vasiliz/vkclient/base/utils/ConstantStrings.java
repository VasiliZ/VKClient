package com.github.vasiliz.vkclient.base.utils;

public interface ConstantStrings {

    interface ApiVK{
        String BASE_URL = "https://oauth.vk.com/";
        String LOGIN_VK_REQUEST = "authorize?client_id=6745673&display=page&redirect_uri=https://oauth.vk.com/blank.html&scope=wall,video,friends,messages,photos,offline&response_type=token&v=5.68&state=123456";
        String CHECK_ACCESS_TOKEN = "access_token";
        String TOKEN_NAME = "vkToken";
    }

    interface Preferences{
        String ACCESS_TOKEN_PREFERENCE = "settings";
    }

}
