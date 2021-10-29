package com.rawrick.flicklist.data.account;

import android.content.Context;

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

}