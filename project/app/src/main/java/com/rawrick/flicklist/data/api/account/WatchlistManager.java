package com.rawrick.flicklist.data.api.account;

import static com.rawrick.flicklist.data.api.URL.accountURL;
import static com.rawrick.flicklist.data.api.account.AccountManager.getAccountID;
import static com.rawrick.flicklist.data.util.SettingsManager.getPreferenceAPIkey;
import static com.rawrick.flicklist.data.util.SettingsManager.getSessionID;

import android.content.Context;
import android.util.Log;

import com.rawrick.flicklist.BuildConfig;
import com.rawrick.flicklist.data.api.APIRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class WatchlistManager {

    private final Context context;
    private String mediaType;

    public WatchlistManager(Context context) {
        this.context = context;
    }

    public void postWatchlistStatus(MediaType type, int id, boolean wl) {
        updateWatchlistStatus(type, id, wl, new APIRequest.ResponseListener() {
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

    private void updateWatchlistStatus(MediaType type, int id, boolean watchlist, APIRequest.ResponseListener listener) {
        // creates request body
        if (type == MediaType.MOVIE) {
            mediaType = "movie";
        } else if (type == MediaType.TV) {
            mediaType = "tv";
        }
        JSONObject object = new JSONObject();
        try {
            object.put("media_type", mediaType); // "movie" or "tv"
            object.put("media_id", id); // int
            object.put("watchlist", watchlist); // true / false
        } catch (JSONException error) {
            error.printStackTrace();
        }
        APIRequest request = new APIRequest(accountURL + "/" + getAccountID(context)
                + "/watchlist?api_key=" + getPreferenceAPIkey(context)
                + "&session_id=" + getSessionID(context), context);
        request.post(listener, object);
    }
}
