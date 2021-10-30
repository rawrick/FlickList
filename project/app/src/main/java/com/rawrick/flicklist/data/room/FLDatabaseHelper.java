package com.rawrick.flicklist.data.room;


import android.content.Context;
import android.util.Log;

import androidx.room.Room;


import com.rawrick.flicklist.data.movie.Movie;
import com.rawrick.flicklist.data.movie.MovieRated;
import com.rawrick.flicklist.data.movie.MovieWatchlisted;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class FLDatabaseHelper {
    private static final String DATABASE_NAME = "main-db";
    private final Context context;
    private FLDatabase db;

    public FLDatabaseHelper(Context context) {
        this.context = context;
        initDatabase();
    }

    private void initDatabase() {
        db = Room.databaseBuilder(context, FLDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries()
                .build();
    }

    /**
     * Rated Movies
     */

    public void addOrUpdateMovieRated(MovieRated movieRated) {
        MovieRated movieFromDB = db.movieRatedDAO().getMovieForID(movieRated.id);
        if (movieFromDB == null) {
            db.movieRatedDAO().addMovie(movieRated);
        } else {
            db.movieRatedDAO().updateMovie(movieRated);
        }
    }

    // deletes movie
    public void deleteMovieRated(MovieRated movieRated) {
        MovieRated movieFromDB = db.movieRatedDAO().getMovieForID(movieRated.id);
        if (movieFromDB != null) {
            db.movieRatedDAO().deleteMovie(movieRated);
        }
    }

    // gets rated movie for id
    public MovieRated getMovieForID(int id) {
        return db.movieRatedDAO().getMovieForID(id);
    }

    // gets list of rated movies
    public List<MovieRated> getMoviesRatedForRating(double rating) {
        return db.movieRatedDAO().getMoviesForRating(rating);
    }

    // gets list of rated movies
    public List<MovieRated> getAllMoviesRated() {
        return db.movieRatedDAO().getAllMoviesRated();
    }

    /**
     * Watchlisted Movies
     */

    public void addOrUpdateMovieWatchlisted(MovieWatchlisted movie) {
        MovieWatchlisted movieFromDB = db.movieWatchlistedDAO().getMovieForID(movie.id);
        if (movieFromDB == null) {
            db.movieWatchlistedDAO().addMovie(movie);
        } else {
            db.movieWatchlistedDAO().updateMovie(movie);
        }
    }

    // deletes movie
    public void deleteMovieWatchlisted(MovieWatchlisted movie) {
        MovieWatchlisted movieFromDB = db.movieWatchlistedDAO().getMovieForID(movie.id);
        if (movieFromDB != null) {
            db.movieWatchlistedDAO().deleteMovie(movie);
        }
    }

    // gets watchlisted movie for id
    public MovieWatchlisted getWatchlistedMovieForID(int id) {
        return db.movieWatchlistedDAO().getMovieForID(id);
    }

    // gets list of rated movies
    public List<MovieWatchlisted> getAllMoviesWatchlisted() {
        return db.movieWatchlistedDAO().getAllMoviesWatchlisted();
    }

    /**
     * Movie Deatils
     */

    public void addOrUpdateMovieDetails(Movie movie) {
        Movie movieFromDB = db.movieDetailsDAO().getMovieForID(movie.id);
        if (movieFromDB == null) {
            db.movieDetailsDAO().addMovie(movie);
        } else {
            db.movieDetailsDAO().updateMovie(movie);
        }
    }

    // deletes movie
    public void deleteMovieDetails(Movie movie) {
        Movie movieFromDB = db.movieDetailsDAO().getMovieForID(movie.id);
        if (movieFromDB != null) {
            db.movieDetailsDAO().deleteMovie(movie);
        }
    }

    public void clearMovieDetailsTable() {
        db.movieDetailsDAO().delete();
    }

    // gets movie deatils for id
    public Movie getMovieDetailsForID(int id) {
        return db.movieDetailsDAO().getMovieForID(id);
    }


    // used in reset data
    public void clearAllTables() {
        db.clearAllTables();
    }
}