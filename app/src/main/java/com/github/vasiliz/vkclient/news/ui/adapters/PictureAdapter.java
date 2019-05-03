package com.github.vasiliz.vkclient.news.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.vasiliz.myimageloader.ImageLoader;
import com.github.vasiliz.vkclient.R;
import com.github.vasiliz.vkclient.news.entity.Photo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PictureHolder> {

    private final List<Photo> mPhotos = new ArrayList<Photo>();
    private final LayoutInflater mLayoutInflater;

    PictureAdapter(final Context pContext, final Collection<Photo> pPhotos) {
        mPhotos.addAll(pPhotos);
        mLayoutInflater = LayoutInflater.from(pContext);

    }

    @NonNull
    @Override
    public PictureAdapter.PictureHolder onCreateViewHolder(@NonNull final ViewGroup pViewGroup, final int pViewType) {
        final View view = mLayoutInflater.inflate(R.layout.item_for_images, pViewGroup, false);
        return new PictureHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PictureAdapter.PictureHolder pViewHolder, final int pI) {

        final Photo photo = mPhotos.get(pI);
        ImageLoader.getInstance()
                .with(mLayoutInflater.getContext())
                .load(photo.getPhoto604())
                .into(pViewHolder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mPhotos.size();
    }

    class PictureHolder extends RecyclerView.ViewHolder {

        private final ImageView mImageView;

        PictureHolder(@NonNull final View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.img);
        }
    }
}
