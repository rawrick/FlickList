package com.rawrick.flicklist.data.api.account;

import static com.rawrick.flicklist.data.util.SettingsManager.getPreferenceAPIkey;
import static com.rawrick.flicklist.data.api.URL.movieURL;
import static com.rawrick.flicklist.data.util.SettingsManager.getSessionID;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.rawrick.flicklist.R;
import com.rawrick.flicklist.data.api.APIRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class RatingManager {

    private final Context context;

    public RatingManager(Context context) {
        this.context = context;
    }

    public void postRating(int id, float value) {
        updatePostMovieRating(id, value, new APIRequest.ResponseListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("success")) {
                        Toast.makeText(context, R.string.rating_success, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, R.string.rating_failure, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError() {
                Log.d("FlickListApp", "No Connection");
            }
        });
    }

    private void updatePostMovieRating(int id, float value, APIRequest.ResponseListener listener) {
        // request body
        JSONObject object = new JSONObject();
        try {
            object.put("value", value);
        } catch (JSONException error) {
            error.printStackTrace();
        }
        APIRequest request = new APIRequest(movieURL + id
                + "/rating?api_key=" + getPreferenceAPIkey(context)
                + "&session_id=" + getSessionID(context), context);
        request.post(listener, object);
    }

    public void deleteRating(int id) {
        updateDeleteRating(id, new APIRequest.ResponseListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("success")) {
                        Toast.makeText(context, R.string.rating_delete_success, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, R.string.rating_delete_failure, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError() {
                Log.d("FlickListApp", "No Connection");
            }
        });
    }

    private void updateDeleteRating(int id, APIRequest.ResponseListener listener) {
        APIRequest request = new APIRequest(movieURL + id
                + "/rating?api_key=" + getPreferenceAPIkey(context)
                + "&session_id=" + getSessionID(context), context);
        request.delete(listener);
    }
}