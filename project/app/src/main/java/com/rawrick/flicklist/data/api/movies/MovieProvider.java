package com.rawrick.flicklist.data.api.movies;

import static com.rawrick.flicklist.data.api.URL.accountURL;
import static com.rawrick.flicklist.data.api.URL.movieURL;
import static com.rawrick.flicklist.data.api.URL.trendingMoviesWeekURL;
import static com.rawrick.flicklist.data.api.account.AccountManager.getAccountID;
import static com.rawrick.flicklist.data.util.SettingsManager.getPreferenceAPIkey;
import static com.rawrick.flicklist.data.util.SettingsManager.getSessionID;

import android.content.Context;
import android.util.Log;

import com.rawrick.flicklist.data.api.APIRequest;
import com.rawrick.flicklist.data.api.Parser;
import com.rawrick.flicklist.data.credits.Cast;
import com.rawrick.flicklist.data.movie.MovieDetails;
import com.rawrick.flicklist.data.movie.MovieFavorited;
import com.rawrick.flicklist.data.movie.MovieRated;
import com.rawrick.flicklist.data.movie.MovieTrending;
import com.rawrick.flicklist.data.movie.MovieWatchlisted;
import com.rawrick.flicklist.data.room.FLDatabaseHelper;

import org.json.JSONObject;

import java.util.ArrayList;

public class MovieProvider {


    private final Context context;

    private FLDatabaseHelper db;

    private ArrayList<MovieTrending> trendingMovieData;
    private ArrayList<MovieRated> ratedMovieData;
    private ArrayList<MovieFavorited> favoritedMovieData;
    private ArrayList<MovieWatchlisted> watchlistedMovieData;
    private MovieDetails movieDetailsData;
    private ArrayList<Cast> movieCastData;

    public MovieProvider(Context context) {
        this.context = context;
        db = new FLDatabaseHelper(context);
    }

    /**
     * Trending Movies
     */

    public void getDataForMoviesTrending(DataListener listener) {
        if (trendingMovieData == null) {
            updateMoviesTrendingData(new APIRequest.ResponseListener() {
                @Override
                public void onResponse(JSONObject response) {
                    trendingMovieData = Parser.parseTrendingMovies(response);
                    listener.onTrendingMovieDataAvailable(trendingMovieData);
                }

                @Override
                public void onError() {
                    Log.d("FlickListApp", "No Connection");
                }
            });
        } else {
            listener.onTrendingMovieDataAvailable(trendingMovieData);
        }
    }

    private void updateMoviesTrendingData(APIRequest.ResponseListener listener) {
        APIRequest request = new APIRequest(trendingMoviesWeekURL + "?api_key=" + getPreferenceAPIkey(context), context);
        request.get(listener);
    }

    public interface DataListener {
        void onTrendingMovieDataAvailable(ArrayList<MovieTrending> data);
    }

    /**
     * Rated Movies
     */

    public void getDataForRatedMovies(int currentPage, RatedMoviesDataListener listener) {
        if (ratedMovieData == null) {
            updateRatedMoviesData(currentPage, new APIRequest.ResponseListener() {
                @Override
                public void onResponse(JSONObject response) {
                    ratedMovieData = Parser.parseRatedMoviesData(response);
                    listener.onRatedMoviesDataAvailable(ratedMovieData);
                }

                @Override
                public void onError() {
                    ratedMovieData = (ArrayList<MovieRated>) db.getAllMoviesRated();
                    listener.onRatedMoviesDataAvailable(ratedMovieData);
                    Log.d("FlickListApp", "No Connection");
                }
            });
        } else {
            listener.onRatedMoviesDataAvailable(ratedMovieData);
        }
    }

    private void updateRatedMoviesData(int currentPage, APIRequest.ResponseListener listener) {
        APIRequest request = new APIRequest(accountURL + "/" + getAccountID(context)
                + "/rated/movies?api_key=" + getPreferenceAPIkey(context)
                + "&language=en-US&session_id=" + getSessionID(context)
                + "&sort_by=created_at.asc&page=" + currentPage, context);
        request.get(listener);
    }

    public interface RatedMoviesDataListener {
        void onRatedMoviesDataAvailable(ArrayList<MovieRated> data);
    }

    /**
     * Favorited Movies
     */

