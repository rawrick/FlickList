package com.rawrick.flicklist.data.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.rawrick.flicklist.data.movie.MovieRated;
import com.rawrick.flicklist.data.movie.MovieWatchlisted;
import com.rawrick.flicklist.data.room.moviesrated_db.MovieRatedDAO;
import com.rawrick.flicklist.data.room.movieswatchlisted_db.MovieWatchlistedDAO;

@Database(entities = {MovieRated.class, MovieWatchlisted.class}, version = 1, exportSchema = false)

public abstract class FLDatabase extends RoomDatabase {
    public abstract MovieRatedDAO movieRatedDAO();
    public abstract MovieWatchlistedDAO movieWatchlistedDAO();
}