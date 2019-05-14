package com.github.vasiliz.vkclient.base.db.config;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.github.vasiliz.vkclient.VkApplication;
import com.github.vasiliz.vkclient.base.utils.ConstantStrings;
import com.github.vasiliz.vkclient.base.utils.CursorUtils;
import com.github.vasiliz.vkclient.news.entity.Attachment;
import com.github.vasiliz.vkclient.news.entity.Audio;
import com.github.vasiliz.vkclient.news.entity.Comments;
import com.github.vasiliz.vkclient.news.entity.Doc;
import com.github.vasiliz.vkclient.news.entity.Groups;
import com.github.vasiliz.vkclient.news.entity.Item;
import com.github.vasiliz.vkclient.news.entity.Likes;
import com.github.vasiliz.vkclient.news.entity.Link;
import com.github.vasiliz.vkclient.news.entity.Photo;
import com.github.vasiliz.vkclient.news.entity.PhotoPreview;
import com.github.vasiliz.vkclient.news.entity.Preview;
import com.github.vasiliz.vkclient.news.entity.Profile;
import com.github.vasiliz.vkclient.news.entity.Reposts;
import com.github.vasiliz.vkclient.news.entity.Response;
import com.github.vasiliz.vkclient.news.entity.ResponseNews;
import com.github.vasiliz.vkclient.news.entity.SizesPhotoPreview;
import com.github.vasiliz.vkclient.news.entity.Video;
import com.github.vasiliz.vkclient.news.entity.VideoDocPreview;
import com.github.vasiliz.vkclient.news.entity.Views;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Database(databaseName = "mydbVk",
        entity = {
                Profile.class,
                Item.class,
                Groups.class,
                Attachment.class,
                Likes.class,
                Reposts.class,
                Comments.class,
                Views.class,
                Audio.class,
                Video.class,
                Photo.class,
                Link.class,
                Doc.class,
                Preview.class,
                PhotoPreview.class,
                VideoDocPreview.class,
                SizesPhotoPreview.class})
public final class AppDB {

    private static AppDB INSTANCE;
    private final DBHelper mDBHelper;
    private final String TAG = AppDB.class.getSimpleName();
    private final String SELECT_FROM = "Select * from ";
    private final String LIMIT = " LIMIT ";
    private final String SELECT_DISTINCT = "Select distinct ";
    private final String DOT = ".";
    private final String COMMA = ", ";
    private final String SPACE = " ";
    private final String FROM = " from ";
    private final String INNER_JOIN = " inner join ";
    private final String ON = " on ";
    private final String OPEN_BRACKET = "(";
    private final String CLOSE_BRACKET = ")";
    private final String EQUALLY = " = ";
    private final String ORDER_BY = " order by ";
    private final String DESC = " desc";
    private final String END_QUERY = " ;";
    private final String LEFT_JOIN = " left join ";
    private final String PHOTO = " photo";
    private String WHERE = " Where ";
    private int mItemslinit = 0;

    private AppDB() {
        mDBHelper = VkApplication.getDBHelper();
        new Runnable() {

            @Override
            public synchronized void run() {
                mDBHelper.getWritableDatabase();
            }
        };

    }

    public static synchronized AppDB getAppDBInstance() {
        if (INSTANCE == null) {
            return INSTANCE = new AppDB();
        }
        return INSTANCE;
    }

    public void writeData(final Response pResponse) {
        final SQLiteDatabase database = mDBHelper.getWritableDatabase();
        try {
            database.beginTransaction();
            final List<Groups> groups = pResponse.getResponseNews().getGroupsList();
            final List<Profile> profiles = pResponse.getResponseNews().getProfileList();
            final List<Item> items = pResponse.getResponseNews().getItemList();
            insertGroups(groups, database);
            insertProfiles(profiles, database);
            insertItems(items, database);
            database.setTransactionSuccessful();
        } catch (final Exception e) {
            e.fillInStackTrace();
            //   Log.d(TAG, "Error while trying read data to db");
        } finally {
            database.endTransaction();
            database.close();
        }

    }

