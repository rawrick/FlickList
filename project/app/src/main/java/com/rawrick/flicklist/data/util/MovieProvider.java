package com.rawrick.flicklist.data.util;

import static com.rawrick.flicklist.data.tools.SettingsManager.getAccountID;
import static com.rawrick.flicklist.data.tools.SettingsManager.getPreferenceAPIkey;
import static com.rawrick.flicklist.data.tools.URL.accountURL;
import static com.rawrick.flicklist.data.tools.URL.trendingMoviesWeekURL;
import static com.rawrick.flicklist.data.util.APIRequest.accountID;
import static com.rawrick.flicklist.data.util.APIRequest.key;
import static com.rawrick.flicklist.data.util.APIRequest.sessionID;

import android.content.Context;
import android.util.Log;

import com.rawrick.flicklist.BuildConfig;
import com.rawrick.flicklist.data.movie.MovieRated;
import com.rawrick.flicklist.data.movie.MovieTrending;

import org.json.JSONObject;

import java.util.ArrayList;

public class MovieProvider {


    private final Context context;

    private ArrayList<MovieTrending> trendingMovieData;
    private ArrayList<MovieRated> ratedMovieData;

    public MovieProvider(Context context) {
        this.context = context;
    }

    public void getDataForMoviesTrending(DataListener listener) {
        if (trendingMovieData == null) {
            updateMoviesTrendingData(new APIRequest.ResponseListener() {
                @Override
                public void onResponse(JSONObject response) {
                    trendingMovieData = Parser.parseTrendingMovies(response);
                    listener.onTrendingMovieDataAvailable(trendingMovieData);
                }

                @Override
                public void onError() {
                    Log.d("FlickListApp", "No Connection");
                }
            });
        } else {
            listener.onTrendingMovieDataAvailable(trendingMovieData);
        }
    }

    private void updateMoviesTrendingData(APIRequest.ResponseListener listener) {
        key = getPreferenceAPIkey(context);
        if (!key.equals(BuildConfig.ApiKey)) {
            key = BuildConfig.ApiKey;
        }
        APIRequest request = new APIRequest(trendingMoviesWeekURL + "?api_key=" + key, context);
        request.send(listener);
    }

    public interface DataListener {
        void onTrendingMovieDataAvailable(ArrayList<MovieTrending> data);
    }

    public void getDataForRatedMovies(RatedMoviesDataListener listener) {
        if (ratedMovieData == null) {
            updateRatedMoviesData(new APIRequest.ResponseListener() {
                @Override
                public void onResponse(JSONObject response) {
                    ratedMovieData = Parser.parseRatedMoviesData(response);
                    listener.onRatedMoviesDataAvailable(ratedMovieData);
                }

                @Override
                public void onError() {
                    Log.d("FlickListApp", "No Connection");
                }
            });
        } else {
            listener.onRatedMoviesDataAvailable(ratedMovieData);
        }
    }

    private void updateRatedMoviesData(APIRequest.ResponseListener listener) {
        key = getPreferenceAPIkey(context);
        if (!key.equals(BuildConfig.ApiKey)) {
            key = BuildConfig.ApiKey;
        }
        accountID = getAccountID(context);
        APIRequest request = new APIRequest(accountURL + "/" + accountID + "/rated/movies?api_key=" + key + "&language=en-US&&session_id=" + sessionID + "&sort_by=created_at.asc&page=1", context);
        request.send(listener);
    }

    public interface RatedMoviesDataListener {
        void onRatedMoviesDataAvailable(ArrayList<MovieRated> data);
    }
}