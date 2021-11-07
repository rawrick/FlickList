package com.rawrick.flicklist.data.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.rawrick.flicklist.data.movie.Movie;
import com.rawrick.flicklist.data.movie.MovieDetails;
import com.rawrick.flicklist.data.room.moviedetails_db.MovieDetailsDAO;
import com.rawrick.flicklist.data.room.movies_db.MovieDAO;

@Database(entities = {Movie.class, MovieDetails.class}, version = 1, exportSchema = false)

public abstract class FLDatabase extends RoomDatabase {
    public abstract MovieDetailsDAO movieDetailsDAO();
    public abstract MovieDAO movieDAO();
}