    private void insertGroups(final List<Groups> pGroups, final SQLiteDatabase pSQLiteDatabase) {
        try {
            for (int i = 0; i < pGroups.size(); i++) {

                final Groups group = pGroups.get(i);
                pSQLiteDatabase
                        .insertWithOnConflict(
                                Groups.class.getSimpleName(),
                                null,
                                group.getContentValues(),
                                SQLiteDatabase.CONFLICT_REPLACE);
            }
        } catch (final Exception pE) {
            pE.fillInStackTrace();
        }

    }

    private void insertProfiles(final List<Profile> pProfiles, final SQLiteDatabase pSQLiteDatabase) {
        for (int i = 0; i < pProfiles.size(); i++) {
            final Profile profile = pProfiles.get(i);
            pSQLiteDatabase
                    .insertWithOnConflict(
                            Profile.class.getSimpleName(),
                            null,
                            profile.getContentValues(),
                            SQLiteDatabase.CONFLICT_REPLACE);

        }
    }

    private void insertItems(final List<Item> pItems, final SQLiteDatabase pSQLiteDatabase) {
        for (int i = 0; i < pItems.size(); i++) {
            final Item item = pItems.get(i);
            pSQLiteDatabase
                    .insertWithOnConflict(
                            Item.class.getSimpleName(),
                            null,
                            item.getContentValues(),
                            SQLiteDatabase.CONFLICT_REPLACE);

            insertAttachment(item, pSQLiteDatabase);
            insertLike(item, pSQLiteDatabase);
            insertComments(item, pSQLiteDatabase);
            insertReposts(item, pSQLiteDatabase);
            insertViews(item, pSQLiteDatabase);
        }
    }

    private void insertViews(final Item pItem, final SQLiteDatabase pSQLiteDatabase) {
        pSQLiteDatabase
                .insertWithOnConflict(
                        Views.class.getSimpleName(),
                        null,
                        pItem.getViews()
                                .getContentValues(pItem.getPostId()),
                        SQLiteDatabase.CONFLICT_REPLACE);
    }

    private void insertReposts(final Item pItem, final SQLiteDatabase pSQLiteDatabase) {
        pSQLiteDatabase
                .insertWithOnConflict(
                        Reposts.class.getSimpleName(),
                        null,
                        pItem.getReposts()
                                .getContentValues(pItem.getPostId()), SQLiteDatabase.CONFLICT_REPLACE);
    }

    private void insertComments(final Item pItem, final SQLiteDatabase pSQLiteDatabase) {
        pSQLiteDatabase
                .insertWithOnConflict(
                        Comments.class.getSimpleName(),
                        null,
                        pItem.getComments()
                                .getContentValue(pItem.getPostId()), SQLiteDatabase.CONFLICT_REPLACE);
    }

    private void insertLike(final Item pItem, final SQLiteDatabase pSQLiteDatabase) {
        pSQLiteDatabase
                .insertWithOnConflict(
                        Likes.class.getSimpleName(),
                        null,
                        pItem.getLikes()
                                .getContentValues(pItem.getPostId()), SQLiteDatabase.CONFLICT_REPLACE);
    }

    private void insertAttachment(final Item pItem, final SQLiteDatabase pSQLiteDatabase) {
        try {
            if (pItem.getAttachments() != null) {
                for (int i = 0; i < pItem.getAttachments().size(); i++) {
                    final Attachment attachment = pItem.getAttachments().get(i);
                    pSQLiteDatabase
                            .insertWithOnConflict(
                                    Attachment.class.getSimpleName(),
                                    null,
                                    attachment.getContentValues(pItem.getPostId()),
                                    SQLiteDatabase.CONFLICT_REPLACE);
                    if (attachment.getAudio() != null) {
                        insertAudio(attachment, pSQLiteDatabase);
                    } else if (attachment.getVideo() != null) {
                        insertVideo(attachment, pSQLiteDatabase);
                    } else if (attachment.getPhoto() != null) {
                        insertPhoto(attachment, pSQLiteDatabase);
                    } else if (attachment.getLink() != null) {
                        insertLink(attachment, pSQLiteDatabase);
                    } else {
                        insertDoc(attachment, pSQLiteDatabase);
                    }
                }
            }
        } catch (final Exception pE) {
            pE.fillInStackTrace();
        }
    }

