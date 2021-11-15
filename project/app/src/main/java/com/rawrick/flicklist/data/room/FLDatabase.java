package com.rawrick.flicklist.data.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.rawrick.flicklist.data.movie.Movie;
import com.rawrick.flicklist.data.room.movies_db.MovieDAO;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)

public abstract class FLDatabase extends RoomDatabase {
    public abstract MovieDAO movieDAO();
}