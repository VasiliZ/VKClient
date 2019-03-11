package com.github.vasiliz.vkclient.base.db.config;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.github.vasiliz.vkclient.base.utils.StringUtils;
import com.github.vasiliz.vkclient.news.entity.Attachment;
import com.github.vasiliz.vkclient.news.entity.Comments;
import com.github.vasiliz.vkclient.news.entity.Groups;
import com.github.vasiliz.vkclient.news.entity.Item;
import com.github.vasiliz.vkclient.news.entity.Likes;
import com.github.vasiliz.vkclient.news.entity.Profile;
import com.github.vasiliz.vkclient.news.entity.Reposts;
import com.github.vasiliz.vkclient.news.entity.Response;
import com.github.vasiliz.vkclient.news.entity.ResponseNews;
import com.github.vasiliz.vkclient.news.entity.Views;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Database(databaseName = "mydbVk",
        entity = {Profile.class,
                Item.class,
                Groups.class,
                Attachment.class})
public final class AppDB {

    private final DBHelper mDBHelper;
    private static AppDB mAppDB;
    private static VkDBConfig mVkDBConfig;
    private static List<String> mQueryes;
    private final String TAG = AppDB.class.getSimpleName();
    private SQLiteDatabase database;
    private static final String COMMA = ",";
    private static final String QUOTE = "\"";
    private static final String SINGLE_QUOTE = "\'";
    private static final String SEMICOLON = ";";
    private static final String OPEN_BRACKET = "(";
    private static final String CLOSE_BRACKET = ")";
    private static final String INSERT = "Insert into ";
    private static final String VALUES = " VALUES ";
    private static final String SELECT_FROM = "Select * from ";
    private static final String LIMIT = " LIMIT ";


    private AppDB() {
        mDBHelper = new DBHelper(mVkDBConfig, mQueryes);
        mDBHelper.getWritableDatabase();
    }

    public static synchronized AppDB getInstanceDB(final VkDBConfig pVkDBConfig, final List<String> pQueryes) {
        if (mAppDB == null) {
            mVkDBConfig = pVkDBConfig;
            mQueryes = pQueryes;
            mAppDB = new AppDB();
        }
        return mAppDB;
    }

    public void writeData(final Response pResponse) {

        database = mAppDB.mDBHelper.getWritableDatabase();
        try {
            database.beginTransaction();
            final List<Groups> groups = pResponse.getResponseNews().getGroupsList();
            final List<Profile> profiles = pResponse.getResponseNews().getProfileList();
            final List<Item> items = pResponse.getResponseNews().getItemList();
            final String groupTable = headOfQuery(Groups.class) + dataOfQueryGroups(groups);
            final String fillProfileTable = headOfQuery(Profile.class) + dataOfQueryProfiles(profiles);
            final String fillItems = headOfQuery(Item.class) + dataOfQueryItems(items);
            database.execSQL(groupTable);
            database.execSQL(fillProfileTable);
            database.execSQL(fillItems);
            database.setTransactionSuccessful();
        } catch (final Exception e) {
            Log.d(TAG, "Error while trying read data to db");
        } finally {
            database.endTransaction();
        }

    }

    private String headOfQuery(final Class pAnyClass) {

        final StringBuilder stringBuilder = new StringBuilder();
        final Field[] fields = pAnyClass.getDeclaredFields();
        stringBuilder.append(INSERT)
                .append(pAnyClass.getSimpleName())
                .append(OPEN_BRACKET);
        for (final Field field : fields) {
            if (field.isAnnotationPresent(com.github.vasiliz.vkclient.base.db.config.Field.class)) {
                stringBuilder.append(field.getName())
                        .append(COMMA);
            }
        }

        stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
        stringBuilder.append(CLOSE_BRACKET + VALUES);
        return stringBuilder.toString();
    }

