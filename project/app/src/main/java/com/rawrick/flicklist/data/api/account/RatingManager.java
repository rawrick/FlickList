package com.rawrick.flicklist.data.api.account;

import static com.rawrick.flicklist.data.util.SettingsManager.getPreferenceAPIkey;
import static com.rawrick.flicklist.data.api.URL.movieURL;
import static com.rawrick.flicklist.data.api.APIRequest.APIkey;
import static com.rawrick.flicklist.data.api.APIRequest.APImovieID;
import static com.rawrick.flicklist.data.api.APIRequest.APIsessionID;
import static com.rawrick.flicklist.data.api.APIRequest.APIrating;

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

    public void postRating(float value) {
        APIrating = value;
        updateRating(new APIRequest.ResponseListener() {
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

    private void updateRating(APIRequest.ResponseListener listener) {
        APIkey = getPreferenceAPIkey(context);
        // request body
        JSONObject object = new JSONObject();
        try {
            object.put("value", APIrating);
        } catch (JSONException error) {
            error.printStackTrace();
        }
        APIRequest request = new APIRequest(movieURL + APImovieID + "/rating?api_key=" + APIkey + "&session_id=" + APIsessionID, context);
        request.post(listener, object);
    }
}