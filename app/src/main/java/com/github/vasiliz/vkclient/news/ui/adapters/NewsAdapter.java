package com.github.vasiliz.vkclient.news.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.vasiliz.myapplication.ExpandableTextView;
import com.github.vasiliz.myimageloader.ImageLoader;
import com.github.vasiliz.vkclient.R;
import com.github.vasiliz.vkclient.VkApplication;
import com.github.vasiliz.vkclient.base.utils.ConstantStrings;
import com.github.vasiliz.vkclient.base.utils.StringUtils;
import com.github.vasiliz.vkclient.base.utils.ViewUtils;
import com.github.vasiliz.vkclient.news.entity.Attachment;
import com.github.vasiliz.vkclient.news.entity.Audio;
import com.github.vasiliz.vkclient.news.entity.Doc;
import com.github.vasiliz.vkclient.news.entity.Groups;
import com.github.vasiliz.vkclient.news.entity.Item;
import com.github.vasiliz.vkclient.news.entity.Link;
import com.github.vasiliz.vkclient.news.entity.Photo;
import com.github.vasiliz.vkclient.news.entity.Profile;
import com.github.vasiliz.vkclient.news.entity.Response;
import com.github.vasiliz.vkclient.news.entity.Video;
import com.github.vasiliz.vkclient.news.ui.listeners.OnClickListener;
import com.github.vasiliz.vkclient.news.ui.views.CircleImage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private final LayoutInflater mLayoutInflater;
    private final List<Item> mItems;
    private final List<Groups> mGroups;
    private final List<Profile> mProfiles;
    private final ImageLoader mImageLoader;
    private final OnClickListener mOnClickListener;

    public NewsAdapter(final Context pContext, final OnClickListener pOnClickListener) {
        mLayoutInflater = LayoutInflater.from(pContext);
        mItems = new ArrayList<Item>();
        mGroups = new ArrayList<Groups>();
        mProfiles = new ArrayList<Profile>();
        mImageLoader = VkApplication.getmImageLoader();
        mOnClickListener = pOnClickListener;
    }

    @NonNull
    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(@NonNull final ViewGroup pViewGroup, final int pI) {
        final View view = mLayoutInflater.inflate(R.layout.item_news, pViewGroup, false);
        return new NewsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final NewsAdapter.NewsViewHolder pViewHolder, final int pPosition) {
        pViewHolder.onBind(pPosition);
    }

    @Override
    public int getItemCount() {
        if (mItems != null) {
            return mItems.size();
        }
        return 0;
    }

    public void clear() {
        mItems.clear();
        mProfiles.clear();
        mGroups.clear();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {

        private final CircleImage mCircleView;
        private final ExpandableTextView mTextNews;
        private final TextView mName;
        private final TextView mDate;
        private final TextView mLikeText;
        private final TextView mCommentText;
        private final TextView mRepostText;
        private final LinearLayout mCommentContainer;
        private final RecyclerView mAttachmentsContainer;
        private final View mView;
        private final LinearLayout mHeaderNews;


        NewsViewHolder(@NonNull final View itemView) {
            super(itemView);
            mView = itemView;
            mCircleView = itemView.findViewById(R.id.avatar_image_view);
            mTextNews = itemView.findViewById(R.id.news_text_view);
            mName = itemView.findViewById(R.id.name_group_or_profile_text_view);
            mDate = itemView.findViewById(R.id.time_post_text_view);
            mLikeText = itemView.findViewById(R.id.like_text_view);
            mCommentText = itemView.findViewById(R.id.comment_text_view);
            mRepostText = itemView.findViewById(R.id.repost_text_view);
            mCommentContainer = itemView.findViewById(R.id.comment_container);
            mAttachmentsContainer = itemView.findViewById(R.id.attachments_container);
            mHeaderNews = itemView.findViewById(R.id.header_item_layout);
        }


        void onBind(final int pPosition) {
            final Item item = mItems.get(pPosition);
            final Groups groups = getGroup(item);
            final Profile profile = getProfile(item);

            final View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    mOnClickListener.onItemClick(item, mGroups, mProfiles);
                }
            };

            mHeaderNews.setOnClickListener(onClickListener);

            if (groups != null) {
                mImageLoader
                        .with(mLayoutInflater.getContext())
                        .load(groups.getUrlGroupPhoto100())
                        .into(mCircleView);
                mName.setText(groups.getNameGroup());
            } else if (profile != null) {

                mImageLoader
                        .with(mLayoutInflater.getContext())
                        .load(profile.getUrlPhoto100())
                        .into(mCircleView);
                mName.setText(String
                        .format(profile.getFirstName() + "%s" + profile.getLastName(), " "));
            }
            if (item.getText().isEmpty()) {
                ViewUtils.setVisibilityGone(mTextNews);
            } else {
                ViewUtils.setVisible(mTextNews);
                mTextNews.setContent(item.getText());
                mTextNews.setOnClickListener(onClickListener);
            }

            mCircleView.setImageResource(R.drawable.test_drawable);

            mDate.setText(StringUtils.getDateFromLong(item.getDate()));

            mRepostText.setText(String.valueOf(item.getReposts().getCountReposts()));
            //footer item
            mLikeText.setText(String.valueOf(item.getLikes().getCountLike()));
            //attachments content
            if (item.getAttachments() != null) {
                ViewUtils.setVisible(mAttachmentsContainer);
                final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mLayoutInflater.getContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                final AttachmentAdapter attachmentAdapter = new AttachmentAdapter(mLayoutInflater.getContext());
                attachmentAdapter.setData(getAggregateData(item.getAttachments()));
                mAttachmentsContainer.setLayoutManager(linearLayoutManager);
                mAttachmentsContainer.setAdapter(attachmentAdapter);
            } else {
                ViewUtils.setVisibilityGone(mAttachmentsContainer);
            }

            if (item.getComments().getCanPost() == 1) {
                ViewUtils.setVisible(mCommentContainer);
                mCommentText.setText(String.valueOf(item.getComments().getCountComments()));
            } else {
                ViewUtils.setVisibilityGone(mCommentContainer);
            }


        }

        private Map<String, List> getAggregateData(final Iterable<Attachment> pAttachments) {

            //copy history type
            final Map<String, List> fullContentList = new LinkedHashMap<String, List>();
            final List<Photo> photos = new ArrayList<Photo>();
            final List<Audio> audioList = new ArrayList<Audio>();
            final List<Video> videoList = new ArrayList<Video>();
            final List<Doc> docs = new ArrayList<Doc>();
            final List<Doc> otherDocs = new ArrayList<Doc>();
            final List<Link> links = new ArrayList<Link>();
            for (final Attachment attachment : pAttachments) {
                if (attachment
                        .getTypeAttachments()
                        .equals(ConstantStrings.TypesAttachment.PHOTO)) {
                    photos.add(attachment.getPhoto());
                } else if (attachment
                        .getTypeAttachments()
                        .equals(ConstantStrings.TypesAttachment.AUDIO)) {
                    audioList.add(attachment.getAudio());
                } else if (attachment
                        .getTypeAttachments()
                        .equals(ConstantStrings.TypesAttachment.VIDEO)) {
                    videoList.add(attachment.getVideo());
                } else if ((attachment
                        .getTypeAttachments()
                        .equals(ConstantStrings.TypesAttachment.DOC))
                        && (attachment.getDoc()
                        .getExt()
                        .equals(ConstantStrings.TypesAttachment.GIF))) {
                    docs.add(attachment.getDoc());
                } else if (attachment
                        .getTypeAttachments()
                        .equals(ConstantStrings.TypesAttachment.LINK)) {
                    links.add(attachment.getLink());
                } else if ((attachment.getTypeAttachments().equals(ConstantStrings.TypesAttachment.DOC))
                        && !(attachment.getDoc().getExt().equals(ConstantStrings.TypesAttachment.GIF))) {
                    otherDocs.add(attachment.getDoc());
                }
            }
            if (!photos.isEmpty()) {
                fullContentList.put(ConstantStrings.TypesAttachment.PHOTO, photos);
            }
            if (!audioList.isEmpty()) {
                fullContentList.put(ConstantStrings.TypesAttachment.SONG, audioList);
            }
            if (!videoList.isEmpty()) {
                fullContentList.put(ConstantStrings.TypesAttachment.VIDEO, videoList);
            }
            if (!docs.isEmpty()) {
                fullContentList.put(ConstantStrings.TypesAttachment.DOC, docs);
            }
            if (!links.isEmpty()) {
                fullContentList.put(ConstantStrings.TypesAttachment.LINK, links);
            }

            if (!otherDocs.isEmpty()) {
                fullContentList.put(ConstantStrings.TypesAttachment.OTHER_DOC, otherDocs);
            }

            return fullContentList;
        }


        private Profile getProfile(final Item pItem) {
            for (final Profile profile : mProfiles) {
                if (pItem.getSourseId() == profile.getId()) {
                    return profile;
                }
            }
            return null;
        }

        private Groups getGroup(final Item pItem) {
            for (final Groups groups : mGroups) {
                if (pItem.getSourseId() == groups.getId() * -1) {
                    return groups;
                }
            }
            return null;
        }

    }

    public void setItems(final Response pResponseNews) {
        final Collection<Item> items = pResponseNews.getResponseNews().getItemList();
        final List<Groups> groups = pResponseNews.getResponseNews().getGroupsList();
        final List<Profile> profiles = pResponseNews.getResponseNews().getProfileList();

        for (final Item item : items) {
            add(item);
        }

        mGroups.addAll(groups);
        mProfiles.addAll(profiles);
        notifyDataSetChanged();
    }

    private void add(final Item pItem) {
        mItems.add(pItem);
        notifyItemInserted(mItems.size() - 1);
    }


}
