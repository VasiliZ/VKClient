package com.github.vasiliz.vkclient.news.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.vasiliz.vkclient.R;
import com.github.vasiliz.vkclient.news.entity.Doc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class OtherDocAdapter extends RecyclerView.Adapter<OtherDocAdapter.OtherDocsHolder> {
    private final List<Doc> mDocs = new ArrayList<Doc>();
    private final LayoutInflater mLayoutInflater;

    OtherDocAdapter(final Context pContext, final Collection<Doc> pDocs) {
        mDocs.addAll(pDocs);
        mLayoutInflater = LayoutInflater.from(pContext);
    }

    @NonNull
    @Override
    public OtherDocsHolder onCreateViewHolder(@NonNull final ViewGroup pViewGroup, final int pI) {
        final View view = mLayoutInflater.inflate(R.layout.other_doc_item, pViewGroup, false);
        return new OtherDocsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OtherDocsHolder pOtherDocsHolder, final int pPosition) {
        final Doc doc = mDocs.get(pPosition);
        pOtherDocsHolder.mTitleTextView.setText(doc.getTitle());
        pOtherDocsHolder.mSizeDocTextView
                .setText(String.format("%s %s", mLayoutInflater
                                .getContext()
                                .getResources()
                                .getString(R.string.doc),
                        reformatSizeValue(doc.getSize())));
    }

    @Override
    public int getItemCount() {
        return mDocs.size();
    }

    private String reformatSizeValue(final int pSizeFile) {
        final int Mbytes = pSizeFile / 1048576;
        final int bytes = pSizeFile % 1024 % 10;
        return String.format("%x.%x Mb", Mbytes, bytes);
    }

    class OtherDocsHolder extends RecyclerView.ViewHolder {
        private final TextView mTitleTextView;
        private final TextView mSizeDocTextView;

        OtherDocsHolder(@NonNull final View itemView) {
            super(itemView);

            mTitleTextView = itemView.findViewById(R.id.title_doc_text);
            mSizeDocTextView = itemView.findViewById(R.id.size_doc_text);
        }
    }
}
