package com.github.vasiliz.vkclient.api;

import android.support.annotation.Nullable;

import com.github.vasiliz.vkclient.base.api.GET;
import com.github.vasiliz.vkclient.base.api.Query;

public interface RequestNews {

    @GET(method = "method/newsfeed.get")
    void getNews(@Query("filters") String pFilters,
                 @Query("access_token") String pToken,
                 @Query("v") String pVersion);
}
