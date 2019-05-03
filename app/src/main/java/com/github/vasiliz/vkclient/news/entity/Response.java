package com.github.vasiliz.vkclient.news.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Response implements Parcelable {
    @SerializedName("response")
    private ResponseNews mResponseNews;

    public Response() {
    }

    public ResponseNews getResponseNews() {
        return mResponseNews;
    }

    public void setResponseNews(final ResponseNews pResponseNews) {
        mResponseNews = pResponseNews;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeParcelable(this.mResponseNews, flags);
    }

    protected Response(final Parcel in) {
        this.mResponseNews = in.readParcelable(ResponseNews.class.getClassLoader());
    }

    public static final Creator<Response> CREATOR = new Creator<Response>() {
        @Override
        public Response createFromParcel(final Parcel source) {
            return new Response(source);
        }

        @Override
        public Response[] newArray(final int size) {
            return new Response[size];
        }
    };
}
