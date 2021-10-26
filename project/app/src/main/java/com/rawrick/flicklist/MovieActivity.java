package com.rawrick.flicklist;

import static com.rawrick.flicklist.data.util.Formatter.runtimeFormatter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rawrick.flicklist.data.movie.Movie;
import com.rawrick.flicklist.data.util.MovieManager;

public class MovieActivity extends AppCompatActivity implements MovieManager.MovieDetailsManagerListener, MovieManager.TrendingMoviesManagerListener, MovieManager.RatedMoviesManagerListener, MovieManager.WatchlistedMoviesManagerListener {

    private MovieManager movieManager;

    private TextView movieTitle;
    private TextView movieReleaseYear;
    private TextView movieRuntime;
    private ImageView moviePoster;
    private ImageView movieBackdrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        initData();
        initUI();
    }

    private void initUI() {
        movieTitle = findViewById(R.id.movie_title);
        moviePoster = findViewById(R.id.movie_poster);
        movieBackdrop = findViewById(R.id.movie_backdrop);
        movieReleaseYear = findViewById(R.id.movie_release);
        movieRuntime = findViewById(R.id.movie_runtime);
    }

    private void initData() {
        movieManager = new MovieManager(this, this, this, this, this);
        movieManager.getMovieDetailsFromAPI();
    }


    @Override
    public void onMovieDetailsUpdated() {
        Movie movie = movieManager.getMovieDetails();
        movieTitle.setText(movie.getTitle());
        movieReleaseYear.setText(movie.getReleaseDate().substring(0, 4));
        movieRuntime.setText(runtimeFormatter(movie.getRuntime()));
        Glide.with(this)
                .load(movie.getPosterPath())
                .centerCrop()
                .into(moviePoster);
        Glide.with(this)
                .load(movie.getBackdropPath())
                .centerCrop()
                .into(movieBackdrop);
    }


    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    @Override
    public void onTrendingMoviesUpdated() {
    }

    @Override
    public void onRatedMoviesUpdated() {
    }

    @Override
    public void onWatchlistedMoviesUpdated() {

    }
}