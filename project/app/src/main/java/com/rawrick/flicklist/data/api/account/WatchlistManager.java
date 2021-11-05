package com.rawrick.flicklist.data.api.account;

import static com.rawrick.flicklist.data.api.APIRequest.APIaccountID;
import static com.rawrick.flicklist.data.api.APIRequest.APIkey;
import static com.rawrick.flicklist.data.api.APIRequest.APImovieID;
import static com.rawrick.flicklist.data.api.APIRequest.APIsessionID;
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
    private int mediaID;
    private MediaType mediaType;
    private String mediaTypeString;
    private boolean watchlist;

    public WatchlistManager(Context context) {
        this.context = context;
    }

    public void postWatchlistStatus(MediaType type, int id, boolean wl) {
        mediaType = type;
        mediaID = id;
        watchlist = wl;
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
        APIkey = getPreferenceAPIkey(context);
        // creates request body
        if (mediaType == MediaType.MOVIE) {
            mediaTypeString = "movie";
        } else if (mediaType == MediaType.TV) {
            mediaTypeString = "tv";
        }
        JSONObject object = new JSONObject();
        try {
            object.put("media_type", mediaTypeString); // "movie" or "tv"
            object.put("media_id", mediaID); // int
            object.put("watchlist", watchlist); // true / false
        } catch (JSONException error) {
            error.printStackTrace();
        }
        APIRequest request = new APIRequest(accountURL + "/" + APIaccountID
                + "/watchlist?api_key=" + APIkey
                + "&session_id=" + APIsessionID, context);
        request.post(listener, object);
    }
}
