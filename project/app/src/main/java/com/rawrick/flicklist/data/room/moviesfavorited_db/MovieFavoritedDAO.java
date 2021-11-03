package com.rawrick.flicklist.data.room.moviesfavorited_db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.rawrick.flicklist.data.movie.MovieFavorited;

import java.util.List;

@Dao
public interface MovieFavoritedDAO {

    @Insert
    void addMovie(MovieFavorited movieRated);

    @Update
    void updateMovie(MovieFavorited movieRated);

    @Delete
    void deleteMovie(MovieFavorited movieRated);

    @Query("SELECT * from moviesFavorited WHERE id= :id")
    MovieFavorited getMovieForID(int id);

    @Query("SELECT EXISTS(SELECT * FROM moviesFavorited WHERE id = :id)")
    boolean isFavorited(int id);

    @Query("SELECT * from moviesFavorited")
    List<MovieFavorited> getAllMovies();

}