    private void insertDoc(final Attachment pAttachment, final SQLiteDatabase pSQLiteDatabase) {
        pSQLiteDatabase
                .insertWithOnConflict(Doc.class.getSimpleName(),
                        null,
                        pAttachment
                                .getDoc()
                                .getContentValues(pAttachment
                                        .hashCode()),
                        SQLiteDatabase.CONFLICT_REPLACE);
        insertPreview(pAttachment.getDoc(), pSQLiteDatabase);
    }

    private void insertPreview(final Doc pDoc, final SQLiteDatabase pSQLiteDatabase) {
        pSQLiteDatabase
                .insertWithOnConflict(Preview.class.getSimpleName(),
                        null,
                        pDoc.getPreview()
                                .getContentValues(pDoc.hashCode()),
                        SQLiteDatabase.CONFLICT_REPLACE);
        insertPhotoPreview(pDoc.getPreview(), pSQLiteDatabase);
        insertVideoPreview(pDoc.getPreview(), pSQLiteDatabase);
    }

    private void insertVideoPreview(final Preview pPreview, final SQLiteDatabase pSQLiteDatabase) {
        pSQLiteDatabase
                .insertWithOnConflict(VideoDocPreview.class.getSimpleName(),
                        null,
                        pPreview.getVideoDocPreview()
                                .getContentValues(pPreview.hashCode()),
                        SQLiteDatabase.CONFLICT_REPLACE);
    }

    private void insertPhotoPreview(final Preview pPreview, final SQLiteDatabase pSQLiteDatabase) {
        pSQLiteDatabase
                .insertWithOnConflict(PhotoPreview.class.getSimpleName(),
                        null,
                        pPreview.getPhotoPreview()
                                .getContentValues(pPreview
                                        .hashCode()),
                        SQLiteDatabase.CONFLICT_REPLACE);

        insertSizesPhotoPreview(pPreview.getPhotoPreview(), pSQLiteDatabase);
    }

    private void insertSizesPhotoPreview(final PhotoPreview pPhotoPreview, final SQLiteDatabase pSQLiteDatabase) {
        for (int i = 0; i < pPhotoPreview.getSizesPhotoPreviews().size(); i++) {
            pSQLiteDatabase
                    .insertWithOnConflict(SizesPhotoPreview.class.getSimpleName(),
                            null,
                            pPhotoPreview.getSizesPhotoPreviews()
                                    .get(i)
                                    .getContentValues(pPhotoPreview.hashCode()),
                            SQLiteDatabase.CONFLICT_REPLACE);
        }
    }

    private void insertLink(final Attachment pAttachment, final SQLiteDatabase pSQLiteDatabase) {
        pSQLiteDatabase
                .insertWithOnConflict(Link.class.getSimpleName(),
                        null,
                        pAttachment.getLink()
                                .getContentValues(pAttachment
                                        .hashCode()),
                        SQLiteDatabase.CONFLICT_REPLACE);
        if (pAttachment.getLink().getPhoto() != null) {
            insertLinkPhoto(pAttachment.getLink(), pSQLiteDatabase);
        }
    }

    private void insertLinkPhoto(final Link pLink, final SQLiteDatabase pSQLiteDatabase) {
        pSQLiteDatabase
                .insertWithOnConflict(Photo.class.getSimpleName(),
                        null,
                        pLink.getPhoto()
                                .getContentValues(pLink
                                        .hashCode()),
                        SQLiteDatabase.CONFLICT_REPLACE);
    }

    private void insertPhoto(final Attachment pAttachment, final SQLiteDatabase pSQLiteDatabase) {
        pSQLiteDatabase
                .insertWithOnConflict(Photo.class.getSimpleName(),
                        null,
                        pAttachment
                                .getPhoto()
                                .getContentValues(pAttachment
                                        .hashCode()),
                        SQLiteDatabase.CONFLICT_REPLACE);
    }

    private void insertVideo(final Attachment pAttachment, final SQLiteDatabase pSQLiteDatabase) {
        pSQLiteDatabase
                .insertWithOnConflict(Video.class.getSimpleName(),
                        null,
                        pAttachment
                                .getVideo()
                                .getContentValues(pAttachment
                                        .hashCode()),
                        SQLiteDatabase.CONFLICT_REPLACE);
    }

