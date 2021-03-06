package com.rawrick.flicklist.data.api.login;

import static com.rawrick.flicklist.data.util.SettingsManager.getPreferenceAPIkey;
import static com.rawrick.flicklist.data.api.URL.authenticationSessionGuestNew;
import static com.rawrick.flicklist.data.api.APIRequest.APIkey;

import android.content.Context;
import android.util.Log;

import com.rawrick.flicklist.BuildConfig;
import com.rawrick.flicklist.data.api.APIRequest;
import com.rawrick.flicklist.data.api.Parser;

import org.json.JSONObject;

public class GuestSessionProvider {

    private final Context context;
    private static String session;

    public GuestSessionProvider(Context context) {
        this.context = context;
    }

    public void getGuestSessionID(GuestSessionProvider.DataListener listener) {
        // TODO check for existing session
        if (session == null) {
            sendSessionRequest(new APIRequest.ResponseListener() {
                @Override
                public void onResponse(JSONObject response) {
                    session = Parser.parseLoginGuestSession(response);
                    listener.onGuestSessionDataAvailable(session);
                }

                @Override
                public void onError() {
                    Log.d("FlickListApp", "No Connection. Guest Session Creation failed.");
                }
            });
        } else {
            listener.onGuestSessionDataAvailable(session);
        }
    }

    private void sendSessionRequest(APIRequest.ResponseListener listener) {
        APIkey = getPreferenceAPIkey(context);
        if (!APIkey.equals(BuildConfig.ApiKey)) {
            APIkey = BuildConfig.ApiKey;
        }
        APIRequest request = new APIRequest(authenticationSessionGuestNew + "?api_key=" + APIkey, context);
        request.get(listener);
    }

    public interface DataListener {
        void onGuestSessionDataAvailable(String data);
    }

}
