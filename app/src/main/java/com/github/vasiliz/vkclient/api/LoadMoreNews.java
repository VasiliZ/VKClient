package com.github.vasiliz.vkclient.api;

import com.github.vasiliz.vkclient.base.api.GET;
import com.github.vasiliz.vkclient.base.api.Query;

public interface LoadMoreNews {

    @GET(method = "method/newsfeed.get")
    void getMore(@Query("filters") String pFilters,
                 @Query("access_token") String pToken,
                 @Query("start_from") String pStart,
                 @Query("v") String pVersion);

}
