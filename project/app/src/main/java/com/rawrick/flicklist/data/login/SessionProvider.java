package com.rawrick.flicklist.data.login;

import static com.rawrick.flicklist.data.tools.SettingsManager.getPreferenceAPIkey;
import static com.rawrick.flicklist.data.util.APIRequest.key;

import android.content.Context;
import android.util.Log;

import com.rawrick.flicklist.BuildConfig;
import com.rawrick.flicklist.data.movie.MovieTrending;
import com.rawrick.flicklist.data.util.APIRequest;

import org.json.JSONObject;

import java.util.ArrayList;

public class SessionProvider {

    private final Context context;
    private static String session;

    public SessionProvider(Context context) {
        this.context = context;
    }

    public void getSession(SessionProvider.DataListener listener) {
        // TODO check for existing session
        if (session == null) {
            sendSessionRequest(new APIRequest.ResponseListener() {
                @Override
                public void onResponse(JSONObject response) {
                    // parse result
                    listener.onSessionDataAvailable(session);
                }

                @Override
                public void onError() {
                    Log.d("FlickListApp", "No Connection. Session Creation failed.");
                }
            });
        } else {
            listener.onSessionDataAvailable(session);
        }
    }

    private void sendSessionRequest(APIRequest.ResponseListener listener) {
        key = getPreferenceAPIkey(context);
        if (!key.equals(BuildConfig.ApiKey)) {
            key = BuildConfig.ApiKey;
        }
        APIRequest request = new APIRequest(APIRequest.Route.AUTHENTICATION_SESSION_NEW, context);
        request.send(listener);
    }

    public interface DataListener {
        void onSessionDataAvailable(String data);
    }
}