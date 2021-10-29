package com.rawrick.flicklist.data.util.api.movies;

import static com.rawrick.flicklist.data.util.SettingsManager.getAccountID;
import static com.rawrick.flicklist.data.util.SettingsManager.getPreferenceAPIkey;
import static com.rawrick.flicklist.data.util.api.URL.accountURL;
import static com.rawrick.flicklist.data.util.api.URL.movieURL;
import static com.rawrick.flicklist.data.util.api.URL.trendingMoviesWeekURL;
import static com.rawrick.flicklist.data.util.api.APIRequest.accountID;
import static com.rawrick.flicklist.data.util.api.APIRequest.currentPageWatchlistedMovies;
import static com.rawrick.flicklist.data.util.api.APIRequest.key;
import static com.rawrick.flicklist.data.util.api.APIRequest.movieID;
import static com.rawrick.flicklist.data.util.api.APIRequest.currentPageRatedMovies;
import static com.rawrick.flicklist.data.util.api.APIRequest.sessionID;

import android.content.Context;
import android.util.Log;

import com.rawrick.flicklist.BuildConfig;
import com.rawrick.flicklist.data.credits.Cast;
import com.rawrick.flicklist.data.movie.Movie;
import com.rawrick.flicklist.data.movie.MovieRated;
import com.rawrick.flicklist.data.movie.MovieTrending;
import com.rawrick.flicklist.data.movie.MovieWatchlisted;
import com.rawrick.flicklist.data.util.api.APIRequest;
import com.rawrick.flicklist.data.util.api.Parser;

import org.json.JSONObject;

import java.util.ArrayList;

public class MovieProvider {


    private final Context context;

    private ArrayList<MovieTrending> trendingMovieData;
    private ArrayList<MovieRated> ratedMovieData;
    private ArrayList<MovieWatchlisted> watchlistedMovieData;
    private Movie movieData;
    private ArrayList<Cast> movieCastData;

    public MovieProvider(Context context) {
        this.context = context;
    }

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
        key = getPreferenceAPIkey(context);
        if (!key.equals(BuildConfig.ApiKey)) {
            key = BuildConfig.ApiKey;
        }
        APIRequest request = new APIRequest(trendingMoviesWeekURL + "?api_key=" + key, context);
        request.get(listener);
    }

    public interface DataListener {
        void onTrendingMovieDataAvailable(ArrayList<MovieTrending> data);
    }

    /**
     * Rated Movies
     */

    public void getDataForRatedMovies(RatedMoviesDataListener listener) {
        if (ratedMovieData == null) {
            updateRatedMoviesData(new APIRequest.ResponseListener() {
                @Override
                public void onResponse(JSONObject response) {
                    ratedMovieData = Parser.parseRatedMoviesData(response);
                    listener.onRatedMoviesDataAvailable(ratedMovieData);
                }

                @Override
                public void onError() {
                    Log.d("FlickListApp", "No Connection");
                }
            });
        } else {
            listener.onRatedMoviesDataAvailable(ratedMovieData);
        }
    }

    private void updateRatedMoviesData(APIRequest.ResponseListener listener) {
        key = getPreferenceAPIkey(context);
        if (!key.equals(BuildConfig.ApiKey)) {
            key = BuildConfig.ApiKey;
        }
        accountID = getAccountID(context);
        APIRequest request = new APIRequest(accountURL + "/" + accountID + "/rated/movies?api_key=" + key + "&language=en-US&&session_id=" + sessionID + "&sort_by=created_at.asc&page=" + currentPageRatedMovies, context);
        request.get(listener);
    }

    public interface RatedMoviesDataListener {
        void onRatedMoviesDataAvailable(ArrayList<MovieRated> data);
    }

    /**
     * Watchlisted Movies
     */

    public void getDataForWatchlistedMovies(WatchlistedMoviesDataListener listener) {
        if (watchlistedMovieData == null) {
            updateWatchlistedMoviesData(new APIRequest.ResponseListener() {
                @Override
                public void onResponse(JSONObject response) {
                    watchlistedMovieData = Parser.parseWatchlistedMoviesData(response);
                    listener.onWatchlistedMoviesDataAvailable(watchlistedMovieData);
                }

                @Override
                public void onError() {
                    Log.d("FlickListApp", "No Connection");
                }
            });
        } else {
            listener.onWatchlistedMoviesDataAvailable(watchlistedMovieData);
        }
    }

    private void updateWatchlistedMoviesData(APIRequest.ResponseListener listener) {
        key = getPreferenceAPIkey(context);
        if (!key.equals(BuildConfig.ApiKey)) {
            key = BuildConfig.ApiKey;
        }
        accountID = getAccountID(context);
        APIRequest request = new APIRequest(accountURL + "/" + accountID + "/watchlist/movies?api_key=" + key + "&language=en-US&&session_id=" + sessionID + "&sort_by=created_at.asc&page=" + currentPageWatchlistedMovies, context);
        request.get(listener);
    }

    public interface WatchlistedMoviesDataListener {
        void onWatchlistedMoviesDataAvailable(ArrayList<MovieWatchlisted> data);
    }

    /**
     * MOVIE DETAILS
     **/

    public void getDataForMovie(MovieDataListener listener) {
        if (movieData == null) {
            updateMovieData(new APIRequest.ResponseListener() {
                @Override
                public void onResponse(JSONObject response) {
                    movieData = Parser.parseMovieData(response);
                    listener.onMovieDataAvailable(movieData);
                }

                @Override
                public void onError() {
                    Log.d("FlickListApp", "No Connection");
                }
            });
        } else {
            listener.onMovieDataAvailable(movieData);
        }
    }

    private void updateMovieData(APIRequest.ResponseListener listener) {
        key = getPreferenceAPIkey(context);
        if (!key.equals(BuildConfig.ApiKey)) {
            key = BuildConfig.ApiKey;
        }
        accountID = getAccountID(context);
        APIRequest request = new APIRequest(movieURL + movieID + "?api_key=" + key + "&language=en-US", context);
        request.get(listener);
    }

    public interface MovieDataListener {
        void onMovieDataAvailable(Movie data);
    }

    /**
     * MOVIE CREDITS
     **/

    public void getCastForMovie(MovieCastDataListener listener) {
        if (movieCastData == null) {
            updateMovieCastData(new APIRequest.ResponseListener() {
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

    private void updateMovieCastData(APIRequest.ResponseListener listener) {
        key = getPreferenceAPIkey(context);
        if (!key.equals(BuildConfig.ApiKey)) {
            key = BuildConfig.ApiKey;
        }
        accountID = getAccountID(context);
        APIRequest request = new APIRequest(movieURL + movieID + "/credits?api_key=" + key + "&language=en-US", context);
        request.get(listener);
    }

    public interface MovieCastDataListener {
        void onMovieCastDataAvailable(ArrayList<Cast> data);
    }
}