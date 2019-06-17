package com.github.vasiliz.vkclient.base.db.config;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
import com.github.vasiliz.vkclient.news.entity.SizesPhotoPreview;
import com.github.vasiliz.vkclient.news.entity.Video;
import com.github.vasiliz.vkclient.news.entity.VideoDocPreview;
import com.github.vasiliz.vkclient.news.entity.Views;

import java.util.List;

public final class DBHelper extends SQLiteOpenHelper {


    private final List<String> mListOfQuery;
    private static DBHelper INSTANCE;
    private final String TAG = DBHelper.class.getSimpleName();

    private DBHelper(final Context pContext, final VkDBConfig pVkDBConfig, final List<String> pListOfQuery) {
        super(pContext, pVkDBConfig.getDbName(), pVkDBConfig.getCursorFactory(), pVkDBConfig.getVersion());
        mListOfQuery = pListOfQuery;
    }

    public static DBHelper getInstance(final Context pContext, final VkDBConfig pVkDBConfig, final List<String> pListOfQuery) {
        if (INSTANCE == null) {
            INSTANCE = new DBHelper(pContext, pVkDBConfig, pListOfQuery);
        }
        return INSTANCE;
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        try {
            onUpgrade(db, 0, 1);
            db.beginTransaction();
            for (int i = 0; i < mListOfQuery.size(); i++) {
                Log.d(TAG, "onCreate: " + mListOfQuery.get(i));
                db.execSQL(mListOfQuery.get(i));
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (final SQLException e) {
            e.fillInStackTrace();
        }
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        final String DROP_TABLE = "drop table if exists ";
        db.execSQL(DROP_TABLE + Attachment.class.getSimpleName());
        db.execSQL(DROP_TABLE + Audio.class.getSimpleName());
        db.execSQL(DROP_TABLE + Comments.class.getSimpleName());
        db.execSQL(DROP_TABLE + Doc.class.getSimpleName());
        db.execSQL(DROP_TABLE + Groups.class.getSimpleName());
        db.execSQL(DROP_TABLE + Item.class.getSimpleName());
        db.execSQL(DROP_TABLE + Likes.class.getSimpleName());
        db.execSQL(DROP_TABLE + Link.class.getSimpleName());
        db.execSQL(DROP_TABLE + Photo.class.getSimpleName());
        db.execSQL(DROP_TABLE + PhotoPreview.class.getSimpleName());
        db.execSQL(DROP_TABLE + Preview.class.getSimpleName());
        db.execSQL(DROP_TABLE + Profile.class.getSimpleName());
        db.execSQL(DROP_TABLE + Reposts.class.getSimpleName());
        db.execSQL(DROP_TABLE + SizesPhotoPreview.class.getSimpleName());
        db.execSQL(DROP_TABLE + Video.class.getSimpleName());
        db.execSQL(DROP_TABLE + VideoDocPreview.class.getSimpleName());
        db.execSQL(DROP_TABLE + Views.class.getSimpleName());
    }
}

