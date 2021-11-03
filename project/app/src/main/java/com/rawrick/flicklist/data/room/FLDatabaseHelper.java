package com.rawrick.flicklist.data.room;


import android.content.Context;
import android.util.Log;

import androidx.room.Room;


import com.rawrick.flicklist.data.movie.Movie;
import com.rawrick.flicklist.data.movie.MovieFavorited;
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
    public MovieRated getMovieRatedForID(int id) {
        return db.movieRatedDAO().getMovieForID(id);
    }

    // checks whether movie is rated
    public boolean isMovieRatedForID (int id) {
        return db.movieRatedDAO().isRated(id);
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
     * Favorited Movies
     */

    public void addOrUpdateMovieFavorited(MovieFavorited movieFavorited) {
        MovieFavorited movieFromDB = db.movieFavoritedDAO().getMovieForID(movieFavorited.id);
        if (movieFromDB == null) {
            db.movieFavoritedDAO().addMovie(movieFavorited);
        } else {
            db.movieFavoritedDAO().updateMovie(movieFavorited);
        }
    }

    // deletes movie
    public void deleteMovieFavorited(MovieFavorited movieFavorited) {
        MovieFavorited movieFromDB = db.movieFavoritedDAO().getMovieForID(movieFavorited.id);
        if (movieFromDB != null) {
            db.movieFavoritedDAO().deleteMovie(movieFavorited);
        }
    }

    // gets favorited movie for id
    public MovieFavorited getMovieFavoritedForID(int id) {
        return db.movieFavoritedDAO().getMovieForID(id);
    }

    // checks whether movie is favorited
    public boolean isMovieFavoritedForID (int id) {
        return db.movieFavoritedDAO().isFavorited(id);
    }

    // gets list of favorited movies
    public List<MovieFavorited> getAllMoviesFavorited() {
        return db.movieFavoritedDAO().getAllMovies();
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

    // checks whether movie is watchlisted
    public boolean isMovieWatchlistedForID (int id) {
        return db.movieWatchlistedDAO().isWatchlisted(id);
    }

    // gets list of rated movies
    public List<MovieWatchlisted> getAllMoviesWatchlisted() {
        return db.movieWatchlistedDAO().getAllMoviesWatchlisted();
    }

    /**
     * Movie Details
     */

    public void addOrUpdateMovieDetails(Movie movie) {
        Movie movieFromDB = db.movieDetailsDAO().getMovieForID(movie.id);
        if (movieFromDB == null) {
            db.movieDetailsDAO().addMovie(movie);
        } else {
            db.movieDetailsDAO().updateMovie(movie);
        }
    }

    public void updateMovieRating(Movie movie, Float rating) {
        db.movieDetailsDAO().updateRating(movie.id, rating);
    }

    public void updateMovieFavoriteStatus(Movie movie, boolean isFavorite) {
        db.movieDetailsDAO().updateFavoriteStatus(movie.id, isFavorite);
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