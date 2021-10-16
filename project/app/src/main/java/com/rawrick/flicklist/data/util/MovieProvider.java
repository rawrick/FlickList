package com.rawrick.flicklist.data.util;

import android.content.Context;
import android.util.Log;

import com.rawrick.flicklist.data.movie.Movie;

import java.util.ArrayList;

public class MovieProvider {


    private final Context context;
    private ArrayList<Movie> movieData;

    public MovieProvider(Context context) {
        this.context = context;
    }

    public void getDataForMovie(DataListener listener) {
        if (movieData == null) {
            updateMovieData(new APIRequest.ResponseListener() {
                @Override
                public void onResponse(String response) {
                    movieData = Movie.fromJSONString(response);
                    listener.onDataAvailable(movieData);
                }

                @Override
                public void onError() {
                    Log.d("AudioBookApp", "No Connection");
                }
            });
        } else {
            listener.onDataAvailable(movieData);
        }
    }

    private void updateMovieData(APIRequest.ResponseListener listener) {
        APIRequest request = new APIRequest(APIRequest.Route.MOVIE_DATA, context);
        request.send(listener);
    }

    public interface DataListener {
        void onDataAvailable(ArrayList<Movie> data
        );
    }
}