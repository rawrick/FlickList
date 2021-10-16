package com.rawrick.flicklist.data.util;

import android.content.Context;

import com.rawrick.flicklist.data.movie.MovieTrending;

import java.util.ArrayList;

public class MovieManager {

    private ArrayList<MovieTrending> moviesTrending;
    private final Context context;
    private final MovieManagerListener listener;

    public MovieManager(Context context, MovieManagerListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void getMoviesFromURL() {
        MovieProvider provider = new MovieProvider(context);
        provider.getDataForMoviesTrending(new MovieProvider.DataListener() {
            @Override
            public void onDataAvailable(ArrayList<MovieTrending> data) {
                moviesTrending = data;
                listener.onMoviesUpdated();
            }
        });
    }



    public ArrayList<MovieTrending> getMoviesTrending() {
        return moviesTrending;
    }

    public interface MovieManagerListener {
        void onMoviesUpdated();
    }

}
