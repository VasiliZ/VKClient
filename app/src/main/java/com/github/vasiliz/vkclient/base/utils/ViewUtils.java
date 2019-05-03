package com.github.vasiliz.vkclient.base.utils;

import android.view.View;

public final class ViewUtils {

    public static void setVisibilityGone(final View pView) {
        if (pView.getVisibility() == View.VISIBLE) {
            pView.setVisibility(View.GONE);
        }
    }

    public static void setVisible(final View pView) {
        if (pView.getVisibility() == View.GONE) {
            pView.setVisibility(View.VISIBLE);
        }
    }
}
