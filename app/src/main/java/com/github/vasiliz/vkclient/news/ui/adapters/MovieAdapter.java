package com.github.vasiliz.vkclient.news.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.vasiliz.myimageloader.ImageLoader;
import com.github.vasiliz.vkclient.R;
import com.github.vasiliz.vkclient.news.entity.Video;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {
    private final List<Video> mVideoList = new ArrayList<Video>();
    private final LayoutInflater mLayoutInflater;

    MovieAdapter(final Context context, final Collection<Video> pVideoList) {
        mVideoList.addAll(pVideoList);
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MovieAdapter.MovieHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        final View view = mLayoutInflater.inflate(R.layout.video_item, viewGroup, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieAdapter.MovieHolder viewHolder, final int pPosition) {
        final Video video = mVideoList.get(pPosition);

        final int videoDuration = video.getDurationVideo();
        viewHolder.mVideoDuration.setText(calculateTime(videoDuration));
        viewHolder.mTitleVideo.setText(video.getTitleVideo());
        ImageLoader.getInstance()
                .with(mLayoutInflater.getContext())
                .load(video.getUrlPhotoVideo())
                .into(viewHolder.mImageView);
    }

    private String calculateTime(final int pDurationInSeconds) {
        final StringBuilder buildStringTime = new StringBuilder();
        final int secondInHour = 3600;
        final int secondsInMinute = 60;
        final int seconds;
        final int minutes;
        final int hours;
        hours = pDurationInSeconds / secondInHour;
        minutes = (pDurationInSeconds % secondInHour) / secondsInMinute;
        seconds = (pDurationInSeconds % secondInHour) % secondsInMinute;
        if (hours != 0) {
            buildStringTime.append(hours).append(":");
        } else if (minutes != 0) {
            buildStringTime.append(minutes).append(":");
        }

        if (seconds == 0) {
            buildStringTime.append("00");
        } else {
            buildStringTime.append(seconds);
        }
        return buildStringTime.toString();
    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }

    class MovieHolder extends RecyclerView.ViewHolder {
        private final ImageView mImageView;
        private final TextView mVideoDuration;
        private final TextView mTitleVideo;

        MovieHolder(@NonNull final View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.first_frame_video);
            mVideoDuration = itemView.findViewById(R.id.video_duration);
            mTitleVideo = itemView.findViewById(R.id.title_video);
        }
    }
}