    private void insertAudio(final Attachment pAttachment, final SQLiteDatabase pSQLiteDatabase) {
        pSQLiteDatabase
                .insertWithOnConflict(Audio.class.getSimpleName(),
                        null,
                        pAttachment
                                .getAudio()
                                .getContentValues(pAttachment
                                        .hashCode()),
                        SQLiteDatabase.CONFLICT_REPLACE);
    }

    public Response getSaveData() {
        final Response response = new Response();
        final ResponseNews responseNews = new ResponseNews();
        responseNews.setGroupsList(getAllGroups());
        responseNews.setProfileList(getAllProfiles());
        responseNews.setItemList(getAllItems());
        response.setResponseNews(responseNews);
        return response;
    }

    private Cursor getCursorForSelectAllData(final Class pClass, final int pLimit) {
        final StringBuilder selectBuilder = new StringBuilder();
        selectBuilder
                .append(SELECT_FROM)
                .append(pClass.getSimpleName());
        final SQLiteDatabase database = mDBHelper.getReadableDatabase();
        if (pLimit != 0) {
            selectBuilder.append(" order by id desc ");
            selectBuilder.append(LIMIT).append(pLimit);
        }

        return database.rawQuery(selectBuilder.toString(), null);
    }

    private List<Groups> getAllGroups() {

        final List<Groups> groups = new ArrayList<Groups>();
        final Cursor cursor = getCursorForSelectAllData(Groups.class, 0);
        try {
            if (cursor.moveToFirst()) {
                do {
                    final Groups group = new Groups();
                    group.setId(cursor.getInt(0));
                    group.setIsClosed(cursor.getInt(1));
                    group.setNameGroup(cursor.getString(2));
                    group.setScreenName(cursor.getString(3));
                    group.setType(cursor.getString(4));
                    group.setUrlGroupPhoto100(cursor.getString(5));
                    group.setUrlGroupPhoto200(cursor.getString(6));
                    group.setUrlGroupPhoto50(cursor.getString(7));
                    groups.add(group);
                } while (cursor.moveToNext());
            }
            return groups;
        } finally {
            CursorUtils.closeCursor(cursor);
        }
    }

    private List<Profile> getAllProfiles() {
        final Cursor cursor = getCursorForSelectAllData(Profile.class, 0);
        final List<Profile> profiles = new ArrayList<Profile>();
        try {
            if (cursor.moveToFirst()) {
                do {
                    final Profile profile = new Profile();
                    profile.setFirstName(cursor.getString(0));
                    profile.setId(cursor.getInt(1));
                    profile.setLastName(cursor.getString(2));
                    profile.setOnline(cursor.getInt(3));
                    profile.setScreenName(cursor.getString(4));
                    profile.setSex(cursor.getString(5));
                    profile.setUrlPhoto100(cursor.getString(6));
                    profile.setUrlPhoto50(cursor.getString(7));
                    profiles.add(profile);
                } while (cursor.moveToNext());
            }
            return profiles;
        } finally {
            CursorUtils.closeCursor(cursor);
        }
    }

