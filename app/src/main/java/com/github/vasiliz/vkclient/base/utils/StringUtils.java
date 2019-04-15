package com.github.vasiliz.vkclient.base.utils;

import android.net.Uri;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class StringUtils {

    private static final String TAG = StringUtils.class.getSimpleName();

    public static String getAccessToken(final String pTokenConteiner) {
        final Uri uri = Uri.parse(pTokenConteiner);
        String someToken = null;

        final String[] parameters = uri.getFragment().split("&");
        for (final String s : parameters) {
            Log.d(TAG, "getAccessToken: " + s);
            final String[] parts = s.split("=");
            if (parts[0].equals(ConstantStrings.ApiVK.CHECK_ACCESS_TOKEN)) {
                someToken = parts[1];
            }
        }
        Log.d(TAG, "getAccessToken: "+someToken);
        return someToken;
    }

    public static String replace(final String pS){
        return pS.replace("\"", "");
    }

    public static String getDateFromLong(final long pDate){
        final Date date = new Date(pDate*1000);
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(date);
    }
}
