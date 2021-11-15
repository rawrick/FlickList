package com.rawrick.flicklist.data.api.movies;

import android.content.Context;

import com.rawrick.flicklist.data.movie.MovieFavorited;
import com.rawrick.flicklist.data.movie.MovieRated;
import com.rawrick.flicklist.data.movie.MovieWatchlisted;

import java.util.ArrayList;

public class MovieManager {

    private final Context context;
    private final RatedMoviesManagerListener listenerRatedMovies;
    private final FavoritedMoviesManagerListener listenerFavoritedMovies;
    private final WatchlistedMoviesManagerListener listenerWatchlistedMovies;

    private ArrayList<MovieRated> ratedMovies;
    private ArrayList<MovieFavorited> favoritedMovies;
    private ArrayList<MovieWatchlisted> watchlistedMovies;

    public MovieManager(Context context,
                        RatedMoviesManagerListener listenerRatedMovies,
                        FavoritedMoviesManagerListener listenerFavoritedMovies,
                        WatchlistedMoviesManagerListener listenerWatchlistedMovies) {
        this.context = context;
        this.listenerRatedMovies = listenerRatedMovies;
        this.listenerFavoritedMovies = listenerFavoritedMovies;
        this.listenerWatchlistedMovies = listenerWatchlistedMovies;
    }

    public void getAllMovieDataFromAPI() {
        getRatedMoviesFromAPI(1);
        getFavoritedMoviesFromAPI(1);
        getWatchlistedMoviesFromAPI(1);
    }

    /**
     * Rated Movies
     */

    public void getRatedMoviesFromAPI(int fromPage) {
        MovieProvider provider = new MovieProvider(context);
        provider.getDataForRatedMovies(fromPage, new MovieProvider.RatedMoviesDataListener() {
            @Override
            public void onRatedMoviesDataAvailable(ArrayList<MovieRated> data) {
                if (ratedMovies == null) {
                    ratedMovies = data;
                } else {
                    ratedMovies.addAll(data);
                }
                if (data.get(0).getCurrentPage() < data.get(0).getPagesTotal()) {
                    getRatedMoviesFromAPI(data.get(0).getCurrentPage() + 1);
                } else if (data.get(0).getCurrentPage() == data.get(0).getPagesTotal()) {
                    listenerRatedMovies.onRatedMoviesUpdated();
                }
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
     * Favorited Movies
     */

    public void getFavoritedMoviesFromAPI(int fromPage) {
        MovieProvider provider = new MovieProvider(context);
        provider.getDataForFavoritedMovies(fromPage, new MovieProvider.FavoritedMoviesDataListener() {
            @Override
            public void onFavoritedMoviesDataAvailable(ArrayList<MovieFavorited> data) {
                if (favoritedMovies == null) {
                    favoritedMovies = data;
                } else {
                    favoritedMovies.addAll(data);
                }
                if (data.get(0).getCurrentPage() < data.get(0).getPagesTotal()) {
                    getFavoritedMoviesFromAPI(data.get(0).getCurrentPage() + 1);
                } else if (data.get(0).getCurrentPage() == data.get(0).getPagesTotal()) {
                    listenerFavoritedMovies.onFavoritedMoviesUpdated();
                }
            }
        });
    }

    public ArrayList<MovieFavorited> getFavoritedMovies() {
        return favoritedMovies;
    }

    public interface FavoritedMoviesManagerListener {
        void onFavoritedMoviesUpdated();
    }

    /**
     * Watchlisted Movies
     */

    public void getWatchlistedMoviesFromAPI(int fromPage) {
        MovieProvider provider = new MovieProvider(context);
        provider.getDataForWatchlistedMovies(fromPage, new MovieProvider.WatchlistedMoviesDataListener() {
            @Override
            public void onWatchlistedMoviesDataAvailable(ArrayList<MovieWatchlisted> data) {
                if (watchlistedMovies == null) {
                    watchlistedMovies = data;
                } else {
                    watchlistedMovies.addAll(data);
                }
                if (data.get(0).getCurrentPage() < data.get(0).getPagesTotal()) {
                    getWatchlistedMoviesFromAPI(data.get(0).getCurrentPage() + 1);
                } else if (data.get(0).getCurrentPage() == data.get(0).getPagesTotal()) {
                    listenerWatchlistedMovies.onWatchlistedMoviesUpdated();
                }
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