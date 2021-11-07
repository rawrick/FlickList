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
import com.rawrick.flicklist.data.movie.Movie;
import com.rawrick.flicklist.data.movie.MovieFavorited;
import com.rawrick.flicklist.data.movie.MovieRated;
import com.rawrick.flicklist.data.movie.MovieWatchlisted;
import com.rawrick.flicklist.data.room.FLDatabaseHelper;
import com.rawrick.flicklist.data.util.MediaComposer;

import java.util.ArrayList;

public class SplashScreenActivity extends AppCompatActivity implements
        AccountManager.AccountManagerListener,
        MovieManager.RatedMoviesManagerListener,
        MovieManager.WatchlistedMoviesManagerListener, MovieManager.FavoritedMoviesManagerListener {

    private FLDatabaseHelper db;

    private AccountManager accountManager;
    private MovieManager movieManager;
    private ArrayList<Movie> movies;
    private ArrayList<MovieRated> ratingData;
    private ArrayList<MovieFavorited> favoritesData;
    private ArrayList<MovieWatchlisted> watchlistData;
    private SeriesManager seriesManager;
    private Intent intent;

    private int currentLoadingProgress = 0;
    private final int totalLoadingProgress = 3;

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
            movies = MediaComposer.composeMovie(ratingData, favoritesData, watchlistData);
            for (Movie movie : movies) {
                db.addOrUpdateMovie(movie);
            }
            Log.d("FlickListApp", "Starting MainActivity...");
            intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void initData() {
        db = FLDatabaseHelper.getInstance(this);
        accountManager = new AccountManager(this, this);
        accountManager.getAccountDataFromAPI();
    }

    private void initMovieData() {
        movieManager = new MovieManager(this, this, this, this);
        movieManager.getAllMovieDataFromAPI();
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
        ratingData = movieManager.getRatedMovies();
        currentLoadingProgress++;
        startMainActivity();
    }

    @Override
    public void onFavoritedMoviesUpdated() {
        favoritesData = movieManager.getFavoritedMovies();
        currentLoadingProgress++;
        startMainActivity();
    }

    @Override
    public void onWatchlistedMoviesUpdated() {
        watchlistData = movieManager.getWatchlistedMovies();
        currentLoadingProgress++;
        startMainActivity();
    }
}