package com.github.vasiliz.vkclient.base.utils;

public interface ConstantStrings {

    interface ApiVK {

        String BASE_URL = "https://oauth.vk.com/";
        String LOGIN_VK_REQUEST = "authorize?client_id=6745673&display=page&redirect_uri=https://oauth.vk.com/blank.html&scope=wall,video,friends,messages,photos,offline&response_type=token&v=5.68&state=123456";
        String CHECK_ACCESS_TOKEN = "access_token";
        String TOKEN_NAME = "vkToken";
        String API_VERSION = "5.69";
        String FILTER_FOR_REQUEST = "post";
        CharSequence ERROR = "error";
    }

    interface TypesAttachment {
        String PHOTO = "photo";
        String AUDIO = "audio";
        String SONG = "song";
        String VIDEO = "video";
        String DOC = "doc";
        String LINK = "link";
        String GIF = "gif";
        String OTHER_DOC = "other_doc";
    }

    interface Preferences {

        String ACCESS_TOKEN_PREFERENCE = "settings";
    }

    interface DB {

        String CREATE_TABLE = "Create table if not exists ";
        String CREATE_ID_COLUMN = " NOT NULL PRIMARY KEY";
        String COMMA = " ,";
        String END_OF_QUERY = ");";
        String OPEN_BRACKET = " (";

        interface Queryes {
            String SELECT_ATTACHMENTS =
                    " Select distinct " +
                            " Attachment.mTypeAttachments," +
                            " Photo.mId, mHeight, mPhoto1280, mPhoto130, mPhoto604, mPhoto807, mPostId, mText, mUserId, mWidth," +
                            " Audio.mArtist, Audio.mNameSong, Audio.mSongDuration, Audio.mSongUrl," +
                            " Video.mDurationVideo, Video.mSource, Video.mTitleVideo, Video.mUrlPhotoVideo," +
                            " Doc.mExt, Doc.mPreview, Doc.mSize, Doc.mTitle, Doc.mUrl," +
                            " Link.mCaption, Link.mDescription, Link.mLinkTitle, Link.mPhoto, Link.mUrlLink" +
                            " from " +
                            " Attachment " +
                            " left join Photo on Attachment.mPhoto = Photo.idPhoto " +
                            " left join Audio on Attachment.mAudio = Audio.idAudio " +
                            " left join Video on Attachment.mVideo = Video.idVideo" +
                            " left join Doc on Attachment.mDoc = Doc.idDoc" +
                            " left join Link on Attachment.mLink = Link.idLink where Attachment.idAttachment = %d";
        }

        interface Type {

            String VARCHAR = " varchar(255)";
            String INTEGER = " INTEGER";
            String TEXT = " TEXT";
            String LONG = " LONG";
        }

        interface ItemTable {
            String TYPE = "mType";
            String SOURCE_ID = "mSourseId";
            String DATE = "mDate";
            String POST_ID = "mPostId";
            String POST_TYPE = "mPostType";
            String TEXT = "mText";
            String ATTACHMENTS = "mAttachments";
            String COMMENTS = "mComments";
            String LIKES = "mLikes";
            String REPOSTS = "mReposts";
            String VIEWS = "mViews";
            String ADDED_AT = "mAddedAt";
        }

        interface AudioTable {
            String ID_AUDIO = "idAudio";
            String ARTIST = "mArtist";
            String NAME_SONG = "mNameSong";
            String SONG_DURATION = "mSongDuration";
            String URL_SONG = "mSongUrl";
        }

        interface AttathmentTable {
            String ID_ATTACHMENT = "idAttachment";
            String TYPE_ATTACHMENT = "mTypeAttachments";
            String AUDIO = "mAudio";
            String DOC = "mDoc";
            String LINK = "mLink";
            String PHOTO = "mPhoto";
            String VIDEO = "mVideo";
        }

        interface CommnetTable {
            String COMMENTS_ID = "mCommentsId";
            String COUNT_COMMENTS = "mCountComments";
            String CAN_POST = "mCanPost";
        }

        interface DocTable {
            String ID_DOC = "idDoc";
            String TITLE = "mTitle";
            String URL = "mUrl";
            String EXT = "mExt";
            String SIZE = "mSize";
            String PREVIEW = "mPreview";
        }

        interface GroupsTable {
            String ID_GROUP = "mId";
            String NAME_GROUP = "mNameGroup";
            String SCREEN_NAME = "mScreenName";
            String IS_CLOSED = "mIsClosed";
            String TYPE = "mType";
            String PHOTO_50 = "mUrlGroupPhoto50";
            String PHOTO_100 = "mUrlGroupPhoto100";
            String PHOTO_200 = "mUrlGroupPhoto200";

        }

        interface LikesTable {
            String ID_LIKE = "mIdLike";
            String COUNT_LIKE = "mCountLike";
            String USER_LIKE = "mUserLike";
            String CAN_LIKE = "mCanLike";
        }

        interface LinkTable {
            String ID_LINK = "idLink";
            String URL_LINK = "mUrlLink";
            String TITLE = "mLinkTitle";
            String DESCRIPTION = "mDescription";
            String PHOTO = "mPhoto";
            String CAPTION = "mCaption";
        }

        interface PhotoTable {
            String ID_PHOTO = "idPhoto";
            String PHOTO_604 = "mPhoto604";
            String PHOTO_807 = "mPhoto807";
            String PHOTO_1280 = "mPhoto1280";
            String PHOTO_130 = "mPhoto130";
            String ID = "mId";
            String USER_ID = "mUserId";
            String WIDTH = "mWidth";
            String HEIGTH = "mHeight";
            String TEXT = "mText";
            String POST_ID = "mPostId";
            String ACCESS_PHOTO_KEY = "mAccessPhotoKey";
        }

        interface PhotoPreviewTable {
            String ID = "idPhotoPreview";
            String SIZES_PHOTO_PREVIEW = "mSizesPhotoPreviews";
        }

        interface PreviewTable {
            String ID_PREVIEW = "idPreview";
            String PHOTO_PREVIEW = "mPhotoPreview";
            String VIDEO_PREVIEW = "mVideoDocPreview";
        }

        interface ProfileTable {
            String ID = "mId";
            String FIRST_NAME = "mFirstName";
            String LAST_NAME = "mLastName";
            String SEX = "mSex";
            String SCREEN_NAME = "mScreenName";
            String PHOTO_50 = "mUrlPhoto50";
            String PHOTO_100 = "mUrlPhoto100";
            String ONLINE = "mOnline";
        }

        interface RepostsTable {
            String ID = "mRepostId";
            String COUNT_REPOST = "mCountReposts";
            String USER_REPOST = "mUserReposted";
        }

        interface SizesPhotoPreviewTable {
            String ID = "idSizesPhotoPreview";
            String SOURCE = "mSourse";
            String TYPE = "mType";
        }

        interface VideoTable {
            String ID = "idVideo";
            String TITLE = "mTitleVideo";
            String SOURCE = "mSource";
            String URL_PHOTO = "mUrlPhotoVideo";
            String DURATION = "mDurationVideo";
        }

        interface VideoDocPreviewTable {
            String ID = "idVideoPreview";
            String SOURCE = "mSourse";
        }

        interface ViewsTable {
            String ID = "mIdViews";
            String COUNT = "mCountViews";
        }
    }

    interface AppConst {
        String SAVE_NEWS = "allNews";
        String BUNDLE_GROUPS = "groups";
        String PROFILES = "profiles";
        String ITEMS = "items";
    }

}
