package com.rawrick.flicklist.data.room;


import android.content.Context;

import androidx.room.Room;


import com.rawrick.flicklist.data.movie.MovieRated;

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

    // gets rated movie for id
    public MovieRated getMovieForID(int id) {
        return db.movieRatedDAO().getMovieForID(id);
    }

    // gets list of rated movies
    public List<MovieRated> getAllMoviesRated() {
        return db.movieRatedDAO().getAllMoviesRated();
    }

    // deletes movie
    public void deleteMovieRated(MovieRated movieRated) {
        MovieRated movieFromDB = db.movieRatedDAO().getMovieForID(movieRated.id);
        if (movieFromDB != null) {
            db.movieRatedDAO().deleteMovie(movieRated);
        }
    }

    // used in reset data
    public void clearAllTables() {
        db.clearAllTables();
    }
}