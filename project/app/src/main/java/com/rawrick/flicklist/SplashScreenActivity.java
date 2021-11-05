package com.rawrick.flicklist;

import static com.rawrick.flicklist.data.api.account.AccountManager.setAccountAvatar;
import static com.rawrick.flicklist.data.api.account.AccountManager.setAccountID;
import static com.rawrick.flicklist.data.api.account.AccountManager.setAccountName;
import static com.rawrick.flicklist.data.util.SettingsManager.getLoginStatus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.rawrick.flicklist.data.api.account.AccountManager;
import com.rawrick.flicklist.data.api.movies.MovieManager;
import com.rawrick.flicklist.data.api.series.SeriesManager;
import com.rawrick.flicklist.data.movie.MovieFavorited;
import com.rawrick.flicklist.data.movie.MovieRated;
import com.rawrick.flicklist.data.movie.MovieWatchlisted;
import com.rawrick.flicklist.data.room.FLDatabaseHelper;

import java.util.ArrayList;

public class SplashScreenActivity extends AppCompatActivity implements
        AccountManager.AccountManagerListener,
        MovieManager.RatedMoviesManagerListener,
        MovieManager.WatchlistedMoviesManagerListener, MovieManager.FavoritedMoviesManagerListener {

    private FLDatabaseHelper db;

    private AccountManager accountManager;
    private MovieManager movieManager;
    private SeriesManager seriesManager;
    private Intent intent;

    private int currentLoadingProgress = 0;
    private final int totalLoadingProgress = 3;

    private int moviesRatedPagesTotal;
    private int moviesFavoritedPagesTotal;
    private int moviesWatchlistedPagesTotal;
    private int moviesRatedPageCurrent;
    private int moviesFavoritedPageCurrent;
    private int moviesWatchlistedPageCurrent;

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  ui
        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        // data
        if (getLoginStatus(this.getApplicationContext())) {
            if (isNetworkConnected()) {
                initData();
            } else {
                intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        } else {
            intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    private void startMainActivity() {
        if (currentLoadingProgress == totalLoadingProgress) {
            Log.d("FlickListApp", "Starting MainActivity...");
            intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void initData() {
        db = FLDatabaseHelper.getInstance(this);
        db.clearMovieDetailsTable();
        moviesRatedPageCurrent = 2;
        moviesFavoritedPageCurrent = 2;
        moviesWatchlistedPageCurrent = 2;
        accountManager = new AccountManager(this, this);
        accountManager.getAccountDataFromAPI();
    }

    private void initMovieData() {
        movieManager = new MovieManager(this, this, this, this);
        // rated
        movieManager.getRatedMoviesFromAPI(1);
        // favorited
        movieManager.getFavoritedMoviesFromAPI(1);
        // watchlisted
        movieManager.getWatchlistedMoviesFromAPI(1);
    }

    private void initSeriesData() {
    }

    @Override
    public void onAccountDataUpdated() {
        String id = accountManager.getAccountData().getId();
        String name = accountManager.getAccountData().getName();
        String avatarPath = accountManager.getAccountData().getAvatar();
        setAccountID(this.getApplicationContext(), id);
        setAccountName(this.getApplicationContext(), name);
        setAccountAvatar(this.getApplicationContext(), avatarPath);
        Log.d("FlickListApp", "Account Data Updated");
        initMovieData();
        initSeriesData();
    }

    @Override
    public void onRatedMoviesUpdated() {
        // saves total pages that have to be fetched
        moviesRatedPagesTotal = movieManager.getRatedMoviesTotalPages();
        while (moviesRatedPageCurrent <= moviesRatedPagesTotal) {
            // changes page number on API request URL
            movieManager.getRatedMoviesFromAPI(moviesRatedPageCurrent);
            moviesRatedPageCurrent++;
        }
        // save to db once all pages have been fetched
        if (moviesRatedPageCurrent - 1 == moviesRatedPagesTotal) {
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
        }
        startMainActivity();
    }

    @Override
    public void onFavoritedMoviesUpdated() {
        // saves total pages that have to be fetched
        moviesFavoritedPagesTotal = movieManager.getFavoritedMoviesTotalPages();
        while (moviesFavoritedPageCurrent <= moviesFavoritedPagesTotal) {
            // changes page number on API request URL
            movieManager.getFavoritedMoviesFromAPI(moviesFavoritedPageCurrent);
            moviesFavoritedPageCurrent++;
        }
        // save to db once all pages have been fetched
        if (moviesFavoritedPageCurrent - 1 == moviesFavoritedPagesTotal) {
            ArrayList<MovieFavorited> moviesFromAPI = movieManager.getFavoritedMovies();
            for (MovieFavorited movieFavorited : moviesFromAPI) {
                db.addOrUpdateMovieFavorited(movieFavorited);
            }
            ArrayList<MovieFavorited> moviesFromDB = (ArrayList<MovieFavorited>) db.getAllMoviesFavorited();
            for (MovieFavorited movieFavoritedFromDB : moviesFromDB) {
                if (!moviesFromAPI.contains(movieFavoritedFromDB)) {
                    Log.d("dbwl", "favorites: moviesFromAPI contains " + movieFavoritedFromDB.getId() + ": " + moviesFromAPI.contains(movieFavoritedFromDB));
                    Log.d("dbwl", "favorites: deleted from db: " + movieFavoritedFromDB.getId());
                    db.deleteMovieFavorited(movieFavoritedFromDB);
                }
            }
            currentLoadingProgress++;
        }
        startMainActivity();
    }

    @Override
    public void onWatchlistedMoviesUpdated() {
        // saves total pages that have to be fetched
        moviesWatchlistedPagesTotal = movieManager.getWatchlistedMoviesTotalPages();
        while (moviesWatchlistedPageCurrent <= moviesWatchlistedPagesTotal) {
            // changes page number on API request URL
            movieManager.getWatchlistedMoviesFromAPI(moviesWatchlistedPageCurrent);
            moviesWatchlistedPageCurrent++;
        }
        // sets adapter once all pages have been fetched
        if (moviesWatchlistedPageCurrent - 1 == moviesWatchlistedPagesTotal) {
            ArrayList<MovieWatchlisted> moviesFromAPI = movieManager.getWatchlistedMovies();
            for (MovieWatchlisted movieWatchlisted : moviesFromAPI) {
                db.addOrUpdateMovieWatchlisted(movieWatchlisted);
            }
            ArrayList<MovieWatchlisted> moviesFromDB = (ArrayList<MovieWatchlisted>) db.getAllMoviesWatchlisted();
            for (MovieWatchlisted movieWatchlistedFromDB : moviesFromDB) {
                if (!moviesFromAPI.contains(movieWatchlistedFromDB)) {
                    Log.d("dbwl", "watchlist: moviesFromAPI contains " + movieWatchlistedFromDB.getTitle() + ": " + moviesFromAPI.contains(movieWatchlistedFromDB));
                    Log.d("dbwl", "watchlist: deleted from db: " + movieWatchlistedFromDB.getTitle());
                    db.deleteMovieWatchlisted(movieWatchlistedFromDB);
                }
            }
            currentLoadingProgress++;
        }
        startMainActivity();
    }
}