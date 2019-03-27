package com.github.vasiliz.vkclient.api;

import com.github.vasiliz.vkclient.base.api.GET;
import com.github.vasiliz.vkclient.base.api.Query;

public interface RequestService {

    @GET(method = "method/newsfeed.get")
    void getNews(@Query("filters") String pFilters,
                 @Query("access_token") String pToken,
                 @Query("v") String pVersion);
}
