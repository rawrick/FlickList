package com.rawrick.flicklist.data.room.moviedetails_db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.rawrick.flicklist.data.movie.MovieDetails;

@Dao
public interface MovieDetailsDAO {

    @Insert
    void addMovie(MovieDetails movieDetails);

    @Update
    void updateMovie(MovieDetails movieDetails);

    @Delete
    void deleteMovie(MovieDetails movieDetails);

    @Query("SELECT * from MovieDetails WHERE id= :id")
    MovieDetails getMovieForID(int id);

    @Query("UPDATE MovieDetails SET userRating=:rating WHERE id= :id")
    void updateRating(int id, Float rating);

    @Query("UPDATE MovieDetails SET userFavoriteStatus=:isFavorite WHERE id= :id")
    void updateFavoriteStatus(int id, boolean isFavorite);

    @Query("DELETE FROM MovieDetails")
    void delete();
}