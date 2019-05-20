package com.github.vasiliz.vkclient.news.entity;

import com.google.gson.annotations.SerializedName;

public class Error {
    @SerializedName("error_code")
    String mErrorCode;
    @SerializedName("error_msg")
    String mErrorMsg;
}
