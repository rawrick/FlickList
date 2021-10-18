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

    public void getTrendingMoviesFromAPI() {
        MovieProvider provider = new MovieProvider(context);
        provider.getDataForMoviesTrending(new MovieProvider.DataListener() {
            @Override
            public void onTrendingMovieDataAvailable(ArrayList<MovieTrending> data) {
                moviesTrending = data;
                listener.onTrendingMoviesUpdated();
            }
        });
    }

    public ArrayList<MovieTrending> getMoviesTrending() {
        return moviesTrending;
    }

    public interface MovieManagerListener {
        void onTrendingMoviesUpdated();
    }

}
