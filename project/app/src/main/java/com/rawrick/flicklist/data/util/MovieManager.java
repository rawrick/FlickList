package com.rawrick.flicklist.data.util;

import android.content.Context;
import android.util.Log;

import static com.rawrick.flicklist.data.tools.SettingsManager.getAccountID;
import static com.rawrick.flicklist.data.util.APIRequest.accountID;

import com.rawrick.flicklist.data.movie.Movie;
import com.rawrick.flicklist.data.movie.MovieRated;
import com.rawrick.flicklist.data.movie.MovieTrending;

import java.util.ArrayList;

public class MovieManager {

    private ArrayList<MovieTrending> moviesTrending;
    private ArrayList<MovieRated> ratedMovies;
    private Movie movie;
    private final Context context;
    private final TrendingMoviesManagerListener listenerTrendingMovies;
    private final RatedMoviesManagerListener listenerRatedMovies;
    private final MovieDetailsManagerListener listenerMovieDetails;

    public MovieManager(Context context, TrendingMoviesManagerListener listenerTrendingMovies, RatedMoviesManagerListener listenerRatedMovies, MovieDetailsManagerListener listenerMovieDetails) {
        this.context = context;
        this.listenerTrendingMovies = listenerTrendingMovies;
        this.listenerRatedMovies = listenerRatedMovies;
        this.listenerMovieDetails = listenerMovieDetails;
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
     * RATED/WATCHED MOVIES
     */

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


    /**
     * MOVIE DETAILS
     */

    public void getMovieDetailsFromAPI() {
        MovieProvider provider = new MovieProvider(context);
        provider.getDataForMovie(new MovieProvider.MovieDataListener() {
            @Override
            public void onMovieDataAvailable(Movie data) {
                movie = data;
                listenerMovieDetails.onMovieDetailsUpdated();
            }
        });
    }

    public Movie getMovieDetails() {
        return movie;
    }

    public interface MovieDetailsManagerListener {
        void onMovieDetailsUpdated();
    }
}
