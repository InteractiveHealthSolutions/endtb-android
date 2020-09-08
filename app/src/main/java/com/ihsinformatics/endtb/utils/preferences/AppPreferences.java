package com.ihsinformatics.endtb.utils.preferences;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by Naveed Iqbal on 10/27/2017.
 * Email: h.naveediqbal@gmail.com
 */

public class AppPreferences {
    public static enum KEY {
        IS_LIVE,
        IS_FIRST_RUN,
        LANGUAGE

    }

    private static AppPreferences instance;
    private Context context;

    private AppPreferences(Context context) {
        this.context = context;
    }

    public static AppPreferences getInstance(Context context) {
        if(instance == null) {
            instance = new  AppPreferences(context);
        }

        return instance;
    }

    public void addOrUpdateStringPreference(KEY key, String value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(key.toString(), value).apply();
    }

    public String findStringPrferenceValue(KEY key, String defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key.toString(), defaultValue);
    }

    public void addOrUpdateBooleanPreference(KEY key, boolean value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(key.toString(), value).apply();
    }

    public boolean findBooleanPrferenceValue(KEY key, boolean defaultValue) {
        boolean value = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key.toString(), defaultValue);
        return value;
    }

   /* public LANGUAGE findLanguagePrferenceValue() {
        String language =  PreferenceManager.getDefaultSharedPreferences(context).getString(KEY.LANGUAGE.toString(), LANGUAGE.URDU.toString());
        LANGUAGE l = LANGUAGE.valueOf(language.toUpperCase(Locale.US));
        if(l !=null) {
            return l;
        }

        return LANGUAGE.URDU;


    }*/
}
