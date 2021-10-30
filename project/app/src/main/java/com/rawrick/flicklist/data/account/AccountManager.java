package com.rawrick.flicklist.data.account;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class AccountManager {

    private Account accountData;
    private final Context context;
    private final AccountManager.AccountManagerListener listener;

    public AccountManager(Context context, AccountManager.AccountManagerListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void getAccountDataFromAPI() {
        AccountProvider provider = new AccountProvider(context);
        provider.getAccountDetails(new AccountProvider.DataListener() {
            @Override
            public void onAccountDataAvailable(Account data) {
                accountData = data;
                listener.onAccountDataUpdated();
            }
        });
    }

    public Account getAccountData() {
        return accountData;
    }

    public interface AccountManagerListener {
        void onAccountDataUpdated();
    }

    /**
     * Shared Preferences
     */

    public static String getAccountID(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("accountid", "");
    }

    public static void setAccountID(Context context, String accountID) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("accountid", accountID);
        editor.apply();
    }

    public static String getAccountName(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("accountname", "");
    }

    public static void setAccountName(Context context, String accountName) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("accountname", accountName);
        editor.apply();
    }
}