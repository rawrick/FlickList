package com.rawrick.flicklist.data.util;

import static com.rawrick.flicklist.data.tools.URL.authenticationSessionNew;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class APIRequest {

    private final String destination;
    private final Context context;

    public static String key;
    public static String token;
    public static String sessionID;
    public static String accountID;
    public static String movieID;
    public static String currentPageRatedMovies;
    public static String currentPageWatchlistedMovies;

    // post values
    public static float rating;
    //BuildConfig.ApiKey;



    private static final String ratedMoviesURL = "";

    public APIRequest(String destination, Context context) {
        this.destination = destination;
        this.context = context.getApplicationContext();
    }


    public void get(ResponseListener listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        VolleyLog.DEBUG = true;
        String request = destination;
        if (token != null) {
            request = authenticationSessionNew + "?api_key=" + key + "&request_token=" + token;
        }
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, request, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        token = null;
                        listener.onResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError();
                Log.d("FlickListApp", "No Connection");
                error.printStackTrace();
            }
        });
        queue.add(stringRequest);
    }

    public void post(ResponseListener listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        VolleyLog.DEBUG = true;
        // creates request body
        JSONObject object = new JSONObject();
        try {
            object.put("value", rating);
        } catch (JSONException error) {
            error.printStackTrace();
        }

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, destination, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError();
                Log.d("FlickListApp", "No Connection");
                error.printStackTrace();
            }
        });
        queue.add(stringRequest);
    }

    public interface ResponseListener {
        void onResponse(JSONObject response);

        void onError();
    }
}