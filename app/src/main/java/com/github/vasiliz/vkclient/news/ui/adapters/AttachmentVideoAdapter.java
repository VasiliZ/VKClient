package com.github.vasiliz.vkclient.news.ui.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.github.vasiliz.myimageloader.ImageLoader;
import com.github.vasiliz.vkclient.R;
import com.github.vasiliz.vkclient.news.entity.Video;

import java.util.List;

public class AttachmentVideoAdapter extends RecyclerView.Adapter<AttachmentVideoAdapter.ItemVideoHolder> {

    private LayoutInflater mLayoutInflater;
    private List<Video> mVideos;

    public AttachmentVideoAdapter(final Context pContext, final List<Video> pVideos) {
        mLayoutInflater = LayoutInflater.from(pContext);
        mVideos = pVideos;
    }

    @NonNull
    @Override
    public ItemVideoHolder onCreateViewHolder(@NonNull final ViewGroup pViewGroup, final int pI) {
        final View view = mLayoutInflater.inflate(R.layout.video_item, pViewGroup, false);
        return new ItemVideoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemVideoHolder pItemVideoHolder, final int pI) {
        final Video video = mVideos.get(pI);
        pItemVideoHolder.mTextView.setText(video.getSource());


    }

    @Override
    public int getItemCount() {
        return mVideos.size();
    }

    class ItemVideoHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;
        private VideoView mPrewiewImage;

        public ItemVideoHolder(@NonNull final View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.video_text);
            mPrewiewImage = itemView.findViewById(R.id.preview_video_image);
        }
    }

}
