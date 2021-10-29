package com.rawrick.flicklist.data.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.rawrick.flicklist.data.movie.MovieRated;
import com.rawrick.flicklist.data.room.moviesrated_db.MovieRatedDAO;

@Database(entities = {MovieRated.class,}, version = 1, exportSchema = false)

//@TypeConverters({HabitAttributeTypeConverter.class})

public abstract class FLDatabase extends RoomDatabase {
    public abstract MovieRatedDAO movieRatedDAO();
}