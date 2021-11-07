package com.rawrick.flicklist.data.room;


import android.content.Context;

import androidx.room.Room;


import com.rawrick.flicklist.data.movie.Movie;
import com.rawrick.flicklist.data.movie.MovieDetails;
import com.rawrick.flicklist.data.movie.MovieFavorited;
import com.rawrick.flicklist.data.movie.MovieRated;
import com.rawrick.flicklist.data.movie.MovieWatchlisted;

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

    // checks whether movie is rated
    //public boolean isMovieRatedForID(int id) {
    //    return db.movieDAO().isRated(id);
    //}

    //public void updateRatedMovieFavoriteStatus(MovieRated movieRated, boolean value) {
    //    db.movieRatedDAO().updateFavouriteStatus(value, movieRated.id);
    //}

     //gets list of rated movies
    public List<Movie> getMoviesForRating(double rating) {
        return db.movieDAO().getMoviesForRating(rating);
    }

    // gets list of sll movies
    public List<Movie> getAllMovies() {
        return db.movieDAO().getAllMovies();
    }

    /**
     * MovieDetails Details
     */

    public void addOrUpdateMovieDetails(MovieDetails movieDetails) {
        MovieDetails movieDetailsFromDB = db.movieDetailsDAO().getMovieForID(movieDetails.id);
        if (movieDetailsFromDB == null) {
            db.movieDetailsDAO().addMovie(movieDetails);
        } else {
            db.movieDetailsDAO().updateMovie(movieDetails);
        }
    }

    public void updateMovieRating(MovieDetails movieDetails, Float rating) {
        db.movieDetailsDAO().updateRating(movieDetails.id, rating);
    }

    public void updateMovieFavoriteStatus(MovieDetails movieDetails, boolean isFavorite) {
        db.movieDetailsDAO().updateFavoriteStatus(movieDetails.id, isFavorite);
    }


    // deletes movieDetails
    public void deleteMovieDetails(MovieDetails movieDetails) {
        MovieDetails movieDetailsFromDB = db.movieDetailsDAO().getMovieForID(movieDetails.id);
        if (movieDetailsFromDB != null) {
            db.movieDetailsDAO().deleteMovie(movieDetails);
        }
    }

    public void clearMovieDetailsTable() {
        db.movieDetailsDAO().delete();
    }

    // gets movie deatils for id
    public MovieDetails getMovieDetailsForID(int id) {
        return db.movieDetailsDAO().getMovieForID(id);
    }


    // used in reset data
    public void clearAllTables() {
        db.clearAllTables();
    }
}