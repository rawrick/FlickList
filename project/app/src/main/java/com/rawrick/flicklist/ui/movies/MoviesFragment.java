package com.rawrick.flicklist.ui.movies;

import static androidx.recyclerview.widget.RecyclerView.VERTICAL;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.rawrick.flicklist.R;
import com.rawrick.flicklist.data.api.movies.MovieManager;
import com.rawrick.flicklist.data.movie.MovieFavorited;
import com.rawrick.flicklist.data.movie.MovieRated;
import com.rawrick.flicklist.data.room.FLDatabaseHelper;
import com.rawrick.flicklist.data.util.ActivitySelector;
import com.rawrick.flicklist.databinding.FragmentMoviesBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MoviesFragment extends Fragment implements MovieListItemViewHolder.ViewHolderListener,
        MovieManager.RatedMoviesManagerListener,
        MovieManager.WatchlistedMoviesManagerListener,
        MovieManager.FavoritedMoviesManagerListener {

    private FragmentMoviesBinding binding;

    private FLDatabaseHelper db;
    private MovieManager movieManager;
    private ActivitySelector activitySelector;
    private ArrayList<MovieRated> moviesRated;
    private ArrayList<MovieFavorited> moviesFavourited;

    private int moviesFavouritedPageCurrent;


    private SwipeRefreshLayout swipeRefreshLayout;

    private RecyclerView recyclerRatedMovies;
    public MovieListAdapter movieListAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMoviesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initData();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initUI(view);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * DATA
     */

    private void initData() {
        activitySelector = new ActivitySelector(getActivity());
        db = FLDatabaseHelper.getInstance(this.getActivity());
        moviesFavourited = (ArrayList<MovieFavorited>) db.getAllMoviesFavorited();
        for (MovieFavorited movie : moviesFavourited) {
            if (db.isMovieRatedForID(movie.getId())) {
                db.updateRatedMovieFavoriteStatus(db.getMovieRatedForID(movie.getId()), true);
            }
        }
        moviesRated = (ArrayList<MovieRated>) db.getAllMoviesRated();
        sortDefault();
    }

    private void refreshData() {
        movieManager = new MovieManager(getActivity(), this, this, this);
        movieManager.getRatedMoviesFromAPI(1);
        movieManager.getFavoritedMoviesFromAPI(1);
    }

    private void sortDefault() {
        Collections.sort(moviesRated, CompDefault);
    }

    Comparator<MovieRated> CompDefault = (M1, M2) -> {
        double R1 = M1.getRating();
        double R2 = M2.getRating();
        String T1 = M1.getTitle();
        String T2 = M2.getTitle();
        String t1 = ignoreArticles(T1);
        String t2 = ignoreArticles(T2);

        if (R1 > R2) {
            return -1;
        }
        if (R1 < R2) {
            return 1;
        }
        if (R1 == R2) {
            return t1.compareTo(t2);
        }
        return 0;
    };

    private String ignoreArticles(String input) {
        if (input.startsWith("The ")) {
            return input.substring(4);
        } else if (input.startsWith("A ")) {
            return input.substring(2);
        } else {
            return input;
        }
    }

    /**
     * UI
     */

    private void initUI(View view) {
        final int spacing = getResources().getDimensionPixelSize(R.dimen.recycler_list_spacing) / 2;
        recyclerRatedMovies = view.findViewById(R.id.movie_list);
        recyclerRatedMovies.setLayoutManager(new LinearLayoutManager(getActivity(), VERTICAL, false));
        recyclerRatedMovies.setPadding(spacing, spacing, spacing, spacing);
        recyclerRatedMovies.setClipToPadding(false);
        recyclerRatedMovies.setClipChildren(false);
        recyclerRatedMovies.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.set(spacing, spacing, spacing, spacing);
            }
        });
        movieListAdapter = new MovieListAdapter(getActivity(), this);
        recyclerRatedMovies.setAdapter(movieListAdapter);
        movieListAdapter.setRatedMovies(moviesRated);

        swipeRefreshLayout = view.findViewById(R.id.movies_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
    }

    @Override
    public void onMovieListItemClicked(int position) {
        // go to movie detail activity
        activitySelector.startMovieActivity(moviesRated.get(position).getId());
    }

    @Override
    public void onRatedMoviesUpdated(int currentPage) {
        // saves total pages that have to be fetched
        int moviesRatedPagesTotal = movieManager.getRatedMoviesTotalPages();
        // sends more requests if there are more than 1 result pages
        if (currentPage < moviesRatedPagesTotal) {
            // changes page number on API request URL
            movieManager.getRatedMoviesFromAPI(currentPage + 1);
        }
        // save to db once all pages have been fetched
        if (currentPage == moviesRatedPagesTotal) {
            Log.d("updateDebug", "all rated movies have been fetched");
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
            movieManager.getFavoritedMoviesFromAPI(1);
        }
    }

    @Override
    public void onFavoritedMoviesUpdated(int currentPage) {
        // saves total pages that have to be fetched
        int moviesFavouritedPagesTotal = movieManager.getFavoritedMoviesTotalPages();
        // sends more requests if there are more than 1 result pages
        if (currentPage < moviesFavouritedPagesTotal) {
            // changes page number on API request URL
            movieManager.getFavoritedMoviesFromAPI(moviesFavouritedPageCurrent);
        }
        // save to db once all pages have been fetched
        if (currentPage == moviesFavouritedPagesTotal) {
            Log.d("updateDebug", "all favourited movies have been fetched");
            ArrayList<MovieFavorited> moviesFromAPI = movieManager.getFavoritedMovies();
            for (MovieFavorited movieFavorited : moviesFromAPI) {
                db.addOrUpdateMovieFavorited(movieFavorited);
            }
            ArrayList<MovieFavorited> moviesFromDB = (ArrayList<MovieFavorited>) db.getAllMoviesFavorited();
            for (MovieFavorited movieFavoritedFromDB : moviesFromDB) {
                if (!moviesFromAPI.contains(movieFavoritedFromDB)) {
                    Log.d("updateDebug", "rated: moviesFromAPI contains " + movieFavoritedFromDB.getId() + ": " + moviesFromAPI.contains(movieFavoritedFromDB));
                    Log.d("updateDebug", "rated: deleted from db: " + movieFavoritedFromDB.getId());
                    db.deleteMovieFavorited(movieFavoritedFromDB);
                }
            }
            moviesFavourited = (ArrayList<MovieFavorited>) db.getAllMoviesFavorited();
            for (MovieFavorited movie : moviesFavourited) {
                if (db.isMovieRatedForID(movie.getId())) {
                    Log.d("updateDebug", "marking as favourite: " + db.getMovieRatedForID(movie.getId()).getTitle());
                    db.updateRatedMovieFavoriteStatus(db.getMovieRatedForID(movie.getId()), true);
                }
            }
            moviesRated = (ArrayList<MovieRated>) db.getAllMoviesRated();
            sortDefault();
            movieListAdapter.setRatedMovies(moviesRated);
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onWatchlistedMoviesUpdated(int currentPage) {
    }
}