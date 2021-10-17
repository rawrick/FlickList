package com.rawrick.flicklist.data.util;

import android.content.Context;
import android.util.Log;

import com.rawrick.flicklist.data.movie.MovieTrending;

import org.json.JSONObject;

import java.util.ArrayList;

public class MovieProvider {


    private final Context context;
    private ArrayList<MovieTrending> movieData;

    public MovieProvider(Context context) {
        this.context = context;
    }

    public void getDataForMoviesTrending(DataListener listener) {
        if (movieData == null) {
            updateMoviesTrendingData(new APIRequest.ResponseListener() {
                @Override
                public void onResponse(JSONObject response) {
                    movieData = Parser.parseTrendingMovies(response);
                    listener.onTrendingMovieDataAvailable(movieData);
                }
                @Override
                public void onError() {
                    Log.d("FlickListApp", "No Connection");
                }
            });
        } else {
            listener.onTrendingMovieDataAvailable(movieData);
        }
    }

    private void updateMoviesTrendingData(APIRequest.ResponseListener listener) {
        APIRequest request = new APIRequest(APIRequest.Route.MOVIES_TRENDING_WEEK_DATA, context);
        request.send(listener);
    }

    public interface DataListener {
        void onTrendingMovieDataAvailable(ArrayList<MovieTrending> data
        );
    }
}