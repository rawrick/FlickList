package com.rawrick.flicklist.data.api.movies;

import static com.rawrick.flicklist.data.api.account.AccountManager.getAccountID;
import static com.rawrick.flicklist.data.api.APIRequest.APIcurrentPageFavouritedMovies;
import static com.rawrick.flicklist.data.util.SettingsManager.getPreferenceAPIkey;
import static com.rawrick.flicklist.data.api.URL.accountURL;
import static com.rawrick.flicklist.data.api.URL.movieURL;
import static com.rawrick.flicklist.data.api.URL.trendingMoviesWeekURL;
import static com.rawrick.flicklist.data.api.APIRequest.APIaccountID;
import static com.rawrick.flicklist.data.api.APIRequest.APIcurrentPageWatchlistedMovies;
import static com.rawrick.flicklist.data.api.APIRequest.APIkey;
import static com.rawrick.flicklist.data.api.APIRequest.APImovieID;
import static com.rawrick.flicklist.data.api.APIRequest.APIcurrentPageRatedMovies;
import static com.rawrick.flicklist.data.api.APIRequest.APIsessionID;

import android.content.Context;
import android.util.Log;

import com.rawrick.flicklist.BuildConfig;
import com.rawrick.flicklist.data.credits.Cast;
import com.rawrick.flicklist.data.movie.Movie;
import com.rawrick.flicklist.data.movie.MovieFavorited;
import com.rawrick.flicklist.data.movie.MovieRated;
import com.rawrick.flicklist.data.movie.MovieTrending;
import com.rawrick.flicklist.data.movie.MovieWatchlisted;
import com.rawrick.flicklist.data.api.APIRequest;
import com.rawrick.flicklist.data.api.Parser;
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
    private Movie movieData;
    private ArrayList<Cast> movieCastData;

    public MovieProvider(Context context) {
        this.context = context;
        db = new FLDatabaseHelper(context);
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
        APIkey = getPreferenceAPIkey(context);
        if (!APIkey.equals(BuildConfig.ApiKey)) {
            APIkey = BuildConfig.ApiKey;
        }
        APIRequest request = new APIRequest(trendingMoviesWeekURL + "?api_key=" + APIkey, context);
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
                    ratedMovieData = (ArrayList<MovieRated>) db.getAllMoviesRated();
                    if (ratedMovieData.size() != 0) {
                        if (ratedMovieData.get(0).getPagesTotal() != 0) {
                            APIRequest.APImoviesRatedPageCurrent = ratedMovieData.get(0).getPagesTotal() + 1;
                        } else {
                            APIRequest.APImoviesRatedPageCurrent = 1; // TODO check what happens when user has no rated movies
                        }
                    }
                    Log.d("FlickListApp", "No Connection");
                }
            });
        } else {
            listener.onRatedMoviesDataAvailable(ratedMovieData);
        }
    }

    private void updateRatedMoviesData(APIRequest.ResponseListener listener) {
        APIkey = getPreferenceAPIkey(context);
        APIaccountID = getAccountID(context);
        APIRequest request = new APIRequest(accountURL + "/" + APIaccountID + "/rated/movies?api_key=" + APIkey + "&language=en-US&session_id=" + APIsessionID + "&sort_by=created_at.asc&page=" + APIcurrentPageRatedMovies, context);
        request.get(listener);
    }

    public interface RatedMoviesDataListener {
        void onRatedMoviesDataAvailable(ArrayList<MovieRated> data);
    }

    /**
     * Favorited Movies
     */

    public void getDataForFavoritedMovies(FavoritedMoviesDataListener listener) {
        if (favoritedMovieData == null) {
            updateFavoritedMoviesData(new APIRequest.ResponseListener() {
                @Override
                public void onResponse(JSONObject response) {
                    favoritedMovieData = Parser.parseFavouritedMoviesData(response);
                    listener.onFavoritedMoviesDataAvailable(favoritedMovieData);
                }

                @Override
                public void onError() {
                    favoritedMovieData = (ArrayList<MovieFavorited>) db.getAllMoviesFavorited();
                    if (favoritedMovieData.size() != 0) {
                        if (favoritedMovieData.get(0).getPagesTotal() != 0) {
                            APIRequest.APImoviesRatedPageCurrent = favoritedMovieData.get(0).getPagesTotal() + 1;
                        } else {
                            APIRequest.APImoviesRatedPageCurrent = 1; // TODO check what happens when user has no rated movies
                        }
                    }
                    Log.d("FlickListApp", "No Connection");
                }
            });
        } else {
            listener.onFavoritedMoviesDataAvailable(favoritedMovieData);
        }
    }

    private void updateFavoritedMoviesData(APIRequest.ResponseListener listener) {
        APIkey = getPreferenceAPIkey(context);
        APIaccountID = getAccountID(context);
        APIRequest request = new APIRequest(accountURL + "/" + APIaccountID + "/favorite/movies?api_key=" + APIkey + "&language=en-US&session_id=" + APIsessionID + "&sort_by=created_at.asc&page=" + APIcurrentPageFavouritedMovies, context);
        request.get(listener);
    }

    public interface FavoritedMoviesDataListener {
        void onFavoritedMoviesDataAvailable(ArrayList<MovieFavorited> data);
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
                    watchlistedMovieData = (ArrayList<MovieWatchlisted>) db.getAllMoviesWatchlisted();
                    if (watchlistedMovieData.size() != 0) {
                        if (watchlistedMovieData.get(0).getPagesTotal() != 0) {
                            APIRequest.APImoviesWatchlistedPageCurrent = watchlistedMovieData.get(0).getPagesTotal() + 1;
                        } else {
                            APIRequest.APImoviesWatchlistedPageCurrent = 1; // TODO check what happens when user has no watchlisted movies
                        }
                    }
                    Log.d("FlickListApp", "No Connection");
                }
            });
        } else {
            listener.onWatchlistedMoviesDataAvailable(watchlistedMovieData);
        }
    }

    private void updateWatchlistedMoviesData(APIRequest.ResponseListener listener) {
        APIkey = getPreferenceAPIkey(context);
        APIaccountID = getAccountID(context);
        APIRequest request = new APIRequest(accountURL + "/" + APIaccountID + "/watchlist/movies?api_key=" + APIkey + "&language=en-US&&session_id=" + APIsessionID + "&sort_by=created_at.asc&page=" + APIcurrentPageWatchlistedMovies, context);
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
        APIkey = getPreferenceAPIkey(context);
        APIaccountID = getAccountID(context);
        APIRequest request = new APIRequest(movieURL + APImovieID + "?api_key=" + APIkey + "&language=en-US", context);
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
        APIkey = getPreferenceAPIkey(context);
        APIaccountID = getAccountID(context);
        APIRequest request = new APIRequest(movieURL + APImovieID + "/credits?api_key=" + APIkey + "&language=en-US", context);
        request.get(listener);
    }

    public interface MovieCastDataListener {
        void onMovieCastDataAvailable(ArrayList<Cast> data);
    }
}