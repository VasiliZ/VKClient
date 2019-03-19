package com.github.vasiliz.vkclient.news.ui.listeners;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {

    private LinearLayoutManager mLinearLayoutManager;

    private static int PAGE_SIZE = 40;

    public PaginationScrollListener(final LinearLayoutManager pLinearLayoutManager) {
        mLinearLayoutManager = pLinearLayoutManager;
    }

    @Override
    public void onScrolled(@NonNull final RecyclerView recyclerView, final int dx, final int dy) {
        super.onScrolled(recyclerView, dx, dy);

        final int visibleCount = mLinearLayoutManager.getChildCount();
        final int totalItemCount = mLinearLayoutManager.getItemCount()-10;

        final int firstVisiblePosition = mLinearLayoutManager.findFirstVisibleItemPosition()-10;

        if (!isLoading() && isLastPage()) {
            if ((visibleCount + firstVisiblePosition) >= totalItemCount
                    && firstVisiblePosition >= 0 && totalItemCount >= PAGE_SIZE) {
                loadMoreItems();
            }
        }
    }

    protected abstract void loadMoreItems();

    public abstract boolean isLastPage();

    public abstract boolean isLoading();
}
