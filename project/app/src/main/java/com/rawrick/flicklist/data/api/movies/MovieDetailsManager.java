package com.rawrick.flicklist.data.api.movies;

import android.content.Context;

import com.rawrick.flicklist.data.credits.Cast;
import com.rawrick.flicklist.data.movie.Movie;

import java.util.ArrayList;

public class MovieDetailsManager {

    private Movie movieDetails;
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

    public void getMovieDetailsFromAPI(int id) {
        MovieProvider provider = new MovieProvider(context);
        provider.getDataForMovie(id, new MovieProvider.MovieDataListener() {
            @Override
            public void onMovieDataAvailable(Movie data) {
                movieDetails = data;
                listenerMovieDetails.onMovieDetailsUpdated();
            }
        });
    }

    public Movie getMovieDetails() {
        return movieDetails;
    }

    public interface MovieDetailsManagerListener {
        void onMovieDetailsUpdated();
    }

    /**
     * MovieDetails Cast
     */

    public void getMovieCastFromAPI(int id) {
        MovieProvider provider = new MovieProvider(context);
        provider.getCastForMovie(id, new MovieProvider.MovieCastDataListener() {
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
     * MovieDetails Crew
     */
}
