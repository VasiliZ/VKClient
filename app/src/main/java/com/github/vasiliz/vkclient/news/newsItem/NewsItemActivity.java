package com.github.vasiliz.vkclient.news.newsItem;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.vasiliz.myapplication.ExpandableTextView;
import com.github.vasiliz.myimageloader.ImageLoader;
import com.github.vasiliz.vkclient.R;
import com.github.vasiliz.vkclient.base.utils.ConstantStrings;
import com.github.vasiliz.vkclient.base.utils.SetImageUtils;
import com.github.vasiliz.vkclient.base.utils.StringUtils;
import com.github.vasiliz.vkclient.base.utils.ViewUtils;
import com.github.vasiliz.vkclient.mymvp.VkActivity;
import com.github.vasiliz.vkclient.mymvp.VkPresenter;
import com.github.vasiliz.vkclient.news.entity.Attachment;
import com.github.vasiliz.vkclient.news.entity.Audio;
import com.github.vasiliz.vkclient.news.entity.Doc;
import com.github.vasiliz.vkclient.news.entity.Groups;
import com.github.vasiliz.vkclient.news.entity.Item;
import com.github.vasiliz.vkclient.news.entity.Link;
import com.github.vasiliz.vkclient.news.entity.Photo;
import com.github.vasiliz.vkclient.news.entity.Profile;
import com.github.vasiliz.vkclient.news.entity.Video;
import com.github.vasiliz.vkclient.news.ui.adapters.AttachmentAdapter;
import com.github.vasiliz.vkclient.news.ui.views.CircleImage;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class NewsItemActivity extends VkActivity {
    private CircleImage mCircleView;
    private ExpandableTextView mTextNews;
    private TextView mName;
    private TextView mDate;
    private TextView mLikeText;
    private TextView mCommentText;
    private TextView mRepostText;
    private LinearLayout mCommentContainer;
    private RecyclerView mAttachmentsContainer;
    private List<Groups> mGroups;
    private List<Profile> mProfiles;
    private Item mItem;


    private String TAG = NewsItemActivity.class.getSimpleName();

    @Override
    protected VkPresenter initPresenter() {
        return new NewsItemPresenterImpl();
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_item_news);
        final Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();
        mGroups = bundle.getParcelableArrayList("groups");
        mProfiles = bundle.getParcelableArrayList("profiles");
        mItem = bundle.getParcelable("item");
        init();
        setDataOnView();
    }

    private void init() {
        mCircleView = findViewById(R.id.avatar_image_view);
        mTextNews = findViewById(R.id.news_text_view);
        mName = findViewById(R.id.name_group_or_profile_text_view);
        mDate = findViewById(R.id.time_post_text_view);
        mLikeText = findViewById(R.id.like_text_view);
        mCommentText = findViewById(R.id.comment_text_view);
        mRepostText = findViewById(R.id.repost_text_view);
        mCommentContainer = findViewById(R.id.comment_container);
        mAttachmentsContainer = findViewById(R.id.attachments_container);

    }

    private void setDataOnView() {
        final Groups group = getGroup(mItem);
        final Profile profile = getProfile(mItem);

        if (group != null) {
            ImageLoader.getInstance()
                    .with(this)
                    .load(SetImageUtils.chechAvatarGroup(group))
                    .into(mCircleView);
            mName.setText(group.getNameGroup());
        } else if (profile != null) {

            ImageLoader.getInstance()
                    .with(this)
                    .load(SetImageUtils.checkAvatarProfile(profile))
                    .into(mCircleView);
            mName.setText(String
                    .format(profile.getFirstName() + "%s" + profile.getLastName(), " "));
        }
        if (mItem.getText().isEmpty()) {
            ViewUtils.setVisibilityGone(mTextNews);
        } else {
            ViewUtils.setVisible(mTextNews);
            mTextNews.setContent(mItem.getText());
        }

        mDate.setText(StringUtils.getDateFromLong(mItem.getDate()));

        mRepostText.setText(String.valueOf(mItem.getReposts().getCountReposts()));
        //footer item
        mLikeText.setText(String.valueOf(mItem.getLikes().getCountLike()));
        //attachments content
        if (mItem.getAttachments() != null) {
            ViewUtils.setVisible(mAttachmentsContainer);
            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            final AttachmentAdapter attachmentAdapter = new AttachmentAdapter(this);
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

