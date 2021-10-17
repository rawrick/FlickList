package com.rawrick.flicklist.data.tools;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class SettingsManager {

    public static String getPreferenceAPIkey(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String key = sharedPreferences.getString("api_key", "");
        return key;
    }

}