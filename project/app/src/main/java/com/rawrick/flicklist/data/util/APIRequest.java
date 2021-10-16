package com.rawrick.flicklist.data.util;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rawrick.flicklist.BuildConfig;

public class APIRequest {

    private final Route route;
    private final Context context;

    private static final String key = BuildConfig.ApiKey;
    private static final String api = "https://api.themoviedb.org/3/movie/";
    private static final String id = "458156";

    public APIRequest(Route route, Context context) {
        this.route = route;
        this.context = context;
    }

    public void send(ResponseListener listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, this.route.url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listener.onResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError();
                    }
                });
        queue.add(stringRequest);
    }

    public enum Route {
        MOVIE_DATA(api + id + "?api_key=" + key),
        SERIES_DATA("");

        private final String url;

        Route(String url) {
            this.url = url;
        }
    }

    public interface ResponseListener {
        void onResponse(String response);

        void onError();
    }
}