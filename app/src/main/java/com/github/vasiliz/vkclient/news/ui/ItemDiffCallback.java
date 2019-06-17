package com.github.vasiliz.vkclient.news.ui;

import android.support.v7.util.DiffUtil;

import com.github.vasiliz.vkclient.news.entity.Item;

import java.util.List;

public class ItemDiffCallback extends DiffUtil.Callback {
    private List<Item> mNewItemList;
    private List<Item> mOldItemList;

    public ItemDiffCallback(final List<Item> newItemList, final List<Item> poldItemList) {
        mNewItemList = newItemList;
        mOldItemList = poldItemList;
    }

    @Override
    public int getOldListSize() {
        return mOldItemList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewItemList.size();
    }

    @Override
    public boolean areItemsTheSame(final int pI, final int pI1) {
        return mOldItemList.get(pI).getLikes().getCountLike() == mNewItemList.get(pI1).getLikes().getCountLike();
    }

    @Override
    public boolean areContentsTheSame(final int pI, final int pI1) {
        final Item newItem  = mNewItemList.get(pI1);
        final  Item oldItem = mOldItemList.get(pI);
        return oldItem.getLikes().getCountLike() == newItem.getLikes().getCountLike();
    }
}
