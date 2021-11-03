package com.rawrick.flicklist.data.api.rating;

import static com.rawrick.flicklist.data.api.APIRequest.APIaccountID;
import static com.rawrick.flicklist.data.api.APIRequest.keyAPI;
import static com.rawrick.flicklist.data.api.APIRequest.APImovieID;
import static com.rawrick.flicklist.data.api.URL.accountURL;
import static com.rawrick.flicklist.data.util.SettingsManager.getPreferenceAPIkey;

import android.content.Context;
import android.util.Log;

import com.rawrick.flicklist.BuildConfig;
import com.rawrick.flicklist.data.api.APIRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class WatchlistManager {

    private final Context context;
    public static boolean isWatchlisted;
    public static String mediaType;
    public static int mediaID;

    public WatchlistManager(Context context) {
        this.context = context;
    }

    public void postWatchlistStatus(float value) {
        updateWatchlistStatus(new APIRequest.ResponseListener() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("FlickListApp", "response received, post status unknown");
            }

            @Override
            public void onError() {
                Log.d("FlickListApp", "No Connection");
            }
        });
    }

    private void updateWatchlistStatus(APIRequest.ResponseListener listener) {
        keyAPI = getPreferenceAPIkey(context);
        if (!keyAPI.equals(BuildConfig.ApiKey)) {
            keyAPI = BuildConfig.ApiKey;
        }
        // creates request body
        isWatchlisted = true;
        mediaType = "movie";
        mediaID = Integer.parseInt(APImovieID);
        JSONObject object = new JSONObject();
        try {
            object.put("media_type", mediaType); // TODO "movie" OR "tv"
            object.put("media_id", mediaID); // TODO post movie OR series ID
            object.put("watchlist", isWatchlisted);
        } catch (JSONException error) {
            error.printStackTrace();
        }
        APIRequest request = new APIRequest(accountURL + APIaccountID + "/watchlist?api_key=" + keyAPI, context);
        request.post(listener, object);
    }
}
