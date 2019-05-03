package com.github.vasiliz.vkclient.news.ui.adapters.decorator;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RecyclerViewMargin extends RecyclerView.ItemDecoration {

    private final int mSpace;

    public RecyclerViewMargin(final int pCpace) {
        mSpace = pCpace;
    }

    @Override
    public void getItemOffsets(@NonNull final Rect outRect, @NonNull final View view, @NonNull final RecyclerView parent, @NonNull final RecyclerView.State state) {
        outRect.left = mSpace;
        outRect.right = mSpace;
        outRect.bottom = mSpace;

        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = mSpace;
        } else {
            outRect.top = 0;
        }
    }
}
