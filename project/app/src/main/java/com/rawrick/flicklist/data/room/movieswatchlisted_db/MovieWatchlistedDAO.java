package com.rawrick.flicklist.data.room.movieswatchlisted_db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.rawrick.flicklist.data.movie.MovieWatchlisted;

import java.util.List;

@Dao
public interface MovieWatchlistedDAO {

    @Insert
    void addMovie(MovieWatchlisted moviesWatchlisted);

    @Update
    void updateMovie(MovieWatchlisted moviesWatchlisted);

    @Delete
    void deleteMovie(MovieWatchlisted moviesWatchlisted);


    @Query("SELECT * from moviesWatchlisted WHERE id= :id")
    MovieWatchlisted getMovieForID(int id);

    @Query("SELECT EXISTS(SELECT * FROM movieswatchlisted WHERE id = :id)")
    boolean isWatchlisted(int id);

    @Query("SELECT * from moviesWatchlisted")
    List<MovieWatchlisted> getAllMoviesWatchlisted();
}