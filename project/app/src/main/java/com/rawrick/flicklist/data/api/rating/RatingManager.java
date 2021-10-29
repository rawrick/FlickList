package com.rawrick.flicklist.data.api.rating;

import static com.rawrick.flicklist.data.util.SettingsManager.getPreferenceAPIkey;
import static com.rawrick.flicklist.data.api.URL.movieURL;
import static com.rawrick.flicklist.data.api.APIRequest.key;
import static com.rawrick.flicklist.data.api.APIRequest.movieID;
import static com.rawrick.flicklist.data.api.APIRequest.sessionID;
import static com.rawrick.flicklist.data.api.APIRequest.rating;

import android.content.Context;
import android.util.Log;

import com.rawrick.flicklist.BuildConfig;
import com.rawrick.flicklist.data.api.APIRequest;

import org.json.JSONObject;

public class RatingManager {

    private final Context context;

    public RatingManager(Context context) {
        this.context = context;
    }

    public void postRating(float value) {
        rating = value * 2;
        updateRating(new APIRequest.ResponseListener() {
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

    private void updateRating(APIRequest.ResponseListener listener) {
        key = getPreferenceAPIkey(context);
        if (!key.equals(BuildConfig.ApiKey)) {
            key = BuildConfig.ApiKey;
        }
        APIRequest request = new APIRequest(movieURL + movieID + "/rating?api_key=" + key + "&session_id=" + sessionID, context);
        request.post(listener);
    }
}
