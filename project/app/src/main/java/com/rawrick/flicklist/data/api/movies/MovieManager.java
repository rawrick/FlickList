package com.rawrick.flicklist.data.api.movies;

import android.content.Context;

import com.rawrick.flicklist.data.movie.MovieRated;
import com.rawrick.flicklist.data.movie.MovieWatchlisted;

import java.util.ArrayList;

public class MovieManager {

    private final Context context;
    private final RatedMoviesManagerListener listenerRatedMovies;
    private final WatchlistedMoviesManagerListener listenerWatchlistedMovies;

    private ArrayList<MovieRated> ratedMovies;
    private ArrayList<MovieWatchlisted> watchlistedMovies;

    public MovieManager(Context context, RatedMoviesManagerListener listenerRatedMovies, WatchlistedMoviesManagerListener listenerWatchlistedMovies) {
        this.context = context;
        this.listenerRatedMovies = listenerRatedMovies;
        this.listenerWatchlistedMovies = listenerWatchlistedMovies;
    }

    /**
     * Rated Movies
     */

    public void getRatedMoviesFromAPI() {
        MovieProvider provider = new MovieProvider(context);
        provider.getDataForRatedMovies(new MovieProvider.RatedMoviesDataListener() {
            @Override
            public void onRatedMoviesDataAvailable(ArrayList<MovieRated> data) {
                if (ratedMovies == null) {
                    ratedMovies = data;
                } else {
                    ratedMovies.addAll(data);
                }
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

    /**
     * Watchlisted Movies
     */

    public void getWatchlistedMoviesFromAPI() {
        MovieProvider provider = new MovieProvider(context);
        provider.getDataForWatchlistedMovies(new MovieProvider.WatchlistedMoviesDataListener() {
            @Override
            public void onWatchlistedMoviesDataAvailable(ArrayList<MovieWatchlisted> data) {
                if (watchlistedMovies == null) {
                    watchlistedMovies = data;
                } else {
                    watchlistedMovies.addAll(data);
                }
                listenerWatchlistedMovies.onWatchlistedMoviesUpdated();
            }
        });
    }

    public ArrayList<MovieWatchlisted> getWatchlistedMovies() {
        return watchlistedMovies;
    }

    public interface WatchlistedMoviesManagerListener {
        void onWatchlistedMoviesUpdated();
    }
}