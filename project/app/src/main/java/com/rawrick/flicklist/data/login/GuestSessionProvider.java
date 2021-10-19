package com.rawrick.flicklist.data.login;

import static com.rawrick.flicklist.data.tools.SettingsManager.getPreferenceAPIkey;
import static com.rawrick.flicklist.data.util.APIRequest.key;

import android.content.Context;
import android.util.Log;

import com.rawrick.flicklist.BuildConfig;
import com.rawrick.flicklist.data.util.APIRequest;
import com.rawrick.flicklist.data.util.Parser;

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
        key = getPreferenceAPIkey(context);
        if (!key.equals(BuildConfig.ApiKey)) {
            key = BuildConfig.ApiKey;
        }
        APIRequest request = new APIRequest(APIRequest.Route.AUTHENTICATION_SESSION_GUEST_NEW, context);
        request.send(listener);
    }

    public interface DataListener {
        void onGuestSessionDataAvailable(String data);
    }

}
