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

    /**
     * Rated Movies
     */

    public void getRatedMoviesFromAPI(int currentPage) {
        MovieProvider provider = new MovieProvider(context);
        provider.getDataForRatedMovies(currentPage, new MovieProvider.RatedMoviesDataListener() {
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

    public int getRatedMoviesTotalPages() {
        if (ratedMovies.size() != 0) {
            return  ratedMovies.get(0).getPagesTotal();
        } else {
            return 0;
        }
    }

    public interface RatedMoviesManagerListener {
        void onRatedMoviesUpdated();
    }

    /**
     * Favorited Movies
     */

    public void getFavoritedMoviesFromAPI(int currentPage) {
        MovieProvider provider = new MovieProvider(context);
        provider.getDataForFavoritedMovies(currentPage, new MovieProvider.FavoritedMoviesDataListener() {
            @Override
            public void onFavoritedMoviesDataAvailable(ArrayList<MovieFavorited> data) {
                if (favoritedMovies == null) {
                    favoritedMovies = data;
                } else {
                    favoritedMovies.addAll(data);
                }
                listenerFavoritedMovies.onFavoritedMoviesUpdated();
            }
        });
    }

    public ArrayList<MovieFavorited> getFavoritedMovies() {
        return favoritedMovies;
    }

    public int getFavoritedMoviesTotalPages() {
        if (favoritedMovies.size() != 0) {
            return  favoritedMovies.get(0).getPagesTotal();
        } else {
            return 0;
        }
    }

    public interface FavoritedMoviesManagerListener {
        void onFavoritedMoviesUpdated();
    }

    /**
     * Watchlisted Movies
     */

    public void getWatchlistedMoviesFromAPI(int currentPage) {
        MovieProvider provider = new MovieProvider(context);
        provider.getDataForWatchlistedMovies(currentPage, new MovieProvider.WatchlistedMoviesDataListener() {
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

    public int getWatchlistedMoviesTotalPages() {
        if (watchlistedMovies.size() != 0) {
            return  watchlistedMovies.get(0).getPagesTotal();
        } else {
            return 0;
        }
    }

    public interface WatchlistedMoviesManagerListener {
        void onWatchlistedMoviesUpdated();
    }
}