package com.rawrick.flicklist.data.util;

import android.util.Log;

import com.rawrick.flicklist.data.movie.Movie;
import com.rawrick.flicklist.data.movie.MovieTrending;
import com.rawrick.flicklist.data.movie.SeriesTrending;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Parser {

    private final static String img500 = "http://image.tmdb.org/t/p/w500/";

    public static String parseLoginToken(JSONObject response) {
        try {
            boolean success = response.getBoolean("success");
            if (!success) {
                Log.d("apidebug", "token creation failed");
                return null;
            }
            String expiration = response.getString("expires_at");
            String token = response.getString("request_token");
            return token;
        } catch (JSONException e) {
            Log.d("apidebug", "token parse error");
            e.printStackTrace();
        }
        return null;
    }

    public static String parseLoginSession(JSONObject response) {
        try {
            boolean success = response.getBoolean("success");
            if (!success) {
                Log.d("apidebug", "session creation failed");
                return null;
            }
            String id = response.getString("session_id");
            return id;
        } catch (JSONException e) {
            Log.d("apidebug", "session parse error");
            e.printStackTrace();
        }
        return null;
    }

    public static String parseLoginGuestSession(JSONObject response) {
        try {
            boolean success = response.getBoolean("success");
            if (!success) {
                Log.d("apidebug", "guest session creation failed");
                return null;
            }
            String expiration = response.getString("expires_at");
            String guest_session_id = response.getString("guest_session_id");
            return guest_session_id;
        } catch (JSONException e) {
            Log.d("apidebug", "token parse error");
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<MovieTrending> parseTrendingMovies(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray("results");
            ArrayList<MovieTrending> movieArrayList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject result = jsonArray.getJSONObject(i);

                String title = result.getString("title");
                double voteAverage = result.getDouble("vote_average");
                String overview = result.getString("overview");
                String releaseDate = result.getString("release_date");
                int id = result.getInt("id");
                boolean isAdult  = result.getBoolean("adult");
                String backdropPath = result.getString("backdrop_path");
                String posterPath = result.getString("poster_path");
                int index = i;
                boolean isSelected;
                if (i == 0) {
                    isSelected = true;
                } else {
                    isSelected = false;
                }

                // unused
                int vote_count = result.getInt("vote_count");
                //JSONArray genre_ids = result.getJSONArray("genre_ids");
                boolean video = result.getBoolean("video");
                String original_language = result.getString("original_language");
                String original_title = result.getString("original_title");
                int popularity = result.getInt("popularity");
                String media_type = result.getString("media_type");

                String fullPosterPath = img500 + posterPath;
                String fullBackdropPath = img500 + backdropPath;
                movieArrayList.add(new MovieTrending(fullBackdropPath, index, isSelected, isAdult, id, overview, fullPosterPath, releaseDate, title, voteAverage));
            }
            return movieArrayList;
        } catch (JSONException e) {
            Log.d("apidebug", "movie trending parse error");
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<SeriesTrending> parseTrendingSeries(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray("results");
            ArrayList<SeriesTrending> seriesArrayList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject result = jsonArray.getJSONObject(i);

                String title = result.getString("name");
                double voteAverage = result.getDouble("vote_average");
                String overview = result.getString("overview");
                String releaseDate = result.getString("first_air_date");
                int id = result.getInt("id");
                String backdropPath = result.getString("backdrop_path");
                String posterPath = result.getString("poster_path");
                int index = i;
                boolean isSelected;
                if (i == 0) {
                    isSelected = true;
                } else {
                    isSelected = false;
                }

                String fullPosterPath = img500 + posterPath;
                String fullBackdropPath = img500 + backdropPath;
                seriesArrayList.add(new SeriesTrending(fullBackdropPath, index, isSelected, id, overview, fullPosterPath, releaseDate, title, voteAverage));
            }
            return seriesArrayList;
        } catch (JSONException e) {
            Log.d("apidebug", "series trending parse error");
            e.printStackTrace();
        }
        return null;
    }

}
