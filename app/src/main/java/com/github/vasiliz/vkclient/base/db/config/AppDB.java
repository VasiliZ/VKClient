package com.github.vasiliz.vkclient.base.db.config;

import android.database.Cursor;
import android.database.SQLException;
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
import java.util.Collections;
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

    private final String EQUALLY = " = ";

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
            database.disableWriteAheadLogging();
            final List<Groups> groups = pResponse.getResponseNews().getGroupsList();
            final List<Profile> profiles = pResponse.getResponseNews().getProfileList();
            final List<Item> items = pResponse.getResponseNews().getItemList();
            insertGroups(groups, database);
            insertProfiles(profiles, database);
            insertItems(items);

        } catch (final Exception e) {
            e.fillInStackTrace();
            //   Log.d(TAG, "ErrorRequest while trying read data to db");
        } finally {
            database.setTransactionSuccessful();
            database.endTransaction();
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

    private void insertItems(final List<Item> pItems) {
        final SQLiteDatabase sqLiteDatabase = mDBHelper.getWritableDatabase();
        try {
            sqLiteDatabase.beginTransaction();
            for (int i = 0; i < pItems.size(); i++) {
                final Item item = pItems.get(i);
                insertItem(item, sqLiteDatabase);
            }
            sqLiteDatabase.setTransactionSuccessful();
        } catch (final Exception pE) {
            pE.fillInStackTrace();
        } finally {
            sqLiteDatabase.endTransaction();
        }

    }

    private void insertItem(final Item pItem, final SQLiteDatabase pSQLiteDatabase) {

        pSQLiteDatabase
                .insertWithOnConflict(
                        Item.class.getSimpleName(),
                        null,
                        pItem.getContentValues(),
                        SQLiteDatabase.CONFLICT_REPLACE);

        insertAttachment(pItem, pSQLiteDatabase);
        insertLike(pItem, pSQLiteDatabase);
        insertComments(pItem, pSQLiteDatabase);
        insertReposts(pItem, pSQLiteDatabase);
        insertViews(pItem, pSQLiteDatabase);
    }

    private void insertViews(final Item pItem, final SQLiteDatabase pSQLiteDatabase) {
        if (pItem.getViews() != null) {
            pSQLiteDatabase
                    .insertWithOnConflict(
                            Views.class.getSimpleName(),
                            null,
                            pItem.getViews()
                                    .getContentValues(pItem.getPostId()),
                            SQLiteDatabase.CONFLICT_REPLACE);
        }
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
                                    attachment.getContentValues(pItem.getPostId(), pItem.getDate() + pItem.getPostId() + i),
                                    SQLiteDatabase.CONFLICT_IGNORE);
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
                        SQLiteDatabase.CONFLICT_IGNORE);
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

    private Cursor getCursorForSelectAllData(final Class pClass) {
        final String SELECT_FROM = "Select * from ";
        final SQLiteDatabase database = mDBHelper.getReadableDatabase();


        final String select = SELECT_FROM +
                pClass.getSimpleName();
        return database.rawQuery(select, null);
    }

    private List<Groups> getAllGroups() {

        final List<Groups> groups = new ArrayList<Groups>();
        final Cursor cursor = getCursorForSelectAllData(Groups.class);
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
        final Cursor cursor = getCursorForSelectAllData(Profile.class);
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
        readable.beginTransaction();
        final List<Item> items = new ArrayList<Item>();
        try {
            final String sql = "Select distinct" +
                    " * " +
                    " from Item " +
                    " order by Item.mAddedAt desc limit 50;";

            cursor = readable.rawQuery(sql, null);
            Log.d(TAG, "getAllItems: " + sql);

            items.addAll(readDataFromCursor(cursor, readable));
            readable.setTransactionSuccessful();
        } catch (final Exception pE) {
            pE.fillInStackTrace();
        } finally {
            readable.endTransaction();
            CursorUtils.closeCursor(cursor);
        }
        if (items.isEmpty()) {
            return null;
        }
        Collections.reverse(items);
        return items;
    }

    private List<Item> readDataFromCursor(final Cursor pCursor, final SQLiteDatabase pSQLiteDatabase) {
        final List<Item> items = new ArrayList<Item>();
        Item item;
        if (pCursor.moveToFirst()) {
            do {

                item = new Item(pCursor.getString(6),//type
                        pCursor.getInt(8),//source_id
                        pCursor.getLong(3),//date
                        pCursor.getInt(5),//post id
                        pCursor.getString(10),//post type
                        pCursor.getString(9),//text
                        //attachments
                        getAttachments(pCursor.getInt(1), pSQLiteDatabase),
                        //comments
                        getComments(pCursor.getLong(2), pSQLiteDatabase),
                        //setLikes
                        getLikes(pCursor.getInt(4), pSQLiteDatabase),
                        //setReposts
                        getReposts(pCursor.getLong(7), pSQLiteDatabase),
                        //setViews
                        getViews(pCursor.getLong(11), pSQLiteDatabase),
                        pCursor.getLong(0)//addedAt
                );
                items.add(item);
            } while (pCursor.moveToNext());
        } else {
            return null;
        }
        return items;
    }

    private Views getViews(final long pLong, final SQLiteDatabase pSQLiteDatabase) {
        Views views = null;
        Cursor cursor = null;
        try {
            cursor = pSQLiteDatabase
                    .rawQuery(String
                                    .format(
                                            Locale.ENGLISH,
                                            "Select * from Views where Views.mIdViews = %d",
                                            pLong),
                            null);
            if (cursor.moveToFirst()) {
                do {
                    views = new Views(cursor.getLong(0));
                } while (cursor.moveToNext());
            }
        } catch (final SQLException pE) {
            pE.fillInStackTrace();
        } finally {
            CursorUtils.closeCursor(cursor);
        }
        return views;
    }

    private Reposts getReposts(final long pLong, final SQLiteDatabase pSQLiteDatabase) {
        Reposts reposts = null;

        Cursor cursor = null;
        try {
            cursor = pSQLiteDatabase
                    .rawQuery(String
                                    .format(
                                            Locale.ENGLISH,
                                            "Select * from Reposts where Reposts.mRepostId = %d",
                                            pLong),
                            null);
            if (cursor.moveToFirst()) {
                do {
                    reposts = new Reposts(cursor.getLong(0),
                            cursor.getInt(2));
                } while (cursor.moveToNext());
            }
        } catch (final SQLException pE) {
            pE.fillInStackTrace();
        } finally {
            CursorUtils.closeCursor(cursor);
        }
        return reposts;
    }

    private Likes getLikes(final int pInt, final SQLiteDatabase pSQLiteDatabase) {
        Likes likes = null;
        Cursor cursor = null;
        try {
            cursor = pSQLiteDatabase
                    .rawQuery(String
                                    .format(
                                            Locale.ENGLISH,
                                            "select * from  Likes where Likes.mIdLike= %d order by Likes.mCountLike desc limit 1",
                                            pInt),
                            null);
            Log.d(TAG, "getLikes: " + pInt);
            if (cursor.moveToFirst()) {
                do {
                    likes = new Likes(cursor.getLong(1),
                            cursor.getInt(3),
                            cursor.getInt(0));
                } while (cursor.moveToNext());
            }
        } catch (final SQLException pE) {
            pE.fillInStackTrace();
        } finally {
            CursorUtils.closeCursor(cursor);
        }
        return likes;
    }

    private Comments getComments(final long pLong, final SQLiteDatabase pSQLiteDatabase) {
        Comments comments = null;
        Cursor cursor = null;
        try {
            cursor = pSQLiteDatabase
                    .rawQuery(String
                                    .format(
                                            Locale.ENGLISH,
                                            "Select * from Comments where Comments.mCommentsId = %d",
                                            pLong),
                            null);
            if (cursor.moveToFirst()) {
                do {
                    comments = new Comments(cursor.getLong(2),
                            cursor.getInt(0));
                } while (cursor.moveToNext());
            }
        } catch (final SQLException pE) {
            pE.fillInStackTrace();
        } finally {
            CursorUtils.closeCursor(cursor);
        }
        return comments;
    }

    private List<Attachment> getAttachments(final int pIdAttachments, final SQLiteDatabase pSQLiteDatabase) {
        final List<Attachment> listAttachment = new ArrayList<Attachment>();
        Cursor cursor = null;
        try {

            //  Log.d(TAG, "getAttachments: " + String.format(ConstantStrings.DB.Queryes.SELECT_ATTACHMENTS, pIdAttachments));
            cursor = pSQLiteDatabase
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
        final String sql = "select Photo.* from Photo " +
                " where " + pLong + EQUALLY + "Photo.idPhoto;";
        final Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        Photo photo = null;
        //  Log.d(TAG, "getPhotoLink: " + sql);

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
        } catch (final Exception e) {
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

    public void updateItem(final Item pItem) {
        final SQLiteDatabase database = mDBHelper.getWritableDatabase();

        try {

            database
                    .updateWithOnConflict(
                            Item.class.getSimpleName(),
                            pItem.updateItem(), null, null, SQLiteDatabase.CONFLICT_IGNORE);

            insertAttachment(pItem, database);
            insertLike(pItem, database);
            insertComments(pItem, database);
            insertReposts(pItem, database);
            insertViews(pItem, database);

        } catch (final Exception pE) {
            pE.fillInStackTrace();
        }


    }
}
