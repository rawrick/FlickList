package com.rawrick.flicklist.data.util;

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

    public void startMovieActivity(int movieID) {
        intent = new Intent(context, MovieActivity.class);
        intent.putExtra("id", movieID);
        db = FLDatabaseHelper.getInstance(context);
        movieDetailsManager = new MovieDetailsManager(context, this, this);
        movieDetailsManager.getMovieDetailsFromAPI(movieID);
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
