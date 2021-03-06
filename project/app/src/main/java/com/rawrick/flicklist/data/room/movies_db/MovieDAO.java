package com.rawrick.flicklist.data.room.movies_db;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.rawrick.flicklist.data.movie.Movie;
import com.rawrick.flicklist.data.movie.MovieRated;

import java.util.List;

@Dao
public interface MovieDAO {

    @Insert
    void addMovie(Movie movie);

    @Update
    void updateMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);

    @Query("SELECT * from movies WHERE id= :id")
    Movie getMovieForID(int id);

    @Query("SELECT EXISTS(SELECT * FROM movies WHERE id = :id)")
    boolean isEntryForID(int id);

    @Query("UPDATE movies SET userRating= :rating WHERE id = :id")
    void updateRatingForID(int id, float rating);

    @Query("UPDATE movies SET isFavourite= :favourite WHERE id = :id")
    void updateFavouriteStatusForID(int id, boolean favourite);

    @Query("UPDATE movies SET isWatchlisted= :watchlist WHERE id = :id")
    void updateWatchlistStatusForID(int id, boolean watchlist);

    @Query("SELECT * from movies WHERE userRating> :rating")
    List<Movie> getMoviesForRating(double rating);

    @Query("SELECT * from movies")
    List<Movie> getAllMovies();
}