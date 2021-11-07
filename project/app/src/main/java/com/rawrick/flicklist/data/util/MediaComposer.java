package com.rawrick.flicklist.data.util;

import com.rawrick.flicklist.data.movie.Movie;
import com.rawrick.flicklist.data.movie.MovieFavorited;
import com.rawrick.flicklist.data.movie.MovieRated;
import com.rawrick.flicklist.data.movie.MovieWatchlisted;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MediaComposer {

    public static ArrayList<Movie> composeMovie(ArrayList<MovieRated> ratingData, ArrayList<MovieFavorited> favouritesData, ArrayList<MovieWatchlisted> watchlistData) {
        ArrayList<Movie> movies = new ArrayList<>(); // output ArrayList
        Map<Integer, MovieFavorited> favouritesMap = new HashMap<>();
        for (MovieFavorited movieFavorited : favouritesData) {
            favouritesMap.put(movieFavorited.getId(), movieFavorited);
        }
        // adds rated movies
        for (MovieRated movieRated : ratingData) {
            boolean isFavourite;
            if (favouritesMap.get(movieRated.getId()) != null) {
                isFavourite = true;
            } else {
                isFavourite = false;
            }
            Movie movie = new Movie(movieRated.getId(), movieRated.getTitle(), movieRated.getTitle(), movieRated.getOverview(),
                    movieRated.getReleaseDate(), movieRated.isAdult(), movieRated.getLanguage(), movieRated.getPopularity(),
                    movieRated.getVoteAverage(), movieRated.getPosterPath(), movieRated.getBackdropPath(), movieRated.getUserRating(),
                    isFavourite, false, null, 0);
            movies.add(movie);
        }
        // adds watchlisted movies
        for (MovieWatchlisted movieWatchlisted : watchlistData) {
            boolean isFavourite;
            if (favouritesMap.get(movieWatchlisted.getId()) != null) {
                isFavourite = true;
            } else {
                isFavourite = false;
            }
            Movie movie = new Movie(movieWatchlisted.getId(), movieWatchlisted.getTitle(), movieWatchlisted.getTitle(), movieWatchlisted.getOverview(),
                    movieWatchlisted.getReleaseDate(), movieWatchlisted.isAdult(), movieWatchlisted.getLanguage(), movieWatchlisted.getPopularity(),
                    movieWatchlisted.getVoteAverage(), movieWatchlisted.getPosterPath(), movieWatchlisted.getBackdropPath(), -1,
                    isFavourite, true, null, 0);
            movies.add(movie);
        }
        return movies;
    }
}

/*
    @Override
    public void onRatedMoviesUpdated(int currentPage) {
        // saves total pages that have to be fetched
        int moviesRatedPagesTotal = movieManager.getRatedMoviesTotalPages();
        if (currentPage < moviesRatedPagesTotal) {
            // changes page number on API request URL
            movieManager.getRatedMoviesFromAPI(currentPage + 1);
        }
        // save to db once all pages have been fetched
        if (currentPage == moviesRatedPagesTotal) {
            ArrayList<MovieRated> moviesFromAPI = movieManager.getRatedMovies();
            for (MovieRated movieRated : moviesFromAPI) {
                db.addOrUpdateMovieRated(movieRated);
            }
            ArrayList<MovieRated> moviesFromDB = (ArrayList<MovieRated>) db.getAllMoviesRated();
            for (MovieRated movieRatedFromDB : moviesFromDB) {
                if (!moviesFromAPI.contains(movieRatedFromDB)) {
                    Log.d("dbwl", "rated: moviesFromAPI contains " + movieRatedFromDB.getId() + ": " + moviesFromAPI.contains(movieRatedFromDB));
                    Log.d("dbwl", "rated: deleted from db: " + movieRatedFromDB.getId());
                    db.deleteMovieRated(movieRatedFromDB);
                }
            }
            currentLoadingProgress++;
            startMainActivity();
        }
    }
 */