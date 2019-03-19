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

import com.github.vasiliz.myimageloader.ImageLoader;
import com.github.vasiliz.vkclient.R;
import com.github.vasiliz.vkclient.VkApplication;
import com.github.vasiliz.vkclient.base.utils.StringUtils;
import com.github.vasiliz.vkclient.news.entity.Attachment;
import com.github.vasiliz.vkclient.news.entity.Audio;
import com.github.vasiliz.vkclient.news.entity.Doc;
import com.github.vasiliz.vkclient.news.entity.Groups;
import com.github.vasiliz.vkclient.news.entity.Item;
import com.github.vasiliz.vkclient.news.entity.Photo;
import com.github.vasiliz.vkclient.news.entity.Profile;
import com.github.vasiliz.vkclient.news.entity.Response;
import com.github.vasiliz.vkclient.news.entity.Video;
import com.github.vasiliz.vkclient.news.ui.views.CircleImage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private final LayoutInflater mLayoutInflater;
    private final List<Item> mItems;
    private final List<Groups> mGroups;
    private final List<Profile> mProfiles;
    private final ImageLoader mImageLoader;
    private AttachmentImageAdapter mAttachmentImageAdapter;

    public NewsAdapter(final Context pContext) {
        mLayoutInflater = LayoutInflater.from(pContext);
        mItems = new ArrayList<>();
        mGroups = new ArrayList<>();
        mProfiles = new ArrayList<>();
        mImageLoader = VkApplication.getmImageLoader();

    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull final ViewGroup pViewGroup, final int pI) {
        final View view = mLayoutInflater.inflate(R.layout.item_news, pViewGroup, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsViewHolder pViewHolder, final int pPosition) {
        final Item item = mItems.get(pPosition);
        final Groups groups = getGroup(item);
        final Profile profile = getProfile(item);

        pViewHolder.mTextNews.setText(item.getText());
        if (groups != null) {
            mImageLoader
                    .with(mLayoutInflater.getContext())
                    .load(groups.getUrlGroupPhoto50())
                    .into(pViewHolder.mCircleView);
            pViewHolder.mName.setText(groups.getNameGroup());
        } else {
            mImageLoader
                    .with(mLayoutInflater.getContext())
                    .load(profile.getUrlPhoto50())
                    .into(pViewHolder.mCircleView);
            pViewHolder.mName.setText(String
                    .format(profile.getFirstName() + "%s" + profile.getLastName(), " "));
        }

        pViewHolder.mCircleView.setImageResource(R.drawable.test_drawable);

        pViewHolder.mDate.setText(StringUtils.getDateFromLong(item.getDate()));

        pViewHolder.mRepostText.setText(String.valueOf(item.getReposts().getCountReposts()));
        //footer item

        pViewHolder.mLikeText.setText(String.valueOf(item.getLikes().getCountLike()));

        //attachments content

        if (item.getAttachments() == null) {
            pViewHolder.mImageContainer.setVisibility(View.GONE);
            pViewHolder.mAudioContainer.setVisibility(View.GONE);
            pViewHolder.mVideoCOntainer.setVisibility(View.GONE);
            pViewHolder.mDocContainer.setVisibility(View.GONE);
        } else {
            final List<Photo> photos = new ArrayList<>();
            final List<Audio> audioList = new ArrayList<>();
            final List<Video> videos = new ArrayList<>();
            final List<Doc> docs = new ArrayList<>();

            for (final Attachment attachment : item.getAttachments()) {
                switch (attachment.getTypeAttachments()) {
                    case "photo":
                        photos.add(attachment.getPhoto());
                        break;
                    case "audio":
                        audioList.add(attachment.getAudio());
                        break;
                    case "video":
                        videos.add(attachment.getVideo());
                        break;
                    case "doc":
                        docs.add(attachment.getDoc());
                        break;
                }
            }
            if (!photos.isEmpty()) {
                pViewHolder.mImageContainer.setVisibility(View.VISIBLE);
                mAttachmentImageAdapter =
                        new AttachmentImageAdapter(mLayoutInflater.getContext(), photos);
                final LinearLayoutManager linearLayoutManager =
                        new LinearLayoutManager(mLayoutInflater.getContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                pViewHolder.mImageContainer
                        .setLayoutManager(linearLayoutManager);
                pViewHolder.mImageContainer.getRecycledViewPool().clear();
                pViewHolder.mImageContainer.setAdapter(mAttachmentImageAdapter);
            } else {
                pViewHolder.mImageContainer.setVisibility(View.GONE);
            }

            if (!audioList.isEmpty()) {
                pViewHolder.mAudioContainer.setVisibility(View.VISIBLE);
                final AttachmentAudioAdapter attachmentAudioAdapter =
                        new AttachmentAudioAdapter(mLayoutInflater.getContext(), audioList);
                final LinearLayoutManager linearLayoutManager =
                        new LinearLayoutManager(mLayoutInflater.getContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                pViewHolder.mAudioContainer.setLayoutManager(linearLayoutManager);
                pViewHolder.mAudioContainer.getRecycledViewPool().clear();
                pViewHolder.mAudioContainer.setAdapter(attachmentAudioAdapter);
            } else {
                pViewHolder.mAudioContainer.setVisibility(View.GONE);
            }

            if (!videos.isEmpty()) {
                pViewHolder.mVideoCOntainer.setVisibility(View.VISIBLE);
                final AttachmentVideoAdapter attachmentVideoAdapter =
                        new AttachmentVideoAdapter(mLayoutInflater.getContext(), videos);
                final LinearLayoutManager linearLayoutManager =
                        new LinearLayoutManager(mLayoutInflater.getContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

                pViewHolder.mVideoCOntainer.setLayoutManager(linearLayoutManager);
                pViewHolder.mVideoCOntainer.setAdapter(attachmentVideoAdapter);
            } else {
                pViewHolder.mVideoCOntainer.setVisibility(View.GONE);
            }

            if (!docs.isEmpty()) {
                pViewHolder.mDocContainer.setVisibility(View.VISIBLE);
                final AttachmentDocAdapter attachmentDocAdapter =
                        new AttachmentDocAdapter(mLayoutInflater.getContext(), docs);
                final LinearLayoutManager linearLayoutManager =
                        new LinearLayoutManager(mLayoutInflater.getContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

                pViewHolder.mDocContainer.setLayoutManager(linearLayoutManager);
                pViewHolder.mDocContainer.setAdapter(attachmentDocAdapter);
            } else {
                pViewHolder.mDocContainer.setVisibility(View.GONE);
            }
        }

        if (item.getComments().getCanPost() == 1) {
            pViewHolder.mCommentContainer.setVisibility(View.VISIBLE);
            pViewHolder.mCommentText.setText(String.valueOf(item.getComments().getCountComments()));
        } else {
            pViewHolder.mCommentContainer.setVisibility(View.GONE);
        }

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

    @Override
    public int getItemCount() {
        if (mItems != null) {
            return mItems.size();
        }
        return 0;
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {

        private final CircleImage mCircleView;
        private final TextView mTextNews;
        private final TextView mName;
        private final TextView mDate;
        private final TextView mLikeText;
        private final TextView mCommentText;
        private final TextView mRepostText;

        private final LinearLayout mCommentContainer;

        private final RecyclerView mImageContainer;
        private final RecyclerView mVideoCOntainer;
        private final RecyclerView mAudioContainer;
        private final RecyclerView mDocContainer;

        public NewsViewHolder(@NonNull final View itemView) {
            super(itemView);
            mCircleView = itemView.findViewById(R.id.avatar_image_view);
            mTextNews = itemView.findViewById(R.id.news_text_view);
            mName = itemView.findViewById(R.id.name_group_or_profile_text_view);
            mDate = itemView.findViewById(R.id.time_post_text_view);
            mLikeText = itemView.findViewById(R.id.like_text_view);
            mCommentText = itemView.findViewById(R.id.comment_text_view);
            mRepostText = itemView.findViewById(R.id.repost_text_view);
            mImageContainer = itemView.findViewById(R.id.image_container);
            mCommentContainer = itemView.findViewById(R.id.comment_container);
            mVideoCOntainer = itemView.findViewById(R.id.video_container);
            mAudioContainer = itemView.findViewById(R.id.audio_container);
            mDocContainer = itemView.findViewById(R.id.doc_container);

        }
    }

    public void setItems(final Response pResponseNews) {
        final Collection<Item> items = pResponseNews.getResponseNews().getItemList();
        final List<Groups> groups = pResponseNews.getResponseNews().getGroupsList();
        final List<Profile> profiles = pResponseNews.getResponseNews().getProfileList();

        mItems.addAll(items);
        mGroups.addAll(groups);
        mProfiles.addAll(profiles);

        items.clear();
        groups.clear();
        profiles.clear();

        notifyDataSetChanged();
    }

}
