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

public class AttachmentAudioAdapter extends RecyclerView.Adapter<AttachmentAudioAdapter.AudioItemHolder> {

    private LayoutInflater mInflater;
    private List<Audio> mAudios;

    public AttachmentAudioAdapter(final Context pContext, final List<Audio> pAudios) {
        mAudios = pAudios;
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
    }

    @Override
    public int getItemCount() {
        return mAudios.size();
    }

    class AudioItemHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public AudioItemHolder(@NonNull final View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.text_audio);
        }
    }

}
