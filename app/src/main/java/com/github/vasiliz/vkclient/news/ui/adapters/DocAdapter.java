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
import com.github.vasiliz.vkclient.news.entity.Doc;

import java.util.List;

public class DocAdapter extends RecyclerView.Adapter<DocAdapter.DocItemHolder> {

    private final LayoutInflater mLayoutInflater;
    private final List<Doc> mDocs;

    DocAdapter(final Context pContext, final List<Doc> pDocList) {
        mLayoutInflater = LayoutInflater.from(pContext);
        mDocs = pDocList;
    }

    @NonNull
    @Override
    public DocItemHolder onCreateViewHolder(@NonNull final ViewGroup pViewGroup, final int pI) {
        final View view = mLayoutInflater.inflate(R.layout.doc_item, pViewGroup, false);
        return new DocItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DocItemHolder pDocItemHolder, final int pI) {
        final Doc doc = mDocs.get(pI);
        pDocItemHolder.mTitleDoc.setText(doc.getTitle());
        if (doc.getPreview() != null) {
            ImageLoader.getInstance()
                    .with(mLayoutInflater.getContext())
                    .load(doc.getPreview()
                            .getPhotoPreview()
                            .getSizesPhotoPreviews()
                            .get(2)
                            .getSourse())
                    .into(pDocItemHolder.mImagePreview);

            pDocItemHolder.mDocExt.setText(doc.getExt());
        }
    }

    @Override
    public int getItemCount() {
        return mDocs.size();
    }

    class DocItemHolder extends RecyclerView.ViewHolder {

        private final TextView mTitleDoc;
        private final TextView mDocExt;
        private final ImageView mImagePreview;

        DocItemHolder(@NonNull final View itemView) {
            super(itemView);

            mTitleDoc = itemView.findViewById(R.id.doc_title_text);
            mDocExt = itemView.findViewById(R.id.doc_ext);
            mImagePreview = itemView.findViewById(R.id.image_doc_preview);
        }
    }

}
