package com.rawrick.flicklist;

import static com.rawrick.flicklist.data.account.AccountManager.setAccountID;
import static com.rawrick.flicklist.data.account.AccountManager.setAccountName;
import static com.rawrick.flicklist.data.api.APIRequest.APIcurrentPageFavouritedMovies;
import static com.rawrick.flicklist.data.api.APIRequest.APIcurrentPageWatchlistedMovies;
import static com.rawrick.flicklist.data.util.SettingsManager.getLoginStatus;
import static com.rawrick.flicklist.data.api.APIRequest.APIcurrentPageRatedMovies;

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

import com.rawrick.flicklist.data.account.AccountManager;
import com.rawrick.flicklist.data.api.series.SeriesManager;
import com.rawrick.flicklist.data.movie.MovieFavorited;
import com.rawrick.flicklist.data.movie.MovieRated;
import com.rawrick.flicklist.data.movie.MovieWatchlisted;
import com.rawrick.flicklist.data.room.FLDatabaseHelper;
import com.rawrick.flicklist.data.api.movies.MovieManager;

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
    public static int moviesRatedPageCurrent = 2;
    public static int moviesWatchlistedPageCurrent = 2;
    public static int moviesFavoritedPageCurrent = 2;
    private int moviesRatedPagesTotal;
    private int moviesFavoritedPagesTotal;
    private int moviesWatchlistedPagesTotal;

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
        db = new FLDatabaseHelper(getApplicationContext());
        db.clearMovieDetailsTable();
        accountManager = new AccountManager(this, this);
        accountManager.getAccountDataFromAPI();
    }

    private void initMovieData() {
        movieManager = new MovieManager(this, this, this, this);
        // rated
        APIcurrentPageRatedMovies = "1";
        movieManager.getRatedMoviesFromAPI();
        // favorited
        APIcurrentPageFavouritedMovies = "1";
        movieManager.getFavoritedMoviesFromAPI();
        // watchlisted
        APIcurrentPageWatchlistedMovies = "1";
        movieManager.getWatchlistedMoviesFromAPI();
    }

    private void initSeriesData() {
    }

    @Override
    public void onAccountDataUpdated() {
        String id = accountManager.getAccountData().getId();
        String name = accountManager.getAccountData().getName();
        setAccountID(this.getApplicationContext(), id);
        setAccountName(this.getApplicationContext(), name);
        Log.d("FlickListApp", "Account Data Updated");
        initMovieData();
        initSeriesData();
    }

    @Override
    public void onRatedMoviesUpdated() {
        // saves total pages that have to be fetched
        moviesRatedPagesTotal = movieManager.getRatedMovies().get(0).getPagesTotal();
        while (moviesRatedPageCurrent <= moviesRatedPagesTotal) {
            // changes page number on API request URL
            APIcurrentPageRatedMovies = String.valueOf(moviesRatedPageCurrent);
            movieManager.getRatedMoviesFromAPI();
            moviesRatedPageCurrent++;
        }
        // save to db once all pages have been fetched
        if (moviesRatedPageCurrent - 1 == moviesRatedPagesTotal) {
            ArrayList<MovieRated> movies = movieManager.getRatedMovies();
            for (MovieRated movieRated : movies) {
                db.addOrUpdateMovieRated(movieRated);
            }
            currentLoadingProgress++;
        }
        startMainActivity();
    }

    @Override
    public void onFavoritedMoviesUpdated() {
        // saves total pages that have to be fetched
        moviesFavoritedPagesTotal = movieManager.getFavoritedMovies().get(0).getPagesTotal();
        while (moviesFavoritedPageCurrent <= moviesFavoritedPagesTotal) {
            // changes page number on API request URL
            APIcurrentPageFavouritedMovies = String.valueOf(moviesFavoritedPageCurrent);
            movieManager.getFavoritedMoviesFromAPI();
            moviesFavoritedPageCurrent++;
        }
        // save to db once all pages have been fetched
        if (moviesFavoritedPageCurrent - 1 == moviesFavoritedPagesTotal) {
            ArrayList<MovieFavorited> movies = movieManager.getFavoritedMovies();
            for (MovieFavorited movieFavorited : movies) {
                db.addOrUpdateMovieFavorited(movieFavorited);
            }
            currentLoadingProgress++;
        }
        startMainActivity();
    }

    @Override
    public void onWatchlistedMoviesUpdated() {
        // saves total pages that have to be fetched
        moviesWatchlistedPagesTotal = movieManager.getWatchlistedMovies().get(0).getPagesTotal();
        while (moviesWatchlistedPageCurrent <= moviesWatchlistedPagesTotal) {
            // changes page number on API request URL
            APIcurrentPageWatchlistedMovies = String.valueOf(moviesWatchlistedPageCurrent);
            movieManager.getRatedMoviesFromAPI();
            moviesWatchlistedPageCurrent++;
        }
        // sets adapter once all pages have been fetched
        if (moviesWatchlistedPageCurrent - 1 == moviesWatchlistedPagesTotal) {
            ArrayList<MovieWatchlisted> movies = movieManager.getWatchlistedMovies();
            for (MovieWatchlisted movieWatchlisted : movies) {
                db.addOrUpdateMovieWatchlisted(movieWatchlisted);
            }
            currentLoadingProgress++;
        }
        startMainActivity();
    }
}