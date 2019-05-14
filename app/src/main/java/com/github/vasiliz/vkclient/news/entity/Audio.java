package com.github.vasiliz.vkclient.news.entity;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.github.vasiliz.vkclient.base.db.config.Field;
import com.github.vasiliz.vkclient.base.db.config.Id;
import com.github.vasiliz.vkclient.base.db.config.Table;
import com.github.vasiliz.vkclient.base.utils.ConstantStrings;
import com.google.gson.annotations.SerializedName;
@Table
public class Audio implements Parcelable {
    @Id
    private long idAudio;
    @SerializedName("artist")
    @Field
    private String mArtist;
    @Field
    @SerializedName("title")
    private String mNameSong;
    @Field
    @SerializedName("duration")
    private int mSongDuration;
    @Field
    @SerializedName("url")
    private String mSongUrl;

    public Audio() {
    }

    public Audio(final String pArtist, final String pNameSong, final int pSongDuration, final String pSongUrl) {
        mArtist = pArtist;
        mNameSong = pNameSong;
        mSongDuration = pSongDuration;
        mSongUrl = pSongUrl;
    }

    public String getArtist() {
        return mArtist;
    }

    public String getmNameSong() {
        return mNameSong;
    }

    public int getmSongDuration() {
        return mSongDuration;
    }

    public String getmSongUrl() {
        return mSongUrl;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(this.mArtist);
        dest.writeString(this.mNameSong);
        dest.writeInt(this.mSongDuration);
        dest.writeString(this.mSongUrl);
    }

    protected Audio(final Parcel in) {
        this.mArtist = in.readString();
        this.mNameSong = in.readString();
        this.mSongDuration = in.readInt();
        this.mSongUrl = in.readString();
    }

    public static final Creator<Audio> CREATOR = new Creator<Audio>() {
        @Override
        public Audio createFromParcel(final Parcel source) {
            return new Audio(source);
        }

        @Override
        public Audio[] newArray(final int size) {
            return new Audio[size];
        }
    };

    public ContentValues getContentValues(final long pIdAudio){
        final ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantStrings.DB.AudioTable.ID_AUDIO, pIdAudio);
        contentValues.put(ConstantStrings.DB.AudioTable.ARTIST, mArtist);
        contentValues.put(ConstantStrings.DB.AudioTable.NAME_SONG, mNameSong);
        contentValues.put(ConstantStrings.DB.AudioTable.SONG_DURATION, mSongDuration);
        contentValues.put(ConstantStrings.DB.AudioTable.URL_SONG, mSongUrl);
        return contentValues;
    }
}