    private List<Item> getAllItems() {
        Cursor cursor = null;
        final SQLiteDatabase readable = mDBHelper.getReadableDatabase();
        final List<Item> items = new ArrayList<Item>();
        try {
            final String sql = SELECT_DISTINCT + SPACE + Item.class.getSimpleName() +
                    DOT + ConstantStrings.DB.ItemTable.ADDED_AT +
                    this.COMMA + ConstantStrings.DB.ItemTable.ATTACHMENTS +
                    this.COMMA + ConstantStrings.DB.ItemTable.COMMENTS +
                    this.COMMA + ConstantStrings.DB.ItemTable.SOURCE_ID +
                    this.COMMA + ConstantStrings.DB.ItemTable.DATE +
                    this.COMMA + ConstantStrings.DB.ItemTable.LIKES +
                    this.COMMA + ConstantStrings.DB.ItemTable.POST_ID +
                    this.COMMA + ConstantStrings.DB.ItemTable.POST_TYPE +
                    this.COMMA + ConstantStrings.DB.ItemTable.REPOSTS +
                    this.COMMA + ConstantStrings.DB.ItemTable.TEXT +
                    this.COMMA + ConstantStrings.DB.ItemTable.TYPE +
                    this.COMMA + ConstantStrings.DB.ItemTable.VIEWS + this.COMMA +
                    Comments.class.getSimpleName() +
                    DOT + ConstantStrings.DB.CommnetTable.CAN_POST + SPACE +
                    this.COMMA + ConstantStrings.DB.CommnetTable.COUNT_COMMENTS + this.COMMA +
                    Likes.class.getSimpleName() +
                    DOT + ConstantStrings.DB.LikesTable.CAN_LIKE + SPACE +
                    this.COMMA + ConstantStrings.DB.LikesTable.COUNT_LIKE + SPACE +
                    this.COMMA + ConstantStrings.DB.LikesTable.USER_LIKE + this.COMMA +
                    Reposts.class.getSimpleName() +
                    DOT + ConstantStrings.DB.RepostsTable.COUNT_REPOST + SPACE +
                    this.COMMA + ConstantStrings.DB.RepostsTable.USER_REPOST + this.COMMA +
                    Views.class.getSimpleName() +
                    DOT + ConstantStrings.DB.ViewsTable.COUNT +
                    FROM + Item.class.getSimpleName() + INNER_JOIN +
                    Comments.class.getSimpleName() + ON + OPEN_BRACKET +
                    Comments.class.getSimpleName() + DOT + ConstantStrings.DB.CommnetTable.COMMENTS_ID + EQUALLY
                    + Item.class.getSimpleName() + DOT + ConstantStrings.DB.ItemTable.POST_ID + CLOSE_BRACKET
                    + INNER_JOIN +
                    Likes.class.getSimpleName() + ON + OPEN_BRACKET +
                    Likes.class.getSimpleName() + DOT + ConstantStrings.DB.LikesTable.ID_LIKE + EQUALLY
                    + Item.class.getSimpleName() + DOT + ConstantStrings.DB.ItemTable.POST_ID + CLOSE_BRACKET
                    + INNER_JOIN +
                    Reposts.class.getSimpleName() + ON + OPEN_BRACKET +
                    Reposts.class.getSimpleName() + DOT + ConstantStrings.DB.RepostsTable.ID + EQUALLY
                    + Item.class.getSimpleName() + DOT + ConstantStrings.DB.ItemTable.POST_ID + CLOSE_BRACKET
                    + INNER_JOIN +
                    Views.class.getSimpleName() + ON + OPEN_BRACKET +
                    Views.class.getSimpleName() + DOT + ConstantStrings.DB.ViewsTable.ID + EQUALLY
                    + Item.class.getSimpleName() + DOT + ConstantStrings.DB.ItemTable.POST_ID + CLOSE_BRACKET
                    + ORDER_BY + Item.class.getSimpleName() + DOT + ConstantStrings.DB.ItemTable.ADDED_AT
                    + DESC + " limit " + mItemslinit + " , 50" + END_QUERY;
            cursor = readable.rawQuery(sql, null);

            items.addAll(readDataFromCursor(cursor));

        } finally {

            CursorUtils.closeCursor(cursor);
            readable.close();
        }
        if (items.isEmpty()) {
            return null;
        }
        mItemslinit += 51;
        return items;
    }

    private List<Item> readDataFromCursor(final Cursor pCursor) {
        final List<Item> items = new ArrayList<Item>();
        Item item;
        if (pCursor.moveToFirst()) {
            do {

                item = new Item(pCursor.getString(7),//type
                        pCursor.getInt(3),//source_id
                        pCursor.getLong(4),//date
                        pCursor.getInt(6),//post id
                        pCursor.getString(7),//post type
                        pCursor.getString(9),//text
                        //attachments
                        getAttachments(pCursor.getInt(6)),
                        //comments
                        new Comments(pCursor.getLong(13),
                                pCursor.getInt(12)),
                        //setLikes
                        new Likes(pCursor.getInt(15),
                                pCursor.getInt(16),
                                pCursor.getInt(14)),
                        //setReposts
                        new Reposts(pCursor.getLong(17),
                                pCursor.getInt(18)),
                        //setViews
                        new Views(pCursor.getLong(19)),
                        pCursor.getLong(0)//addedAt
                );
                items.add(item);
            } while (pCursor.moveToNext());
        } else {
            return null;
        }
        return items;
    }

