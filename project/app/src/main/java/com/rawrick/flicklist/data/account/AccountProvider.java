package com.rawrick.flicklist.data.account;

import static com.rawrick.flicklist.data.account.AccountManager.getAccountID;
import static com.rawrick.flicklist.data.account.AccountManager.getAccountName;
import static com.rawrick.flicklist.data.util.SettingsManager.getPreferenceAPIkey;
import static com.rawrick.flicklist.data.util.SettingsManager.getSessionID;
import static com.rawrick.flicklist.data.api.URL.accountURL;
import static com.rawrick.flicklist.data.api.APIRequest.keyAPI;

import android.content.Context;
import android.util.Log;

import com.rawrick.flicklist.BuildConfig;
import com.rawrick.flicklist.data.api.APIRequest;
import com.rawrick.flicklist.data.api.Parser;

import org.json.JSONObject;


import static com.rawrick.flicklist.data.api.APIRequest.APIsessionID;

public class AccountProvider {

    private final Context context;
    private static Account details;

    public AccountProvider(Context context) {
        this.context = context;
    }

    public void getAccountDetails(AccountProvider.DataListener listener) {
        if (details == null) {
            sendSessionRequest(new APIRequest.ResponseListener() {
                @Override
                public void onResponse(JSONObject response) {
                    details = Parser.parseAccountData(response);
                    Log.d("FlickListApp", "account data received");
                    listener.onAccountDataAvailable(details);
                }

                @Override
                public void onError() {
                    Log.d("FlickListApp", "No Connection.");
                    details = new Account(getAccountID(context), getAccountName(context), getAccountName(context), null, null, false);
                    listener.onAccountDataAvailable(details);
                }
            });
        } else {
            listener.onAccountDataAvailable(details);
        }
    }

    private void sendSessionRequest(APIRequest.ResponseListener listener) {
        keyAPI = getPreferenceAPIkey(context);
        if (!keyAPI.equals(BuildConfig.ApiKey)) {
            keyAPI = BuildConfig.ApiKey;
        }
        APIsessionID = getSessionID(context);
        APIRequest request = new APIRequest(accountURL + "?api_key=" + keyAPI + "&session_id=" + APIsessionID, context);
        request.get(listener);
    }

    public interface DataListener {
        void onAccountDataAvailable(Account data);
    }
}