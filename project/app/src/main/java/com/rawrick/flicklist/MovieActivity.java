package com.rawrick.flicklist;

import static com.rawrick.flicklist.data.api.APIRequest.APImovieID;
import static com.rawrick.flicklist.data.util.Formatter.runtimeFormatter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.rawrick.flicklist.data.movie.Movie;
import com.rawrick.flicklist.data.api.rating.RatingManager;
import com.rawrick.flicklist.data.movie.MovieFavorited;
import com.rawrick.flicklist.data.room.FLDatabaseHelper;
import com.rawrick.flicklist.ui.moviedetails.MovieAboutFragment;
import com.rawrick.flicklist.ui.moviedetails.MovieCastFragment;

public class MovieActivity extends FragmentActivity {

    private FLDatabaseHelper db;
    private Movie movie;
    private RatingManager ratingManager;

    private TextView movieTitle;
    private TextView movieReleaseYear;
    private TextView movieRuntime;
    private ImageView moviePoster;
    private ImageView movieBackdrop;

    private boolean isMovieRated;
    private boolean isMovieFavorited;
    private boolean isMovieWatchlisted;

    private FloatingActionButton movieRateFAB;
    private FloatingActionButton movieFavoriteFAB;
    private FloatingActionButton movieWatchlistFAB;

    private TextView ratingEditText;
    private float ratingFromDB = 0f;

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
        db = new FLDatabaseHelper(this);
        movie = db.getMovieDetailsForID(Integer.parseInt(APImovieID));
        isMovieRated = db.isMovieRatedForID(movie.getId());
        isMovieFavorited = db.isMovieFavoritedForID(movie.getId());
        isMovieWatchlisted = db.isMovieWatchlistedForID(movie.getId());
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
        movieFavoriteFAB = findViewById(R.id.movie_favourite_fab);
        movieWatchlistFAB = findViewById(R.id.movie_watchlist_fab);

        if (isMovieRated) {
            setFABIconColor(movieRateFAB, R.color.fabIsRated);
        }
        movieRateFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRatingDialog();
            }
        });
        if (isMovieFavorited) {
            setFABIconColor(movieFavoriteFAB, R.color.fabIsFavorited);
        }
        movieFavoriteFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMovieFavorited) {
                    setFABIconColor(movieFavoriteFAB, R.color.textDefaultDark);
                    db.updateMovieFavoriteStatus(movie, false);
                    isMovieFavorited = false;
                } else {
                    setFABIconColor(movieFavoriteFAB, R.color.fabIsFavorited);
                    db.updateMovieFavoriteStatus(movie, true);
                    isMovieFavorited = true;
                }
            }
        });
        if (isMovieWatchlisted) {
            movieWatchlistFAB.setImageTintList(getResources().getColorStateList(R.color.fabIsWatchlisted, this.getTheme()));
        }
        movieWatchlistFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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

    private void showRatingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View customLayout = getLayoutInflater().inflate(R.layout.rating_dialog, null);
        ratingEditText = customLayout.findViewById(R.id.rating_input);
        if (isMovieRated) {
            ratingFromDB = db.getMovieRatedForID(Integer.parseInt(APImovieID)).getRating();
            db.updateMovieRating(movie, ratingFromDB);
            ratingEditText.setText(String.valueOf((int) ratingFromDB), TextView.BufferType.EDITABLE);
        }
        builder.setView(customLayout)
                .setTitle(" ")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        float rating = Float.parseFloat(ratingEditText.getText().toString());
                        ratingManager.postRating(rating);
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

    private void setFABIconColor(FloatingActionButton fab, int colorID) {
        fab.setImageTintList(getResources().getColorStateList(colorID, this.getTheme()));
    }

    private void initializeNavigation() {
        // Instantiate a ViewPager2 and a PagerAdapter.
        viewPager = findViewById(R.id.movie_details_pager);
        pagerAdapter = new MovieActivity.ScreenSlidePagerAdapter(this);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(pagerAdapter);
        //viewPager.registerOnPageChangeCallback(pageChangeCallback);
        TabLayout tabLayout = findViewById(R.id.movie_tab_layout);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.backgroundVariationDark));
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    if (position == 0) tab.setText("About");
                    else if (position == 1) tab.setText("Cast");
                    else if (position == 2) tab.setText("Reviews");
                    else if (position == 3) tab.setText("Media");
                    else if (position == 4) tab.setText("Recommendations");
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
}