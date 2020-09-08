package com.ihsinformatics.endtb.utils.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.ihsinformatics.endtb.database.Entities.User;
import com.ihsinformatics.endtb.utils.Global;
import com.ihsinformatics.endtb.utils.Logger;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by Nabil shafi on 1/5/2018.
 */

public class PreferencesManager {

    public static final String PREF_KEY_LAST_LOGGED_IN_USERNAME = "last_username";
    public static final String PREF_KEY_USERNAME = "username";
    public static final String PREF_KEY_PASSWORD = "password";
    public static final String PREF_KEY_ID = "id";
    public static final String PREF_FILE_NAME = "elims_preferences";
    public static final String PREF_KEY_LAST_SYNC_DATE = "last_sync_date";
    public static final String PREF_KEY_LAST_METADATA_DATE = "last_metadata_date";

    public void writeUser(Context context, User user) {
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(PREF_FILE_NAME, 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PREF_KEY_USERNAME, user.getUserName());
        editor.putString(PREF_KEY_PASSWORD, user.getPassword());
        editor.putLong(PREF_KEY_ID, user.getId());

        editor.commit();
    }

    public void deleteUser(Context context) {
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(PREF_FILE_NAME, 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PREF_KEY_USERNAME, null);
        editor.putString(PREF_KEY_PASSWORD, null);
        editor.putLong(PREF_KEY_ID, -1);

        editor.commit();
    }

    public User fetchUser(Context context) {
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(PREF_FILE_NAME, 0); // 0 - for private mode

        String username = pref.getString(PREF_KEY_USERNAME, null);
        String password = pref.getString(PREF_KEY_PASSWORD, null);
        long id = pref.getLong(PREF_KEY_ID, -1);

        if(username == null || password == null || id == -1)
            return null;

        User user = new User();
        user.setUserName(username);
        user.setPassword(password);
        user.setId(id);

        return user;
    }

    public void writeLastLoggedInUsername(Context context, String username) {
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(PREF_FILE_NAME, 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PREF_KEY_LAST_LOGGED_IN_USERNAME,username);

        editor.commit();
    }

    public String fetchLastLoggedInUsername(Context context) {
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(PREF_FILE_NAME, 0); // 0 - for private mode

        String username = pref.getString(PREF_KEY_LAST_LOGGED_IN_USERNAME, null);

        return username;
    }

    public void writeLastSyncDate(Context context, Date date) {
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(PREF_FILE_NAME, 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PREF_KEY_LAST_SYNC_DATE, Global.MYSQL_DATE_FORMAT.format(date));

        editor.commit();
    }

    public Date fetchLastSyncDate(Context context) {
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(PREF_FILE_NAME, 0); // 0 - for private mode
        Date date = new Date();
        String dateString = pref.getString(PREF_KEY_LAST_SYNC_DATE, "2017-01-01");
        try {
            date = Global.MYSQL_DATE_FORMAT.parse(dateString);
        } catch (ParseException e) {
            Logger.log(e);
        }

        return date;
    }

    public void writeLastMetadataSyncDate(Context context, Date date) {
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(PREF_FILE_NAME, 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PREF_KEY_LAST_METADATA_DATE, Global.MYSQL_DATE_FORMAT.format(date));

        editor.commit();
    }

    public Date fetchLastMetadataSyncDate(Context context) {
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(PREF_FILE_NAME, 0); // 0 - for private mode
        Date date = new Date();
        String dateString = pref.getString(PREF_KEY_LAST_METADATA_DATE, "2017-01-01");
        try {
            date = Global.MYSQL_DATE_FORMAT.parse(dateString);
        } catch (ParseException e) {
            Logger.log(e);
        }

        return date;
    }
}
