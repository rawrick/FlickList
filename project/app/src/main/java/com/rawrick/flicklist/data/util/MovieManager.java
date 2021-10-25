package com.rawrick.flicklist.data.util;

import android.content.Context;
import android.util.Log;

import static com.rawrick.flicklist.data.tools.SettingsManager.getAccountID;
import static com.rawrick.flicklist.data.util.APIRequest.accountID;

import com.rawrick.flicklist.data.movie.MovieRated;
import com.rawrick.flicklist.data.movie.MovieTrending;

import java.util.ArrayList;

public class MovieManager {

    private ArrayList<MovieTrending> moviesTrending;
    private ArrayList<MovieRated> ratedMovies;
    private final Context context;
    private final TrendingMoviesManagerListener listenerTrendingMovies;
    private final RatedMoviesManagerListener listenerRatedMovies;

    public MovieManager(Context context, TrendingMoviesManagerListener listenerTrendingMovies, RatedMoviesManagerListener listenerRatedMovies) {
        this.context = context;
        this.listenerTrendingMovies = listenerTrendingMovies;
        this.listenerRatedMovies = listenerRatedMovies;
    }

    public void getTrendingMoviesFromAPI() {
        MovieProvider provider = new MovieProvider(context);
        provider.getDataForMoviesTrending(new MovieProvider.DataListener() {
            @Override
            public void onTrendingMovieDataAvailable(ArrayList<MovieTrending> data) {
                moviesTrending = data;
                listenerTrendingMovies.onTrendingMoviesUpdated();
            }
        });
    }

    public ArrayList<MovieTrending> getMoviesTrending() {
        return moviesTrending;
    }

    public interface TrendingMoviesManagerListener {
        void onTrendingMoviesUpdated();
    }

    public void getRatedMoviesFromAPI() {
        MovieProvider provider = new MovieProvider(context);
        provider.getDataForRatedMovies(new MovieProvider.RatedMoviesDataListener() {
            @Override
            public void onRatedMoviesDataAvailable(ArrayList<MovieRated> data) {
                ratedMovies = data;
                listenerRatedMovies.onRatedMoviesUpdated();
            }
        });
    }

    public ArrayList<MovieRated> getRatedMovies() {
        return ratedMovies;
    }

    public interface RatedMoviesManagerListener {
        void onRatedMoviesUpdated();
    }

}
