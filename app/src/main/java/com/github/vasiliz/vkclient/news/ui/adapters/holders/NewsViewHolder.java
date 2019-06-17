package com.github.vasiliz.vkclient.news.ui.adapters.holders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.vasiliz.myapplication.ExpandableTextView;
import com.github.vasiliz.myimageloader.ImageLoader;
import com.github.vasiliz.vkclient.R;
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
import com.github.vasiliz.vkclient.news.entity.Video;
import com.github.vasiliz.vkclient.news.ui.IMainView;
import com.github.vasiliz.vkclient.news.ui.adapters.AttachmentAdapter;
import com.github.vasiliz.vkclient.news.ui.listeners.OnClickListener;
import com.github.vasiliz.vkclient.news.ui.views.CircleImage;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class NewsViewHolder extends RecyclerView.ViewHolder {

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
    private final LinearLayout mLikeContainer;
    private Item mItem;
    private final ImageLoader mImageLoader = ImageLoader.getInstance();
    private final LayoutInflater mLayoutInflater;
    private final List<Profile> mProfiles = new ArrayList<Profile>();
    private final List<Groups> mGroups = new ArrayList<Groups>();
    private final OnClickListener mOnClickListener;


    public NewsViewHolder(@NonNull final View itemView, final LayoutInflater pLayoutInflater,
                          final List<Groups> pGroups, final List<Profile> pProfiles,
                          final OnClickListener pOnClickListener) {
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
        mLikeContainer = itemView.findViewById(R.id.like_container);
        mLayoutInflater = pLayoutInflater;
        mProfiles.addAll(pProfiles);
        mGroups.addAll(pGroups);
        mOnClickListener = pOnClickListener;
    }


    public void onBind(final Item pItem, final Context pContext, final IMainView pIMainView) {
        mItem = pItem;
        final Groups groups = getGroup(mItem);
        final Profile profile = getProfile(mItem);

        final View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mOnClickListener.onItemClick(mItem, mGroups, mProfiles);
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
        if (mItem.getText().isEmpty()) {
            ViewUtils.setVisibilityGone(mTextNews);
        } else {
            ViewUtils.setVisible(mTextNews);
            mTextNews.setContent(mItem.getText());
            mTextNews.setOnClickListener(onClickListener);
        }

        mCircleView.setImageResource(R.drawable.test_drawable);

        mDate.setText(StringUtils.getDateFromLong(mItem.getDate()));
        mLikeContainer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                if (mItem.getLikes().getCanLike() == 1 && mItem.getLikes().getUserLike() == 0) {
                    Item item = pItem;
                    item.getLikes().setUserLike(1);
                    item.getLikes().setCanLike(0);
                    pIMainView.clickLikeOnItem(item);
                    mLikeText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.read_heart, 0, 0, 0);
                }
            }
        });
        if (mItem.getLikes().getUserLike() == 1) {
            mLikeText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.read_heart, 0, 0, 0);
        } else {
            mLikeText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_24dp, 0, 0, 0);
        }
        mRepostText.setText(String.valueOf(mItem.getReposts().getCountReposts()));
        //footer item
        mLikeText.setText(String.valueOf(mItem.getLikes().getCountLike()));
        //attachments content
        if (mItem.getAttachments() != null) {
            ViewUtils.setVisible(mAttachmentsContainer);
            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mLayoutInflater.getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            final AttachmentAdapter attachmentAdapter = new AttachmentAdapter(mLayoutInflater.getContext());
            attachmentAdapter.setData(getAggregateData(mItem.getAttachments()));
            mAttachmentsContainer.setLayoutManager(linearLayoutManager);
            mAttachmentsContainer.setAdapter(attachmentAdapter);
        } else {
            ViewUtils.setVisibilityGone(mAttachmentsContainer);
        }

        if (mItem.getComments().getCanPost() == 1) {
            ViewUtils.setVisible(mCommentContainer);
            mCommentText.setText(String.valueOf(mItem.getComments().getCountComments()));
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
