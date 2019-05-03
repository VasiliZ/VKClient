package com.github.vasiliz.vkclient.news.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.vasiliz.myimageloader.ImageLoader;
import com.github.vasiliz.vkclient.R;
import com.github.vasiliz.vkclient.base.utils.ConstantStrings;
import com.github.vasiliz.vkclient.base.utils.ViewUtils;
import com.github.vasiliz.vkclient.news.entity.Audio;
import com.github.vasiliz.vkclient.news.entity.Doc;
import com.github.vasiliz.vkclient.news.entity.Link;
import com.github.vasiliz.vkclient.news.entity.Photo;
import com.github.vasiliz.vkclient.news.entity.Video;
import com.github.vasiliz.vkclient.news.ui.adapters.decorator.RecyclerViewMargin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AttachmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int PHOTO_TYPE = 0;
    private static final int AUDIO_TYPE = 1;
    private static final int VIDEO_TYPE = 2;
    private static final int DOC_TYPE = 3;
    private static final int LINK_TYPE = 4;
    private static final int OTHER_DOC = 5;
    private final Map<String, List> mAttachments;
    private final LayoutInflater mLayoutInflater;

    public AttachmentAdapter(final Context pContext) {
        mLayoutInflater = LayoutInflater.from(pContext);
        mAttachments = new LinkedHashMap<String, List>();
    }

    public void setData(final Map<String, List> pListWithContent) {
        if (!pListWithContent.isEmpty()) {
            mAttachments.putAll(pListWithContent);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup pViewGroup, final int pViewType) {
        switch (pViewType) {
            case PHOTO_TYPE:
                final View view = mLayoutInflater.inflate(R.layout.content_container, pViewGroup, false);
                return new ImageHolder(view);
            case AUDIO_TYPE:
                final View view1 = mLayoutInflater.inflate(R.layout.content_container, pViewGroup, false);
                return new AudioHolder(view1);
            case VIDEO_TYPE:
                final View videoView = mLayoutInflater.inflate(R.layout.content_container, pViewGroup, false);
                return new VideoHolder(videoView);
            case DOC_TYPE:
                final View docView = mLayoutInflater.inflate(R.layout.content_container, pViewGroup, false);
                return new DocHolder(docView);
            case LINK_TYPE:
                final View linkView = mLayoutInflater.inflate(R.layout.link_item, pViewGroup, false);
                return new LinkHolder(linkView);
            case OTHER_DOC:
                final View otherDocView = mLayoutInflater.inflate(R.layout.content_container, pViewGroup, false);
                return new OtherDocHolder(otherDocView);
            default:
                throw new IllegalArgumentException("invalid view type");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder pViewHolder, final int pPosition) {
        int countIterable = 0;
        final Set set = mAttachments.entrySet();

        for (final Object aSet : set) {

            if (pPosition == countIterable) {
                final Map.Entry entry = (Map.Entry) aSet;

                if (entry.getKey().equals(ConstantStrings.TypesAttachment.PHOTO)) {
                    final Collection<Photo> list = (Collection<Photo>) entry.getValue();
                    final ImageHolder imageHolder = (ImageHolder) pViewHolder;
                    imageHolder.bind(list);
                } else if (entry.getKey().equals(ConstantStrings.TypesAttachment.SONG)) {
                    final Collection<Audio> listAudio = (Collection<Audio>) entry.getValue();
                    final AudioHolder audioHolder = (AudioHolder) pViewHolder;
                    audioHolder.bind(listAudio);
                } else if (entry.getKey().equals(ConstantStrings.TypesAttachment.VIDEO)) {
                    final Collection<Video> videoCollections = (Collection<Video>) entry.getValue();
                    final VideoHolder videoHolder = (VideoHolder) pViewHolder;
                    videoHolder.bind(videoCollections);
                } else if (entry.getKey().equals(ConstantStrings.TypesAttachment.DOC)) {
                    final Collection<Doc> docCollection = (Collection<Doc>) entry.getValue();
                    final DocHolder docHolder = (DocHolder) pViewHolder;
                    docHolder.bind(docCollection);
                } else if (entry.getKey().equals(ConstantStrings.TypesAttachment.LINK)) {
                    final Collection<Link> linkCollection = (Collection<Link>) entry.getValue();
                    final LinkHolder linkHolder = (LinkHolder) pViewHolder;
                    linkHolder.bind(linkCollection);
                } else if (entry.getKey().equals(ConstantStrings.TypesAttachment.OTHER_DOC)) {
                    final Collection<Doc> docs = (Collection<Doc>) entry.getValue();
                    final OtherDocHolder otherDocHolder = (OtherDocHolder) pViewHolder;
                    otherDocHolder.bind(docs);
                }
            }
            countIterable++;
        }
    }

    @Override
    public int getItemCount() {
        return mAttachments.size();
    }

    @Override
    public int getItemViewType(final int position) {
        int countIterable = 0;
        final Set set = mAttachments.entrySet();

        for (final Object aSet : set) {

            if (position == countIterable) {
                final Map.Entry entry = (Map.Entry) aSet;
                if (entry.getKey().equals(ConstantStrings.TypesAttachment.PHOTO)) {
                    return 0;
                } else if (entry.getKey().equals(ConstantStrings.TypesAttachment.SONG)) {
                    return 1;
                } else if (entry.getKey().equals(ConstantStrings.TypesAttachment.VIDEO)) {
                    return 2;
                } else if (entry.getKey().equals(ConstantStrings.TypesAttachment.DOC)) {
                    return 3;
                } else if (entry.getKey().equals(ConstantStrings.TypesAttachment.LINK)) {
                    return 4;
                } else if (entry.getKey().equals(ConstantStrings.TypesAttachment.OTHER_DOC)) {
                    return 5;
                }
            }
            countIterable++;
        }
        throw new IllegalArgumentException("wrong item type");
    }

    class ImageHolder extends RecyclerView.ViewHolder {

        private final RecyclerView mGridRecycler;

        private final List<Photo> mPhotos = new ArrayList<Photo>();

        ImageHolder(final View pItemView) {
            super(pItemView);
            mGridRecycler = itemView.findViewById(R.id.recycler_with_content);

        }

        void bind(final Collection<Photo> pType) {
            if (!pType.isEmpty()) {
                mPhotos.clear();
                mPhotos.addAll(pType);

                final GridLayoutManager gridLayoutManager = new GridLayoutManager(mLayoutInflater.getContext(), 2);
                gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

                    @Override
                    public int getSpanSize(final int pPosition) {
                        if (mPhotos.size() == 1) {
                            return 2;
                        } else {
                            return 1;
                        }
                    }
                });

                final RecyclerViewMargin recyclerViewMargin = new RecyclerViewMargin(4);
                mGridRecycler.setLayoutManager(gridLayoutManager);
                mGridRecycler.setAdapter(new PictureAdapter(mLayoutInflater.getContext(), mPhotos));
                mGridRecycler.addItemDecoration(recyclerViewMargin);
            }
        }

    }

    class AudioHolder extends RecyclerView.ViewHolder {

        private final RecyclerView mSongs;

        private final List<Audio> mAudio = new ArrayList<Audio>();

        AudioHolder(final View pItemView) {
            super(pItemView);
            mSongs = itemView.findViewById(R.id.recycler_with_content);
        }

        void bind(final Collection<Audio> pContent) {
            mAudio.clear();
            mAudio.addAll(pContent);
            mSongs.setLayoutManager(new LinearLayoutManager(mLayoutInflater.getContext()));
            mSongs.setAdapter(new SongsAdapter(mLayoutInflater.getContext(), mAudio));
        }

    }

    class VideoHolder extends RecyclerView.ViewHolder {

        private final RecyclerView mVideosContainer;
        private final Collection<Video> videoList = new ArrayList<Video>();

        VideoHolder(@NonNull final View itemView) {
            super(itemView);
            mVideosContainer = itemView.findViewById(R.id.recycler_with_content);
        }

        void bind(final Collection<Video> pVideoCollection) {
            videoList.clear();
            videoList.addAll(pVideoCollection);

            final GridLayoutManager gridLayoutManager = new GridLayoutManager(mLayoutInflater.getContext(), 2);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(final int i) {
                    if (videoList.size() == 1) {
                        return 2;
                    } else {
                        return 1;
                    }
                }
            });
            final RecyclerViewMargin recyclerViewMargin = new RecyclerViewMargin(4);
            mVideosContainer.setLayoutManager(gridLayoutManager);
            mVideosContainer.setAdapter(new MovieAdapter(mLayoutInflater.getContext(), videoList));
            mVideosContainer.addItemDecoration(recyclerViewMargin);

        }
    }

    private class DocHolder extends RecyclerView.ViewHolder {
        private final List<Doc> mDocs;
        private final RecyclerView mRecyclerDoc;

        DocHolder(@NonNull final View itemView) {
            super(itemView);
            mDocs = new ArrayList<Doc>();
            mRecyclerDoc = itemView.findViewById(R.id.recycler_with_content);
        }

        void bind(final Collection<Doc> pDocCollection) {
            mDocs.clear();
            mDocs.addAll(pDocCollection);

            final GridLayoutManager gridLayoutManager = new GridLayoutManager(mLayoutInflater.getContext(), 2);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(final int i) {
                    if (mDocs.size() == 1) {
                        return 2;
                    } else {
                        return 1;
                    }
                }
            });
            final RecyclerViewMargin recyclerViewMargin = new RecyclerViewMargin(4);
            mRecyclerDoc.setLayoutManager(gridLayoutManager);
            mRecyclerDoc.addItemDecoration(recyclerViewMargin);
            mRecyclerDoc.setAdapter(new DocAdapter(mLayoutInflater.getContext(), mDocs));
        }
    }

    private class LinkHolder extends RecyclerView.ViewHolder {
        private final List<Link> mLinks;
        private final TextView mTextView;
        private final TextView mTitleText;
        private final ImageView mImageLink;

        LinkHolder(@NonNull final View itemView) {
            super(itemView);
            mLinks = new ArrayList<Link>();
            mTextView = itemView.findViewById(R.id.caption_link_text);
            mTitleText = itemView.findViewById(R.id.title_link_text);
            mImageLink = itemView.findViewById(R.id.link_image);
        }

        void bind(final Collection<Link> pLinkCollection) {
            mLinks.clear();
            mLinks.addAll(pLinkCollection);
            //single item for item post
            final Link link = mLinks.get(0);
            mTitleText.setText(link.getLinkTitle());
            mTextView.setText(link.getCaption());
            if (mAttachments.get(ConstantStrings.TypesAttachment.PHOTO) == null) {
                ViewUtils.setVisible(mImageLink);
                mImageLink.setAdjustViewBounds(true);
                ImageLoader.getInstance()
                        .with(mLayoutInflater.getContext())
                        .load(link.getPhoto().getPhoto604())
                        .into(mImageLink);
            } else {
                ViewUtils.setVisibilityGone(mImageLink);
            }
        }
    }

    class OtherDocHolder extends RecyclerView.ViewHolder {
        private final List<Doc> mDocList;
        private RecyclerView mOtherDocs;

        OtherDocHolder(@NonNull final View itemView) {
            super(itemView);
            mDocList = new ArrayList<Doc>();
        }

        void bind(final Collection<Doc> pDocs) {
            mDocList.clear();
            mDocList.addAll(pDocs);

            mOtherDocs = itemView.findViewById(R.id.recycler_with_content);
            mOtherDocs.setAdapter(new OtherDocAdapter(mLayoutInflater.getContext(), mDocList));
            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mLayoutInflater.getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mOtherDocs.setLayoutManager(linearLayoutManager);
        }
    }
}


