package com.github.vasiliz.vkclient.news.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.vasiliz.vkclient.R;
import com.github.vasiliz.vkclient.news.entity.Audio;

import java.util.List;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.AudioItemHolder> {

    private final LayoutInflater mInflater;
    private final List<Audio> mAudios;

    SongsAdapter(final Context pContext, final List<Audio> pSongs) {
        mAudios = pSongs;
        mInflater = LayoutInflater.from(pContext);
    }

    @NonNull
    @Override
    public AudioItemHolder onCreateViewHolder(@NonNull final ViewGroup pViewGroup, final int pI) {
        final View view = mInflater.inflate(R.layout.audio_item, pViewGroup, false);
        return new AudioItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AudioItemHolder pAudioItemHolder, final int pI) {
        final Audio audio = mAudios.get(pI);
        pAudioItemHolder.mTextView.setText(audio.getArtist());
        pAudioItemHolder.mNameSongTextView.setText(audio.getmNameSong());
        pAudioItemHolder.mDurationSong.setText(String.valueOf(audio.getmSongDuration()));
    }

    @Override
    public int getItemCount() {
        return mAudios.size();
    }

    class AudioItemHolder extends RecyclerView.ViewHolder {

        private final TextView mTextView;
        private final TextView mNameSongTextView;
        private final TextView mDurationSong;

        AudioItemHolder(@NonNull final View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.title_music_text_view);
            mNameSongTextView = itemView.findViewById(R.id.name_sound_text_view);
            mDurationSong = itemView.findViewById(R.id.sound_duration_text_view);
        }
    }

}
