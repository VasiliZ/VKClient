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
import com.github.vasiliz.vkclient.news.entity.Attachment;
import com.github.vasiliz.vkclient.news.entity.Photo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AttachmentImageAdapter extends RecyclerView.Adapter<AttachmentImageAdapter.ImagesAttachmentHolder> {

    private static final String TAG = AttachmentImageAdapter.class.getSimpleName();
    private final LayoutInflater mLayoutInflater;
    private List<Photo> mPhotos;

    public AttachmentImageAdapter(final Context pContext, final List<Photo> pPhotos) {
        mLayoutInflater = LayoutInflater.from(pContext);
        mPhotos = pPhotos;
    }

    @NonNull
    @Override
    public ImagesAttachmentHolder onCreateViewHolder(@NonNull final ViewGroup pViewGroup, final int pI) {
        final View view = mLayoutInflater.inflate(R.layout.images_item, pViewGroup, false);
        return new ImagesAttachmentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImagesAttachmentHolder pImagesAttachmentHolder, final int pI) {
        final Photo photo = mPhotos.get(pI);
        if (photo.getPhoto1280()!=null) {
            ImageLoader.getInstance()
                    .with(mLayoutInflater.getContext())
                    .load(photo.getPhoto1280())
                    .into(pImagesAttachmentHolder.mImageView);
        }else {
            ImageLoader.getInstance()
                    .with(mLayoutInflater.getContext())
                    .load(photo.getPhoto604())
                    .into(pImagesAttachmentHolder.mImageView);
        }
    }

    @Override
    public int getItemCount() {
        return mPhotos.size();
    }

    class ImagesAttachmentHolder extends RecyclerView.ViewHolder {

        private final ImageView mImageView;

        public ImagesAttachmentHolder(@NonNull final View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.img);
        }
    }

}
