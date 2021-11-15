package com.rawrick.flicklist.data.room;


import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import com.rawrick.flicklist.data.movie.Movie;

import java.util.ArrayList;
import java.util.List;

public class FLDatabaseHelper {
    private static final String DATABASE_NAME = "main-db";
    private final Context context;
    private FLDatabase db;
    private static FLDatabaseHelper mInstance = null;

    public FLDatabaseHelper(Context context) {
        this.context = context;
        initDatabase();
    }

    private void initDatabase() {
        db = Room.databaseBuilder(context, FLDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries()
                .build();
    }

    public static FLDatabaseHelper getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new FLDatabaseHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }

    /**
     * Movies
     */

    public void addOrUpdateMovie(Movie movie) {
        Movie movieFromDB = db.movieDAO().getMovieForID(movie.id);
        if (movieFromDB == null) {
            db.movieDAO().addMovie(movie);
        } else {
            db.movieDAO().updateMovie(movie);
        }
    }

    // deletes movie
    public void deleteMovie(Movie movie) {
        Movie movieFromDB = db.movieDAO().getMovieForID(movie.id);
        if (movieFromDB != null) {
            db.movieDAO().deleteMovie(movie);
        }
    }

    // gets rated movie for id
    public Movie getMovieForID(int id) {
        return db.movieDAO().getMovieForID(id);
    }

    // checks whether movie is in database
    public boolean isMovieInDBForID(int id) {
        return db.movieDAO().isEntryForID(id);
    }

    public void updateMovieRating(int id, float value) {
        db.movieDAO().updateRatingForID(id, value);
    }

    public void updateMovieFavouriteStatus(int id, boolean value) {
        db.movieDAO().updateFavouriteStatusForID(id, value);
    }

    public void updateMovieWatchlistStatus(int id, boolean value) {
        db.movieDAO().updateWatchlistStatusForID(id, value);
    }

     //gets list of rated movies
    public List<Movie> getMoviesForRating(double rating) {
        return db.movieDAO().getMoviesForRating(rating);
    }

    public void cleanDB(ArrayList<Movie> moviesFromAPI) {
        ArrayList<Movie> moviesFromDB = (ArrayList<Movie>) db.movieDAO().getAllMovies();
        for (Movie movieFromDB : moviesFromDB) {
            if (!moviesFromAPI.contains(movieFromDB)) {
                Log.d("FlickListApp", "deleted from db: " + movieFromDB.getTitle());
                db.movieDAO().deleteMovie(movieFromDB);
            }
        }
    }

    // gets list of all movies
    public List<Movie> getAllMovies() {
        return db.movieDAO().getAllMovies();
    }

    // used in reset data
    public void clearAllTables() {
        db.clearAllTables();
    }
}