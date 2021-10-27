package com.rawrick.flicklist;

import static com.rawrick.flicklist.data.util.APIRequest.movieID;
import static com.rawrick.flicklist.data.util.Formatter.runtimeFormatter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.rawrick.flicklist.ui.movies.MoviesFragment;
import com.rawrick.flicklist.ui.profile.ProfileFragment;
import com.rawrick.flicklist.ui.series.SeriesFragment;
import com.rawrick.flicklist.ui.watchlist.WatchlistFragment;

public class MovieActivity extends FragmentActivity implements MovieManager.MovieDetailsManagerListener, MovieManager.TrendingMoviesManagerListener, MovieManager.RatedMoviesManagerListener, MovieManager.WatchlistedMoviesManagerListener {

    private MovieManager movieManager;
    private RatingManager ratingManager;

    private TextView movieTitle;
    private TextView movieReleaseYear;
    private TextView movieRuntime;
    private ImageView moviePoster;
    private ImageView movieBackdrop;

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

    private void initUI() {
        initializeNavigation();
        movieTitle = findViewById(R.id.movie_title);
        moviePoster = findViewById(R.id.movie_poster);
        movieBackdrop = findViewById(R.id.movie_backdrop);
        movieReleaseYear = findViewById(R.id.movie_release);
        movieRuntime = findViewById(R.id.movie_runtime);

    }

    private void initData() {
        movieManager = new MovieManager(this, this, this, this, this);
        movieManager.getMovieDetailsFromAPI();
        ratingManager = new RatingManager(this);
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
                    return new MovieAboutFragment();
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