package com.rawrick.flicklist.data.account;

import android.content.Context;

import com.rawrick.flicklist.data.movie.MovieTrending;
import com.rawrick.flicklist.data.util.MovieProvider;

import java.util.ArrayList;

public class AccountManager {

    private String[] accountData;
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
            public void onAccountDataAvailable(String[] data) {
                accountData = data;
                listener.onAccountDataUpdated();
            }
        });
    }

    public String[] getAccountData() {
        return accountData;
    }

    public interface AccountManagerListener {
        void onAccountDataUpdated();
    }

}