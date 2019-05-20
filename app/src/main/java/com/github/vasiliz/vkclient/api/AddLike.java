package com.github.vasiliz.vkclient.api;

import com.github.vasiliz.vkclient.base.api.GET;
import com.github.vasiliz.vkclient.base.api.Query;

public interface AddLike {
    @GET(method = "method/likes.add")
    void setLike(@Query("type") String pType,
                 @Query("item_id") String pItemId,
                 @Query("owner_id") String pOwnerId,
                 @Query("access_token") String pAccessToken,
                 @Query("v") String pVersion);
}
