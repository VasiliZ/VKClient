package com.github.vasiliz.vkclient.base.utils;

import com.github.vasiliz.vkclient.news.entity.Groups;
import com.github.vasiliz.vkclient.news.entity.Photo;
import com.github.vasiliz.vkclient.news.entity.Profile;

public final class SetImageUtils {

    public static String chechAvatarGroup(final Groups groups) {
        if (groups.getUrlGroupPhoto200() != null) {
            return groups.getUrlGroupPhoto200();
        } else if (groups.getUrlGroupPhoto100() != null) {
            return groups.getUrlGroupPhoto100();
        } else {
            return groups.getUrlGroupPhoto50();
        }

    }

    public static String checkAvatarProfile(final Profile pProfile) {
        if (pProfile.getUrlPhoto100() != null) {
            return pProfile.getUrlPhoto100();
        } else {
            return pProfile.getUrlPhoto50();
        }
    }

    public static String checkAttachmentImage(Photo pPhoto){
        if (pPhoto.getPhoto1280()!=null){
            return pPhoto.getPhoto1280();
        }else if (pPhoto.getPhoto807()!=null){
            return pPhoto.getPhoto807();
        }else if (pPhoto.getPhoto604()!=null){
            return pPhoto.getPhoto604();
        }else {
            return pPhoto.getPhoto130();
        }
    }
}
