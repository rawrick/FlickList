package com.rawrick.flicklist.data.account;

import static com.rawrick.flicklist.data.tools.SettingsManager.getPreferenceAPIkey;
import static com.rawrick.flicklist.data.tools.SettingsManager.getSessionID;
import static com.rawrick.flicklist.data.tools.URL.accountURL;
import static com.rawrick.flicklist.data.util.APIRequest.key;

import android.content.Context;
import android.util.Log;

import com.rawrick.flicklist.BuildConfig;
import com.rawrick.flicklist.data.util.APIRequest;
import com.rawrick.flicklist.data.util.Parser;

import org.json.JSONObject;


import static com.rawrick.flicklist.data.tools.SettingsManager.getPreferenceAPIkey;
import static com.rawrick.flicklist.data.util.APIRequest.key;
import static com.rawrick.flicklist.data.util.APIRequest.sessionID;

import android.content.Context;
import android.util.Log;

import com.rawrick.flicklist.BuildConfig;
import com.rawrick.flicklist.data.util.APIRequest;
import com.rawrick.flicklist.data.util.Parser;

import org.json.JSONObject;

public class AccountProvider {

    private final Context context;
    private static String[] details;

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
                    Log.d("FlickListApp", "No Connection. Token Creation failed.");
                }
            });
        } else {
            listener.onAccountDataAvailable(details);
        }
    }

    private void sendSessionRequest(APIRequest.ResponseListener listener) {
        key = getPreferenceAPIkey(context);
        if (!key.equals(BuildConfig.ApiKey)) {
            key = BuildConfig.ApiKey;
        }
        sessionID = getSessionID(context);
        APIRequest request = new APIRequest(accountURL + "?api_key=" + key + "&session_id=" + sessionID, context);
        request.send(listener);
    }

    public interface DataListener {
        void onAccountDataAvailable(String[] data);
    }
}