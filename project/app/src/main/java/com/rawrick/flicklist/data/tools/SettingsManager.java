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

    public static void setLoginStatus(Context context, boolean isLoggedIn) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("login", isLoggedIn);
        editor.apply();
    }

    public static boolean getLoginProgress(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isLoggedIn = sharedPreferences.getBoolean("loginprogress", false);
        return isLoggedIn;
    }

    public static void setLoginProgress(Context context, boolean isInProgress) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("loginprogress", isInProgress);
        editor.apply();
    }

    public static String getSessionID(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String sessionID = sharedPreferences.getString("sessionid", "");
        return sessionID;
    }

    public static void setSessionID(Context context, String sessionID) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("sessionid", sessionID);
        editor.apply();
    }

    public static String getAccountID(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String accountID = sharedPreferences.getString("accountid", "");
        return accountID;
    }

    public static void setAccountID(Context context, String accountID) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("accountid", accountID);
        editor.apply();
    }

    public static String getPreferenceAPIkey(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String key = sharedPreferences.getString("api_key", "");
        return key;
    }


}