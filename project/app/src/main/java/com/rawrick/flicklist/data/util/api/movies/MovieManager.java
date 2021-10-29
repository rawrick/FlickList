package com.rawrick.flicklist.data.util.api.movies;

import android.content.Context;

import com.rawrick.flicklist.data.credits.Cast;
import com.rawrick.flicklist.data.movie.Movie;
import com.rawrick.flicklist.data.movie.MovieRated;
import com.rawrick.flicklist.data.movie.MovieTrending;
import com.rawrick.flicklist.data.movie.MovieWatchlisted;

import java.util.ArrayList;

public class MovieManager {


    private ArrayList<MovieTrending> moviesTrending;
    private ArrayList<MovieRated> ratedMovies;
    private ArrayList<MovieWatchlisted> watchlistedMovies;
    private Movie movie;
    private ArrayList<Cast> cast;
    private final Context context;
    private final TrendingMoviesManagerListener listenerTrendingMovies;
    private final RatedMoviesManagerListener listenerRatedMovies;
    private final WatchlistedMoviesManagerListener listenerWatchlistedMovies;

    public MovieManager(Context context, TrendingMoviesManagerListener listenerTrendingMovies, RatedMoviesManagerListener listenerRatedMovies, WatchlistedMoviesManagerListener listenerWatchlistedMovies) {
        this.context = context;
        this.listenerTrendingMovies = listenerTrendingMovies;
        this.listenerRatedMovies = listenerRatedMovies;
        this.listenerWatchlistedMovies = listenerWatchlistedMovies;
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
