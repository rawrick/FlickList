package com.rawrick.flicklist.data.room.moviesrated_db;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.rawrick.flicklist.data.movie.MovieRated;

import java.util.List;

@Dao
public interface MovieRatedDAO {

    @Insert
    void addMovie(MovieRated movieRated);

    @Update
    void updateMovie(MovieRated movieRated);

    @Delete
    void deleteMovie(MovieRated movieRated);

    @Query("SELECT * from moviesRated WHERE id= :id")
    MovieRated getMovieForID(int id);

    @Query("SELECT EXISTS(SELECT * FROM moviesRated WHERE id = :id)")
    boolean isRated(int id);

    @Query("SELECT * from moviesRated WHERE rating> :rating")
    List<MovieRated> getMoviesForRating(double rating);

    @Query("SELECT * from moviesRated")
    List<MovieRated> getAllMoviesRated();

}