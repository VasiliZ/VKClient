package com.github.vasiliz.vkclient.base.db.config;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.github.vasiliz.vkclient.base.utils.StringUtils;
import com.github.vasiliz.vkclient.main.entity.Attachment;
import com.github.vasiliz.vkclient.main.entity.Groups;
import com.github.vasiliz.vkclient.main.entity.Item;
import com.github.vasiliz.vkclient.main.entity.Profile;
import com.github.vasiliz.vkclient.main.entity.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Field;
import java.util.List;

@Database(databaseName = "mydbVk",
        entity = {Profile.class,
                Item.class,
                Groups.class,
                Attachment.class})
public final class AppDB {

    private DBHelper mDBHelper;
    private static AppDB mAppDB;
    private static VkDBConfig mVkDBConfig;
    private static List<String> mQueryes;
    private String TAG = AppDB.class.getSimpleName();
    private SQLiteDatabase database;
    private static final String COMMA = ",";
    private static final String QUOTE = "\"";
    private static final String SINGLE_QUOTE = "\'";

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
            e.fillInStackTrace();
            // Log.d(TAG, "Error while trying read data to db");
        } finally {
            database.endTransaction();
        }

    }

    private String headOfQuery(final Class pAnyClass) {

        final StringBuilder stringBuilder = new StringBuilder();
        final Field[] fields = pAnyClass.getDeclaredFields();
        stringBuilder.append("Insert into ")
                .append(pAnyClass.getSimpleName())
                .append(" ( ");
        for (final Field field : fields) {
            if (field.isAnnotationPresent(com.github.vasiliz.vkclient.base.db.config.Field.class)) {
                stringBuilder.append(field.getName())
                        .append(" ,");
            }
        }

        stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
        stringBuilder.append(") VALUES ");
        Log.d(TAG, "headOfQuery: " + stringBuilder);

        return stringBuilder.toString();
    }

    private String dataOfQueryGroups(final List<Groups> pGroups) {
        final StringBuilder groupsValueBuilder = new StringBuilder();
        for (int i = 0; i < pGroups.size(); i++) {
            groupsValueBuilder
                    .append(" ( ").append(pGroups.get(i).getId()).append(COMMA)
                    .append(pGroups.get(i).getIsClosed()).append(COMMA)
                    .append(QUOTE).append(StringUtils.replace(pGroups.get(i).getNameGroup())).append(QUOTE).append(COMMA)
                    .append(QUOTE).append(pGroups.get(i).getScreenName()).append(QUOTE).append(COMMA)
                    .append(QUOTE).append(pGroups.get(i).getType()).append(QUOTE).append(COMMA)
                    .append(QUOTE).append(pGroups.get(i).getUrlGroupPhoto100()).append(QUOTE).append(COMMA)
                    .append(QUOTE).append(pGroups.get(i).getUrlGroupPhoto200()).append(QUOTE).append(COMMA)
                    .append(QUOTE).append(pGroups.get(i).getUrlGroupPhoto50()).append(QUOTE).append(COMMA);
            groupsValueBuilder.delete(groupsValueBuilder.length() - 1, groupsValueBuilder.length());
            groupsValueBuilder
                    .append(")")
                    .append(COMMA);
            if (pGroups.get(i).getNameGroup().contains("Сумеречный")) {
                Log.d(TAG, "dataOfQueryGroups: " + pGroups.get(i).getNameGroup());
            }
            }
            groupsValueBuilder.delete(groupsValueBuilder.length() - 1, groupsValueBuilder.length());
            groupsValueBuilder.append(";");
            Log.d(TAG, "writeData: " + groupsValueBuilder);
            return groupsValueBuilder.toString();
        }

        private String dataOfQueryProfiles ( final List<Profile> pProfiles){
            final StringBuilder profileValueBuilder = new StringBuilder();
            for (int i = 0; i < pProfiles.size(); i++) {
                profileValueBuilder.append("(")
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
                        .append(")")
                        .append(COMMA);

            }
            profileValueBuilder.delete(profileValueBuilder.length() - 1, profileValueBuilder.length());
            profileValueBuilder.append(";");
            Log.d(TAG, "dataOfQueryProfiles: " + profileValueBuilder);
            return profileValueBuilder.toString();
        }

        private String dataOfQueryItems ( final List<Item> pItems){
            final StringBuilder buildQueryForItem = new StringBuilder();
            for (int i = 0; i < pItems.size(); i++) {
                buildQueryForItem
                        .append("(")
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
                        .append(")")
                        .append(COMMA);
            }

            buildQueryForItem.delete(buildQueryForItem.length() - 1, buildQueryForItem.length());
            buildQueryForItem.append(";");
            return buildQueryForItem.toString();
        }

        private String objectToJson ( final Object pClass){

            final Gson gson = new GsonBuilder().create();
            return gson.toJson(pClass);

        }
    }
