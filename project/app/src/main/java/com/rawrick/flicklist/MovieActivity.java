package com.rawrick.flicklist;

import static com.rawrick.flicklist.data.tools.SettingsManager.getTempRating;
import static com.rawrick.flicklist.data.util.APIRequest.movieID;
import static com.rawrick.flicklist.data.util.Formatter.runtimeFormatter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.rawrick.flicklist.data.movie.Movie;
import com.rawrick.flicklist.data.util.MovieManager;
import com.rawrick.flicklist.data.util.rating.RatingManager;
import com.rawrick.flicklist.ui.home.HomeFragment;
import com.rawrick.flicklist.ui.moviedetails.MovieAboutFragment;
import com.rawrick.flicklist.ui.moviedetails.MovieCastFragment;
import com.rawrick.flicklist.ui.movies.MoviesFragment;
import com.rawrick.flicklist.ui.profile.ProfileFragment;
import com.rawrick.flicklist.ui.series.SeriesFragment;
import com.rawrick.flicklist.ui.watchlist.WatchlistFragment;

public class MovieActivity extends FragmentActivity implements MovieManager.MovieDetailsManagerListener, MovieManager.TrendingMoviesManagerListener, MovieManager.RatedMoviesManagerListener, MovieManager.WatchlistedMoviesManagerListener, MovieManager.MovieCastManagerListener {

    private MovieManager movieManager;
    private RatingManager ratingManager;

    private TextView movieTitle;
    private TextView movieReleaseYear;
    private TextView movieRuntime;
    private ImageView moviePoster;
    private ImageView movieBackdrop;
    private FloatingActionButton movieRateFAB;

    private static final int NUM_PAGES = 5;
    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;
    private final ViewPager2.OnPageChangeCallback pageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        initData();
        initUI();
    }

    private void initData() {
        movieManager = new MovieManager(this, this, this, this, this, this);
        movieManager.getMovieDetailsFromAPI();
        ratingManager = new RatingManager(this);
    }

    private void initUI() {
        initializeNavigation();
        movieTitle = findViewById(R.id.movie_title);
        moviePoster = findViewById(R.id.movie_poster);
        movieBackdrop = findViewById(R.id.movie_backdrop);
        movieReleaseYear = findViewById(R.id.movie_release);
        movieRuntime = findViewById(R.id.movie_runtime);
        movieRateFAB = findViewById(R.id.movie_rate_fab);
        movieRateFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRatingDialog();
            }
        });
    }

    private void showRatingDialog() {
        float tempRating = getTempRating(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View customLayout = getLayoutInflater().inflate(R.layout.rating_dialog, null);
        builder.setView(customLayout)
                .setTitle("")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RatingBar ratingBar = customLayout.findViewById(R.id.rating_bar);
                        ratingBar.setRating(tempRating);
                        makeToast(String.valueOf(ratingBar.getRating()));
                    }
                })
                .setNeutralButton("Delete Rating", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // delete rating
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // cancel
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void makeToast(String data) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onMovieDetailsUpdated() {
        Movie movie = movieManager.getMovieDetails();
        movieID = String.valueOf(movie.getId());
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

    private void initializeNavigation() {
        // Instantiate a ViewPager2 and a PagerAdapter.
        viewPager = findViewById(R.id.movie_details_pager);
        pagerAdapter = new MovieActivity.ScreenSlidePagerAdapter(this);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(pagerAdapter);
        //viewPager.registerOnPageChangeCallback(pageChangeCallback);
        TabLayout tabLayout = findViewById(R.id.movie_tab_layout);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.variation1BackgroundDark));
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    if (position == 0) tab.setText("About");
                    else if (position == 1) tab.setText("Cast");
                    else tab.setText("default");
                }
        ).attach();
    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new MovieAboutFragment();
                case 1:
                    return new MovieCastFragment();
                case 2:
                    return new MovieAboutFragment();
                case 3:
                    return new MovieAboutFragment();
                case 4:
                    return new MovieAboutFragment();
                default:
                    return new MovieAboutFragment();
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
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

    @Override
    public void onMovieCastUpdated() {

    }
}