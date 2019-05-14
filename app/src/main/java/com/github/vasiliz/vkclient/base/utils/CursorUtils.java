package com.github.vasiliz.vkclient.base.utils;

import android.database.Cursor;

public final class CursorUtils {

    private CursorUtils() {
    }

    public static void closeCursor(final Cursor pCursor) {
        if (!pCursor.isClosed() && pCursor != null) {
            pCursor.close();
        }
    }
}
