package com.rawrick.flicklist.data.api;

import static com.rawrick.flicklist.data.api.URL.authenticationSessionNew;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class APIRequest {

    private final String destination;
    private final Context context;

    public static String APIkey;
    public static String APItoken;
    public static boolean APItokenRequested = false;
    public static String APIsessionID;
    public static String APIguestSessionID;

    public static String APIaccountID;
    public static int APImovieID;
    public static int APImoviesRatedPageCurrent = 2;
    public static int APImoviesWatchlistedPageCurrent = 2;
    public static int APImoviesFavoritedPageCurrent = 2;
    public static String APIcurrentPageRatedMovies;
    public static String APIcurrentPageWatchlistedMovies;
    public static String APIcurrentPageFavouritedMovies;
    public static float APIrating;

    public APIRequest(String destination, Context context) {
        this.destination = destination;
        this.context = context.getApplicationContext();
    }

    /**
     * GET
     */

    public void get(ResponseListener listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        VolleyLog.DEBUG = true;
        String request = destination;
        if (APItokenRequested) {
            request = authenticationSessionNew + "?api_key=" + APIkey + "&request_token=" + APItoken;
        }
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, request, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        APItoken = null;
                        listener.onResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    Log.d("FlickListApp", "Cannot connect to Internet...Please check your connection!");
                }
                listener.onError();
                Log.d("FlickListApp", "No Connection");
                error.printStackTrace();
            }
        });
        queue.add(stringRequest);
    }

    /**
     * POST
     */

    public void post(ResponseListener listener, JSONObject requestBody) {
        RequestQueue queue = Volley.newRequestQueue(context);
        VolleyLog.DEBUG = true;
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, destination, requestBody,
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

    /**
     * DELETE
     */
    public void delete(ResponseListener listener, JSONObject requestBody) {
        RequestQueue queue = Volley.newRequestQueue(context);
        VolleyLog.DEBUG = true;
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.DELETE, destination, requestBody,
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