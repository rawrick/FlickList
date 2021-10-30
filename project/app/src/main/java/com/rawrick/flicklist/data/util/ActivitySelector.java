package com.rawrick.flicklist.data.util;

import static com.rawrick.flicklist.data.api.APIRequest.movieID;

import android.content.Context;
import android.content.Intent;

import com.rawrick.flicklist.MovieActivity;
import com.rawrick.flicklist.data.api.APIRequest;
import com.rawrick.flicklist.data.api.movies.MovieDetailsManager;
import com.rawrick.flicklist.data.movie.Movie;
import com.rawrick.flicklist.data.room.FLDatabaseHelper;

public class ActivitySelector implements MovieDetailsManager.MovieDetailsManagerListener, MovieDetailsManager.MovieCastManagerListener {

    private FLDatabaseHelper db;
    private MovieDetailsManager movieDetailsManager;
    private Intent intent;
    Context context;

    public ActivitySelector(Context context) {
        this.context = context;
    }

    public void startMovieActivity(String movieID) {
        intent = new Intent(context, MovieActivity.class);
        APIRequest.movieID = movieID;
        db = new FLDatabaseHelper(context);
        movieDetailsManager = new MovieDetailsManager(context, this, this);
        movieDetailsManager.getMovieDetailsFromAPI();
    }

    @Override
    public void onMovieDetailsUpdated() {
        Movie movie = movieDetailsManager.getMovieDetails();
        db.addOrUpdateMovieDetails(movie);
        context.startActivity(intent);
    }

    @Override
    public void onMovieCastUpdated() {

    }
}