    private String dataOfQueryGroups(final List<Groups> pGroups) {
        final StringBuilder groupsValueBuilder = new StringBuilder();
        for (int i = 0; i < pGroups.size(); i++) {
            groupsValueBuilder
                    .append(OPEN_BRACKET).append(pGroups.get(i).getId()).append(COMMA)
                    .append(pGroups.get(i).getIsClosed()).append(COMMA)
                    .append(QUOTE).append(StringUtils.replace(pGroups.get(i).getNameGroup())).append(QUOTE).append(COMMA)
                    .append(QUOTE).append(pGroups.get(i).getScreenName()).append(QUOTE).append(COMMA)
                    .append(QUOTE).append(pGroups.get(i).getType()).append(QUOTE).append(COMMA)
                    .append(QUOTE).append(pGroups.get(i).getUrlGroupPhoto100()).append(QUOTE).append(COMMA)
                    .append(QUOTE).append(pGroups.get(i).getUrlGroupPhoto200()).append(QUOTE).append(COMMA)
                    .append(QUOTE).append(pGroups.get(i).getUrlGroupPhoto50()).append(QUOTE).append(COMMA);
            groupsValueBuilder.delete(groupsValueBuilder.length() - 1, groupsValueBuilder.length());
            groupsValueBuilder
                    .append(CLOSE_BRACKET)
                    .append(COMMA);
        }
        groupsValueBuilder.delete(groupsValueBuilder.length() - 1, groupsValueBuilder.length());
        groupsValueBuilder.append(SEMICOLON);
        return groupsValueBuilder.toString();
    }

    private String dataOfQueryProfiles(final List<Profile> pProfiles) {
        final StringBuilder profileValueBuilder = new StringBuilder();
        for (int i = 0; i < pProfiles.size(); i++) {
            profileValueBuilder.append(OPEN_BRACKET)
                    .append(SINGLE_QUOTE).append(pProfiles.get(i).getFirstName()).append(SINGLE_QUOTE).append(COMMA)
                    .append(pProfiles.get(i).getId()).append(COMMA)
                    .append(QUOTE).append(pProfiles.get(i).getLastName()).append(QUOTE).append(COMMA)
                    .append(pProfiles.get(i).getOnline()).append(COMMA)
                    .append(QUOTE).append(pProfiles.get(i).getScreenName()).append(QUOTE).append(COMMA)
                    .append(QUOTE).append(pProfiles.get(i).getSex()).append(QUOTE).append(COMMA)
                    .append(QUOTE).append(pProfiles.get(i).getUrlPhoto100()).append(QUOTE).append(COMMA)
                    .append(QUOTE).append(pProfiles.get(i).getUrlPhoto50()).append(QUOTE).append(COMMA);
            profileValueBuilder.delete(profileValueBuilder.length() - 1, profileValueBuilder.length());
            profileValueBuilder
                    .append(CLOSE_BRACKET)
                    .append(COMMA);

        }
        profileValueBuilder.delete(profileValueBuilder.length() - 1, profileValueBuilder.length());
        profileValueBuilder.append(SEMICOLON);
        return profileValueBuilder.toString();
    }

    private String dataOfQueryItems(final List<Item> pItems) {
        final StringBuilder buildQueryForItem = new StringBuilder();
        for (int i = 0; i < pItems.size(); i++) {
            buildQueryForItem
                    .append(OPEN_BRACKET)
                    .append(SINGLE_QUOTE).append(objectToJson(pItems.get(i).getAttachments())).append(SINGLE_QUOTE).append(COMMA)
                    .append(SINGLE_QUOTE).append(objectToJson(pItems.get(i).getComments())).append(SINGLE_QUOTE).append(COMMA)
                    .append(pItems.get(i).getDate()).append(COMMA)
                    .append(SINGLE_QUOTE).append(objectToJson(pItems.get(i).getLikes())).append(SINGLE_QUOTE).append(COMMA)
                    .append(pItems.get(i).getPostId()).append(COMMA)
                    .append(SINGLE_QUOTE).append(pItems.get(i).getPostType()).append(SINGLE_QUOTE).append(COMMA)
                    .append(SINGLE_QUOTE).append(objectToJson(pItems.get(i).getReposts())).append(SINGLE_QUOTE).append(COMMA)
                    .append(pItems.get(i).getSourseId()).append(COMMA)
                    .append(SINGLE_QUOTE).append(pItems.get(i).getText()).append(SINGLE_QUOTE).append(COMMA)
                    .append(SINGLE_QUOTE).append(pItems.get(i).getType()).append(SINGLE_QUOTE).append(COMMA)
                    .append(SINGLE_QUOTE).append(objectToJson(pItems.get(i).getViews())).append(SINGLE_QUOTE).append(COMMA);
            buildQueryForItem.delete(buildQueryForItem.length() - 1, buildQueryForItem.length());
            buildQueryForItem
                    .append(CLOSE_BRACKET)
                    .append(COMMA);
        }

        buildQueryForItem.delete(buildQueryForItem.length() - 1, buildQueryForItem.length());
        buildQueryForItem.append(SEMICOLON);
        return buildQueryForItem.toString();
    }

