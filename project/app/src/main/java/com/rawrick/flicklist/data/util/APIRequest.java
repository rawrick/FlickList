package com.rawrick.flicklist.data.util;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class APIRequest {

    private final Route route;
    private final Context context;

    public static String key;
    public static String token;
    public static String sessionID;
    public static String accountID;
    //BuildConfig.ApiKey;

    private static final String trendingMoviesWeekURL = "https://api.themoviedb.org/3/trending/movie/week";
    private static final String trendingMoviesDayURL = "https://api.themoviedb.org/3/trending/movie/day";
    private static final String trendingSeriesWeekURL = "https://api.themoviedb.org/3/trending/tv/week";
    private static final String trendingSeriesDayURL = "https://api.themoviedb.org/3/trending/tv/day";
    private static final String movieURL = "https://api.themoviedb.org/3/movie/";

    private static final String authenticationTokenNew = "https://api.themoviedb.org/3/authentication/token/new";
    private static final String authenticationSessionNew = "https://api.themoviedb.org/3/authentication/session/new";
    private static final String authenticationSessionGuestNew = "https://api.themoviedb.org/3/authentication/guest_session/new";

    private static final String accountURL = "https://api.themoviedb.org/3/account";

    private static final String ratedMoviesURL = "";

    public APIRequest(Route route, Context context) {
        this.route = route;
        this.context = context.getApplicationContext();
    }


    public void send(ResponseListener listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        VolleyLog.DEBUG = true;
        String request = this.route.url;
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

    public enum Route {
        MOVIES_TRENDING_WEEK_DATA(trendingMoviesWeekURL + "?api_key=" + key),
        MOVIES_TRENDING_DAY_DATA(trendingMoviesDayURL + "?api_key=" + key),
        SERIES_TRENDING_WEEK_DATA(trendingSeriesWeekURL + "?api_key=" + key),
        SERIES_TRENDING_DAY_DATA(trendingSeriesDayURL + "?api_key=" + key),

        AUTHENTICATION_SESSION_NEW(""),
        AUTHENTICATION_TOKEN_NEW(authenticationTokenNew + "?api_key=" + key),
        AUTHENTICATION_SESSION_GUEST_NEW(authenticationSessionGuestNew + "?api_key=" + key),
        ACCOUNT_DATA(accountURL + "?api_key=" + key + "&session_id=" + sessionID),

        RATED_MOVIES_DATA(accountURL + "/" + accountID + "/rated/movies?api_key=" + key + "&language=en-US&&session_id=" + sessionID + "&sort_by=created_at.asc&page=1");

        private final String url;

        Route(String url) {
            this.url = url;
        }
    }

    public interface ResponseListener {
        void onResponse(JSONObject response);

        void onError();
    }
}