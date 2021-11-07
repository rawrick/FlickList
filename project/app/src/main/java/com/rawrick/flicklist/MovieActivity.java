package com.rawrick.flicklist;

import static com.rawrick.flicklist.data.util.Formatter.runtimeFormatter;
import static com.rawrick.flicklist.data.util.RatingValidator.isRatingValid;

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
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.rawrick.flicklist.data.api.account.FavoritesManager;
import com.rawrick.flicklist.data.api.account.MediaType;
import com.rawrick.flicklist.data.api.account.RatingManager;
import com.rawrick.flicklist.data.api.account.WatchlistManager;
import com.rawrick.flicklist.data.movie.Movie;
import com.rawrick.flicklist.data.room.FLDatabaseHelper;
import com.rawrick.flicklist.ui.moviedetails.MovieAboutFragment;
import com.rawrick.flicklist.ui.moviedetails.MovieCastFragment;

public class MovieActivity extends FragmentActivity {

    private FLDatabaseHelper db;
    private RatingManager ratingManager;
    private FavoritesManager favoritesManager;
    private WatchlistManager watchlistManager;
    private Movie movie;
    private int movieID;

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
    private String ratingFromDBString;

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
        Bundle bundle = getIntent().getBundleExtra("movie");
        movie = bundle.getParcelable("movie");
        movieID = movie.getId();
        db = FLDatabaseHelper.getInstance(this);
        ratingManager = new RatingManager(this);
        favoritesManager = new FavoritesManager(this);
        watchlistManager = new WatchlistManager(this);

        if (db.isMovieInDBForID(movieID)) {
            if (db.getMovieForID(movieID).getUserRating() != -1) {
                isMovieRated = true;
            }
            if (db.getMovieForID(movieID).isWatchlisted()) {
                isMovieWatchlisted = true;
            }
            if (db.getMovieForID(movieID).isFavourite()) {
                isMovieFavorited = true;
            }
        }
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
                showRatingDialog(movie);
            }
        });
        if (isMovieFavorited) {
            setFABIconColor(movieFavoriteFAB, R.color.fabIsFavorited);
        }
        movieFavoriteFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isMovieFavorited) {
                    favoritesManager.postFavoriteStatus(MediaType.MOVIE, movieID, true);
                    if (db.isMovieInDBForID(movieID)) {
                        db.updateMovieFavouriteStatus(movieID, true);
                    } else {
                        db.addOrUpdateMovie(new Movie(movieID, movie.getTitle(), movie.getTitleOriginal(), movie.getOverview(), movie.getReleaseDate(),
                                movie.isAdult(), movie.getLanguage(), movie.getPopularity(), movie.getVoteAverage(), movie.getPosterPath(),
                                movie.getBackdropPath(), -1, true, false, null, 0));
                    }
                    setFABIconColor(movieFavoriteFAB, R.color.fabIsFavorited);
                    isMovieFavorited = true;
                } else if (isMovieWatchlisted || isMovieRated) {
                    favoritesManager.postFavoriteStatus(MediaType.MOVIE, movieID, false);
                    db.updateMovieFavouriteStatus(movieID, false);
                    setFABIconColor(movieFavoriteFAB, R.color.textDefaultDark);
                    isMovieFavorited = false;
                } else {
                    favoritesManager.postFavoriteStatus(MediaType.MOVIE, movieID, false);
                    db.deleteMovie(movie);
                    setFABIconColor(movieFavoriteFAB, R.color.textDefaultDark);
                    isMovieFavorited = false;
                }
            }
        });
        if (isMovieWatchlisted) {
            movieWatchlistFAB.setImageTintList(getResources().getColorStateList(R.color.fabIsWatchlisted, this.getTheme()));
        }
        movieWatchlistFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isMovieWatchlisted) {
                    watchlistManager.postWatchlistStatus(MediaType.MOVIE, movieID, true);
                    if (db.isMovieInDBForID(movieID)) {
                        db.updateMovieWatchlistStatus(movieID, true);
                    } else {
                        db.addOrUpdateMovie(new Movie(movieID, movie.getTitle(), movie.getTitleOriginal(), movie.getOverview(), movie.getReleaseDate(),
                                movie.isAdult(), movie.getLanguage(), movie.getPopularity(), movie.getVoteAverage(), movie.getPosterPath(),
                                movie.getBackdropPath(), -1, false, true, null, 0));
                    }
                    setFABIconColor(movieWatchlistFAB, R.color.fabIsWatchlisted);
                    isMovieWatchlisted = true;
                } else if (isMovieFavorited) {
                    watchlistManager.postWatchlistStatus(MediaType.MOVIE, movieID, false);
                    db.updateMovieWatchlistStatus(movieID, false);
                    setFABIconColor(movieWatchlistFAB, R.color.textDefaultDark);
                    isMovieWatchlisted = false;
                } else {
                    watchlistManager.postWatchlistStatus(MediaType.MOVIE, movieID, false);
                    db.deleteMovie(movie);
                    setFABIconColor(movieWatchlistFAB, R.color.textDefaultDark);
                    isMovieWatchlisted = false;
                }
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

    private void showRatingDialog(Movie movie) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View customLayout = getLayoutInflater().inflate(R.layout.rating_dialog, null);
        ratingEditText = customLayout.findViewById(R.id.rating_input);
        if (isMovieRated) {
            ratingFromDB = db.getMovieForID(movieID).getUserRating();
            ratingFromDBString = String.valueOf(ratingFromDB);
            if (ratingFromDBString.endsWith(".0")) {
                ratingFromDBString = ratingFromDBString.substring(0, ratingFromDBString.length() - 2);
            }
            db.updateMovieRating(movie.getId(), ratingFromDB);
            ratingEditText.setText(ratingFromDBString, TextView.BufferType.EDITABLE);
        }
        builder.setView(customLayout)
                .setTitle(" ")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        float rating = Float.parseFloat(ratingEditText.getText().toString());
                        if (isRatingValid(rating)) {
                            ratingManager.postRating(movieID, rating);
                            if (db.isMovieInDBForID(movieID)) {
                                db.updateMovieRating(movieID, rating);
                            } else {
                                db.addOrUpdateMovie(new Movie(movieID, movie.getTitle(), movie.getTitleOriginal(), movie.getOverview(), movie.getReleaseDate(),
                                        movie.isAdult(), movie.getLanguage(), movie.getPopularity(), movie.getVoteAverage(), movie.getPosterPath(),
                                        movie.getBackdropPath(), rating, movie.isFavourite(), movie.isWatchlisted(), null, 0));
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid rating. Must be between 0.5 and 10.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNeutralButton("Delete Rating", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ratingManager.deleteRating(movieID);
                        if (isMovieFavorited) {
                            db.updateMovieRating(movieID, -1);
                        } else {
                            db.deleteMovie(movie);
                        }
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
            // about
            MovieAboutFragment movieAboutFragment = new MovieAboutFragment();
            Bundle bundleAbout = new Bundle();
            bundleAbout.putParcelable("movie", movie);
            movieAboutFragment.setArguments(bundleAbout);
            // cast
            MovieCastFragment movieCastFragment = new MovieCastFragment();
            Bundle bundleCast = new Bundle();
            bundleCast.putInt("id", movieID);
            movieCastFragment.setArguments(bundleCast);
            switch (position) {
                case 0:
                    return movieAboutFragment;
                case 1:

                    return movieAboutFragment;
                case 2:
                    return movieAboutFragment;
                case 3:
                    return movieAboutFragment;
                case 4:
                    return movieAboutFragment;
                default:
                    return movieAboutFragment;
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