    private String objectToJson(final Object pClass) {
        final Gson gson = new GsonBuilder().create();
        return gson.toJson(pClass);

    }

    public Response getSaveData() {
        final Response response = new Response();
        final ResponseNews responseNews = new ResponseNews();
        responseNews.setGroupsList(getAllGroups());
        responseNews.setProfileList(getAllProfiles());
        responseNews.setItemList(getAllItems());
        Log.d(TAG, "getSaveData: " + responseNews.getItemList().size());
        response.setResponseNews(responseNews);
        return response;
    }

    private Cursor getCursorForSelectAllData(final Class pClass, final int pLimit) {
        final StringBuilder selectBuilder = new StringBuilder();
        selectBuilder
                .append(SELECT_FROM)
                .append(pClass.getSimpleName());
        final SQLiteDatabase database = mDBHelper.getReadableDatabase();
        if (pLimit!=0){
            selectBuilder.append(LIMIT).append(pLimit);
        }

        return database.rawQuery(selectBuilder.toString(), null);
    }

    private List<Groups> getAllGroups() {
        final List<Groups> groups = new ArrayList<>();
        final Cursor cursor = getCursorForSelectAllData(Groups.class, 0);
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                final Groups group = new Groups();
                group.setId(cursor.getInt(1));
                group.setIsClosed(cursor.getInt(2));
                group.setNameGroup(cursor.getString(3));
                group.setScreenName(cursor.getString(4));
                group.setType(cursor.getString(5));
                group.setUrlGroupPhoto100(cursor.getString(6));
                group.setUrlGroupPhoto200(cursor.getString(7));
                group.setUrlGroupPhoto50(cursor.getString(8));
                groups.add(group);
            }
        }
        return groups;
    }

    private List<Profile> getAllProfiles() {
        final Cursor cursor = getCursorForSelectAllData(Profile.class, 0);
        final List<Profile> profiles = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                final Profile profile = new Profile();
                profile.setFirstName(cursor.getString(1));
                profile.setId(cursor.getInt(2));
                profile.setLastName(cursor.getString(3));
                profile.setOnline(cursor.getInt(4));
                profile.setScreenName(cursor.getString(5));
                profile.setSex(cursor.getString(6));
                profile.setUrlPhoto100(cursor.getString(7));
                profile.setUrlPhoto50(cursor.getString(8));
                profiles.add(profile);
            }
        }
        return profiles;
    }

    private List<Item> getAllItems() {
        final Cursor cursor = getCursorForSelectAllData(Item.class, 50);
        final List<Item> items = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                final Item item = new Item();
                item.setAttachments(getGson().fromJson(cursor.getString(1), new TypeToken<ArrayList<Attachment>>() {

                }.getType()));
                item.setComments(getGson().fromJson(cursor.getString(2), Comments.class));
                item.setDate(cursor.getInt(3));
                item.setLikes(getGson().fromJson(cursor.getString(4), Likes.class));
                item.setPostId(cursor.getInt(5));
                item.setPostType(cursor.getString(6));
                item.setReposts(getGson().fromJson(cursor.getString(7), Reposts.class));
                item.setSourseId(cursor.getInt(8));
                item.setText(cursor.getString(9));
                item.setType(cursor.getString(10));
                item.setViews(getGson().fromJson(cursor.getString(11), Views.class));
                items.add(item);
            }
        }
        Collections.reverse(items);
        return items;
    }

    private Gson getGson() {
        return new GsonBuilder().create();
    }
}
