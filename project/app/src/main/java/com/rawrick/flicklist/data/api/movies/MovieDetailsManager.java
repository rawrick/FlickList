package com.rawrick.flicklist.data.api.movies;

import android.content.Context;

import com.rawrick.flicklist.data.credits.Cast;
import com.rawrick.flicklist.data.movie.Movie;

import java.util.ArrayList;

public class MovieDetailsManager {

    private Movie movie;
    private ArrayList<Cast> cast;
    private final Context context;

    private final MovieDetailsManagerListener listenerMovieDetails;
    private final MovieCastManagerListener listenerMovieCast;

    public MovieDetailsManager(Context context, MovieDetailsManagerListener listenerMovieDetails, MovieCastManagerListener listenerMovieCast) {
        this.context = context;
        this.listenerMovieDetails = listenerMovieDetails;
        this.listenerMovieCast = listenerMovieCast;
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

    /**
     * Movie Cast
     */

    public void getMovieCastFromAPI() {
        MovieProvider provider = new MovieProvider(context);
        provider.getCastForMovie(new MovieProvider.MovieCastDataListener() {
            @Override
            public void onMovieCastDataAvailable(ArrayList<Cast> data) {
                cast = data;
                listenerMovieCast.onMovieCastUpdated();
            }
        });
    }

    public ArrayList<Cast> getMovieCast() {
        return cast;
    }

    public interface MovieCastManagerListener {
        void onMovieCastUpdated();
    }

    /**
     * Movie Crew
     */
}