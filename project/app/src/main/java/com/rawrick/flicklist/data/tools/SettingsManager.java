package com.rawrick.flicklist.data.tools;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class SettingsManager {

    public static boolean getLoginStatus(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isLoggedIn = sharedPreferences.getBoolean("login", false);
        return isLoggedIn;
    }

    public static String getPreferenceAPIkey(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String key = sharedPreferences.getString("api_key", "");
        return key;
    }

}