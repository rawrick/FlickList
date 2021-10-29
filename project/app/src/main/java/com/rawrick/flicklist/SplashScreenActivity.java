package com.rawrick.flicklist;

import static com.rawrick.flicklist.data.tools.SettingsManager.getLoginStatus;
import static com.rawrick.flicklist.data.tools.SettingsManager.setAccountID;
import static com.rawrick.flicklist.data.util.APIRequest.currentPageRatedMovies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.rawrick.flicklist.data.account.AccountManager;
import com.rawrick.flicklist.data.movie.MovieRated;
import com.rawrick.flicklist.data.room.FLDatabaseHelper;
import com.rawrick.flicklist.data.util.MovieManager;

import java.util.ArrayList;

public class SplashScreenActivity extends AppCompatActivity implements AccountManager.AccountManagerListener, MovieManager.MovieDetailsManagerListener, MovieManager.TrendingMoviesManagerListener, MovieManager.RatedMoviesManagerListener, MovieManager.WatchlistedMoviesManagerListener, MovieManager.MovieCastManagerListener {

    private FLDatabaseHelper db;

    private AccountManager accountManager;
    private MovieManager movieManager;
    private Intent intent;


    private int i = 2;
    private int pagesTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  ui
        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        // data
        if (getLoginStatus(this.getApplicationContext())) {
            initData();
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


    private void initData() {
        initDB(getApplicationContext());
        initAccountData();

    }

    private void initDB(Context context) {
        db = new FLDatabaseHelper(context);
    }

    private void initAccountData() {
        accountManager = new AccountManager(this, this);
        accountManager.getAccountDataFromAPI();
    }

    private void initRatedMoviesData() {
        movieManager = new MovieManager(this, this, this, this, this, this);
        currentPageRatedMovies = "1";
        movieManager.getRatedMoviesFromAPI();
    }

    @Override
    public void onAccountDataUpdated() {
        String id = accountManager.getAccountData().getId();
        setAccountID(this.getApplicationContext(), id);
        initRatedMoviesData();
    }

    @Override
    public void onTrendingMoviesUpdated() {

    }

    @Override
    public void onRatedMoviesUpdated() {
        // saves total pages that have to be fetched
        pagesTotal = movieManager.getRatedMovies().get(0).getPagesTotal();
        while (i <= pagesTotal) {
            // changes page number on API request URL
            currentPageRatedMovies = String.valueOf(i);
            movieManager.getRatedMoviesFromAPI();
            i++;
        }
        // save to db once all pages have been fetched
        if (i - 1 == pagesTotal) {
            ArrayList<MovieRated> movies = movieManager.getRatedMovies();
            for (MovieRated movieRated : movies ) {
                db.addOrUpdateMovieRated(movieRated);
            }
            startActivty();
        }


    }

    @Override
    public void onWatchlistedMoviesUpdated() {

    }

    @Override
    public void onMovieDetailsUpdated() {

    }

    @Override
    public void onMovieCastUpdated() {

    }


    private void startActivty() {
        intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        intent.putExtra("name", accountManager.getAccountData().getName());
        intent.putExtra("avatar", accountManager.getAccountData().getAvatar());
        startActivity(intent);
        finish();
    }
    // start new activity
    /*

     */
}