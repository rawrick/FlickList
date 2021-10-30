package com.rawrick.flicklist;

import static com.rawrick.flicklist.data.account.AccountManager.setAccountID;
import static com.rawrick.flicklist.data.account.AccountManager.setAccountName;
import static com.rawrick.flicklist.data.api.APIRequest.currentPageWatchlistedMovies;
import static com.rawrick.flicklist.data.util.SettingsManager.getLoginStatus;
import static com.rawrick.flicklist.data.api.APIRequest.currentPageRatedMovies;

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
import com.rawrick.flicklist.data.movie.MovieRated;
import com.rawrick.flicklist.data.movie.MovieWatchlisted;
import com.rawrick.flicklist.data.room.FLDatabaseHelper;
import com.rawrick.flicklist.data.api.movies.MovieManager;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

public class SplashScreenActivity extends AppCompatActivity implements
        AccountManager.AccountManagerListener,
        MovieManager.RatedMoviesManagerListener,
        MovieManager.WatchlistedMoviesManagerListener {

    private FLDatabaseHelper db;

    private AccountManager accountManager;
    private MovieManager movieManager;
    private SeriesManager seriesManager;
    private Intent intent;

    private int currentLoadingProgress = 0;
    private final int totalLoadingProgress = 2;
    public static int x = 2;
    public static int y = 2;
    private int moviesRatedPagesTotal;
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
        movieManager = new MovieManager(this, this, this);
        // rated
        currentPageRatedMovies = "1";
        movieManager.getRatedMoviesFromAPI();
        // watchlisted
        currentPageWatchlistedMovies = "1";
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
        while (x <= moviesRatedPagesTotal) {
            // changes page number on API request URL
            currentPageRatedMovies = String.valueOf(x);
            movieManager.getRatedMoviesFromAPI();
            x++;
        }
        // save to db once all pages have been fetched
        if (x - 1 == moviesRatedPagesTotal) {
            ArrayList<MovieRated> movies = movieManager.getRatedMovies();
            for (MovieRated movieRated : movies) {
                db.addOrUpdateMovieRated(movieRated);
            }
            currentLoadingProgress++;
        }
        startMainActivity();
    }

    @Override
    public void onWatchlistedMoviesUpdated() {
        // saves total pages that have to be fetched
        moviesWatchlistedPagesTotal = movieManager.getWatchlistedMovies().get(0).getPagesTotal();
        while (y <= moviesWatchlistedPagesTotal) {
            // changes page number on API request URL
            currentPageWatchlistedMovies = String.valueOf(y);
            movieManager.getRatedMoviesFromAPI();
            y++;
        }
        // sets adapter once all pages have been fetched
        if (y - 1 == moviesWatchlistedPagesTotal) {
            ArrayList<MovieWatchlisted> movies = movieManager.getWatchlistedMovies();
            for (MovieWatchlisted movieWatchlisted : movies) {
                db.addOrUpdateMovieWatchlisted(movieWatchlisted);
            }
            currentLoadingProgress++;
        }
        startMainActivity();
    }
}