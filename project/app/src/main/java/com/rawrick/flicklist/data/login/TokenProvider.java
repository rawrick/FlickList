package com.rawrick.flicklist.data.login;

import static com.rawrick.flicklist.data.util.SettingsManager.getPreferenceAPIkey;
import static com.rawrick.flicklist.data.api.URL.authenticationTokenNew;
import static com.rawrick.flicklist.data.api.APIRequest.key;

import android.content.Context;
import android.util.Log;

import com.rawrick.flicklist.BuildConfig;
import com.rawrick.flicklist.data.api.APIRequest;
import com.rawrick.flicklist.data.api.Parser;

import org.json.JSONObject;

public class TokenProvider {

    private final Context context;
    private static String token;

    public TokenProvider(Context context) {
        this.context = context;
    }

    public void getToken(TokenProvider.DataListener listener) {
        if (token == null) {
            sendSessionRequest(new APIRequest.ResponseListener() {
                @Override
                public void onResponse(JSONObject response) {
                    token = Parser.parseLoginToken(response);
                    listener.onTokenDataAvailable(token);
                }

                @Override
                public void onError() {
                    Log.d("FlickListApp", "No Connection. Token Creation failed.");
                }
            });
        } else {
            listener.onTokenDataAvailable(token);
        }
    }

    private void sendSessionRequest(APIRequest.ResponseListener listener) {
        key = getPreferenceAPIkey(context);
        if (!key.equals(BuildConfig.ApiKey)) {
            key = BuildConfig.ApiKey;
        }
        APIRequest request = new APIRequest(authenticationTokenNew + "?api_key=" + key, context);
        request.get(listener);
    }

    public interface DataListener {
        void onTokenDataAvailable(String data);
    }
}