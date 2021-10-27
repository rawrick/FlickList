package com.rawrick.flicklist.data.login;

import static com.rawrick.flicklist.data.tools.SettingsManager.getPreferenceAPIkey;
import static com.rawrick.flicklist.data.util.APIRequest.key;

import android.content.Context;
import android.util.Log;

import com.rawrick.flicklist.BuildConfig;
import com.rawrick.flicklist.data.util.APIRequest;
import com.rawrick.flicklist.data.util.Parser;

import org.json.JSONObject;

public class SessionProvider {

    private final Context context;
    private static String sessionID;


    public SessionProvider(Context context) {
        this.context = context;
    }

    public void getSessionID(SessionProvider.DataListener listener) {
        // TODO check for existing session
        if (sessionID == null) {
            sendSessionRequest(new APIRequest.ResponseListener() {
                @Override
                public void onResponse(JSONObject response) {
                    sessionID = Parser.parseLoginSession(response);
                    listener.onSessionDataAvailable(sessionID);
                }

                @Override
                public void onError() {
                    Log.d("FlickListApp", "No Connection. Session Creation failed.");
                }
            });
        } else {
            listener.onSessionDataAvailable(sessionID);
        }
    }

    private void sendSessionRequest(APIRequest.ResponseListener listener) {
        key = getPreferenceAPIkey(context);
        if (!key.equals(BuildConfig.ApiKey)) {
            key = BuildConfig.ApiKey;
        }
        APIRequest request = new APIRequest("", context);
        request.get(listener);
    }

    public interface DataListener {
        void onSessionDataAvailable(String data);
    }
}