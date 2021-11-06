package com.rawrick.flicklist.data.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.rawrick.flicklist.data.movie.MovieDetails;
import com.rawrick.flicklist.data.movie.MovieFavorited;
import com.rawrick.flicklist.data.movie.MovieRated;
import com.rawrick.flicklist.data.movie.MovieWatchlisted;
import com.rawrick.flicklist.data.room.moviedetails_db.MovieDetailsDAO;
import com.rawrick.flicklist.data.room.moviesfavorited_db.MovieFavoritedDAO;
import com.rawrick.flicklist.data.room.moviesrated_db.MovieRatedDAO;
import com.rawrick.flicklist.data.room.movieswatchlisted_db.MovieWatchlistedDAO;

@Database(entities = {MovieDetails.class, MovieRated.class, MovieWatchlisted.class, MovieFavorited.class}, version = 1, exportSchema = false)

public abstract class FLDatabase extends RoomDatabase {
    public abstract MovieDetailsDAO movieDetailsDAO();
    public abstract MovieRatedDAO movieRatedDAO();
    public abstract MovieWatchlistedDAO movieWatchlistedDAO();
    public abstract MovieFavoritedDAO movieFavoritedDAO();
}