    private List<Attachment> getAttachments(final int pIdAttachments) {
        final List<Attachment> listAttachment = new ArrayList<Attachment>();
        final SQLiteDatabase sqLiteDatabase = mDBHelper.getReadableDatabase();
        Cursor cursor = null;
        try {

            Log.d(TAG, "getAttachments: " + String.format(ConstantStrings.DB.Queryes.SELECT_ATTACHMENTS, pIdAttachments));
            cursor = sqLiteDatabase
                    .rawQuery(String
                                    .format(Locale.ENGLISH,
                                            ConstantStrings.DB.Queryes.SELECT_ATTACHMENTS,
                                            pIdAttachments),
                            null);
            Attachment attachment;
            if (cursor.moveToFirst()) {
                do {
                    attachment = new Attachment(
                            cursor.getString(0),
                            setDbDataPhoto(cursor),
                            setDbDataAudio(cursor),
                            setDbDataVideo(cursor),
                            setDbDataDoc(cursor),
                            setDbDataLink(cursor));
                    listAttachment.add(attachment);
                } while (cursor.moveToNext());
            } else {
                return null;
            }
        } catch (final Exception pE) {
            pE.fillInStackTrace();
        } finally {
            CursorUtils.closeCursor(cursor);
        }


        return listAttachment;
    }

    private Link setDbDataLink(final Cursor pCursor) {
        final Link link = new Link(
                pCursor.getString(28),
                pCursor.getString(26),
                pCursor.getString(25),
                getPhotoLink(pCursor.getLong(27)),
                pCursor.getString(24));
        if (link.getLinkTitle() == null) {
            return null;
        }
        return link;

    }

    private Photo getPhotoLink(final long pLong) {

        final SQLiteDatabase sqLiteDatabase = mDBHelper.getReadableDatabase();
        final String sql = "select Photo.* from Photo, Link " +
                " where " + pLong + EQUALLY + "Photo.idPhoto;";
        final Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        Photo photo = null;
        Log.d(TAG, "getPhotoLink: " + sql);

        try {
            if (cursor.moveToFirst()) {
                do {
                    photo = new Photo(
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getLong(0),
                            cursor.getLong(9),
                            cursor.getInt(10),
                            cursor.getInt(2),
                            cursor.getString(8),
                            cursor.getInt(7),
                            cursor.getString(1));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.fillInStackTrace();
        } finally {
            CursorUtils.closeCursor(cursor);
        }


        return photo;
    }

    private Doc setDbDataDoc(final Cursor pCursor) {
        final Doc doc = new Doc(
                pCursor.getString(22),
                pCursor.getString(23),
                pCursor.getString(19),
                pCursor.getInt(21),
                getDbDataPreview(pCursor.getLong(20)));
        if (doc.getUrl() == null) {
            return null;
        }
        return doc;
    }

    private Preview getDbDataPreview(final long idDataPreview) {
        return null;
    }

    private Video setDbDataVideo(final Cursor pCursor) {
        final Video video = new Video(
                pCursor.getString(17),
                pCursor.getString(16),
                pCursor.getString(18),
                pCursor.getInt(15));

        if (video.getSource() == null) {
            return null;
        }
        return video;
    }

    private Audio setDbDataAudio(final Cursor pCursor) {
        final Audio audio = new Audio(
                pCursor.getString(11),
                pCursor.getString(12),
                pCursor.getInt(13),
                pCursor.getString(14));

        if (audio.getArtist() == null) {
            return null;
        }
        return audio;
    }

    private Photo setDbDataPhoto(final Cursor pCursor) {
        final Photo photo = new Photo(
                pCursor.getString(6),
                pCursor.getString(7),
                pCursor.getString(4),
                pCursor.getString(5),
                pCursor.getLong(1),
                pCursor.getLong(10),
                pCursor.getInt(11),
                pCursor.getInt(3),
                pCursor.getString(9),
                pCursor.getInt(8),
                pCursor.getString(2));
        if (photo.getId() == 0) {
            return null;
        }
        return photo;
    }

}
