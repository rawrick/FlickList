package com.rawrick.flicklist.data.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.rawrick.flicklist.MovieActivity;
import com.rawrick.flicklist.data.api.movies.MovieDetailsManager;

public class ActivitySelector implements MovieDetailsManager.MovieCastManagerListener,
        MovieDetailsManager.MovieDetailsManagerListener {

    private Intent intent;
    Context context;

    private MovieDetailsManager movieDetailsManager;


    public ActivitySelector(Context context) {
        this.context = context;
    }

    public void startMovieActivity(int movieID) {
        intent = new Intent(context, MovieActivity.class);
        intent.putExtra("id", movieID);
        movieDetailsManager = new MovieDetailsManager(context, this, this);
        movieDetailsManager.getMovieDetailsFromAPI(movieID);

    }

    @Override
    public void onMovieDetailsUpdated() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("movie", movieDetailsManager.getMovieDetails());
        intent.putExtra("movie", bundle);
        context.startActivity(intent);
    }

    @Override
    public void onMovieCastUpdated() {

    }
}