    public void getDataForFavoritedMovies(int currentPage, FavoritedMoviesDataListener listener) {
        if (favoritedMovieData == null) {
            updateFavoritedMoviesData(currentPage, new APIRequest.ResponseListener() {
                @Override
                public void onResponse(JSONObject response) {
                    favoritedMovieData = Parser.parseFavouritedMoviesData(response);
                    listener.onFavoritedMoviesDataAvailable(favoritedMovieData);
                }

                @Override
                public void onError() {
                    favoritedMovieData = (ArrayList<MovieFavorited>) db.getAllMoviesFavorited();
                    listener.onFavoritedMoviesDataAvailable(favoritedMovieData);
                    Log.d("FlickListApp", "No Connection");
                }
            });
        } else {
            listener.onFavoritedMoviesDataAvailable(favoritedMovieData);
        }
    }

    private void updateFavoritedMoviesData(int currentPage, APIRequest.ResponseListener listener) {
        APIRequest request = new APIRequest(accountURL + "/" + getAccountID(context)
                + "/favorite/movies?api_key=" + getPreferenceAPIkey(context)
                + "&language=en-US&session_id=" + getSessionID(context)
                + "&sort_by=created_at.asc&page=" + currentPage, context);
        request.get(listener);
    }

    public interface FavoritedMoviesDataListener {
        void onFavoritedMoviesDataAvailable(ArrayList<MovieFavorited> data);
    }

    /**
     * Watchlisted Movies
     */

    public void getDataForWatchlistedMovies(int currentPage, WatchlistedMoviesDataListener listener) {
        if (watchlistedMovieData == null) {
            updateWatchlistedMoviesData(currentPage, new APIRequest.ResponseListener() {
                @Override
                public void onResponse(JSONObject response) {
                    watchlistedMovieData = Parser.parseWatchlistedMoviesData(response);
                    listener.onWatchlistedMoviesDataAvailable(watchlistedMovieData);
                }

                @Override
                public void onError() {
                    watchlistedMovieData = (ArrayList<MovieWatchlisted>) db.getAllMoviesWatchlisted();
                    listener.onWatchlistedMoviesDataAvailable(watchlistedMovieData);
                    Log.d("FlickListApp", "No Connection");
                }
            });
        } else {
            listener.onWatchlistedMoviesDataAvailable(watchlistedMovieData);
        }
    }

    private void updateWatchlistedMoviesData(int currentPage, APIRequest.ResponseListener listener) {
        APIRequest request = new APIRequest(accountURL + "/" + getAccountID(context)
                + "/watchlist/movies?api_key=" + getPreferenceAPIkey(context)
                + "&language=en-US&&session_id=" + getSessionID(context)
                + "&sort_by=created_at.asc&page=" + currentPage, context);
        request.get(listener);
    }

    public interface WatchlistedMoviesDataListener {
        void onWatchlistedMoviesDataAvailable(ArrayList<MovieWatchlisted> data);
    }

    /**
     * MOVIE DETAILS
     **/

    public void getDataForMovie(int id, MovieDataListener listener) {
        if (movieDetailsData == null) {
            updateMovieData(id, new APIRequest.ResponseListener() {
                @Override
                public void onResponse(JSONObject response) {
                    movieDetailsData = Parser.parseMovieData(response);
                    listener.onMovieDataAvailable(movieDetailsData);
                }

                @Override
                public void onError() {
                    Log.d("FlickListApp", "No Connection");
                }
            });
        } else {
            listener.onMovieDataAvailable(movieDetailsData);
        }
    }

    private void updateMovieData(int id, APIRequest.ResponseListener listener) {
        APIRequest request = new APIRequest(movieURL + id
                + "?api_key=" + getPreferenceAPIkey(context)
                + "&language=en-US", context);
        request.get(listener);
    }

    public interface MovieDataListener {
        void onMovieDataAvailable(MovieDetails data);
    }

    /**
     * MOVIE CREDITS
     **/

    public void getCastForMovie(int id, MovieCastDataListener listener) {
        if (movieCastData == null) {
            updateMovieCastData(id, new APIRequest.ResponseListener() {
                @Override
                public void onResponse(JSONObject response) {
                    movieCastData = Parser.parseMovieCastData(response);
                    listener.onMovieCastDataAvailable(movieCastData);
                }

                @Override
                public void onError() {
                    Log.d("FlickListApp", "No Connection");
                }
            });
        } else {
            listener.onMovieCastDataAvailable(movieCastData);
        }
    }

    private void updateMovieCastData(int id, APIRequest.ResponseListener listener) {
        APIRequest request = new APIRequest(movieURL + id
                + "/credits?api_key=" + getPreferenceAPIkey(context)
                + "&language=en-US", context);
        request.get(listener);
    }

    public interface MovieCastDataListener {
        void onMovieCastDataAvailable(ArrayList<Cast> data);
    }
}