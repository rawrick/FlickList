package com.rawrick.flicklist.data.room.moviedetails_db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.rawrick.flicklist.data.movie.Movie;
import com.rawrick.flicklist.data.movie.MovieRated;

import java.util.List;

@Dao
public interface MovieDetailsDAO {

    @Insert
    void addMovie(Movie movie);

    @Update
    void updateMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);

    @Query("SELECT * from moviedetails WHERE id= :id")
    Movie getMovieForID(int id);

    @Query("UPDATE moviedetails SET userRating=:rating WHERE id= :id")
    void updateRating(int id, Float rating);

    @Query("UPDATE moviedetails SET userFavoriteStatus=:isFavorite WHERE id= :id")
    void updateFavoriteStatus(int id, boolean isFavorite);

    @Query("DELETE FROM moviedetails")
    void delete();
}