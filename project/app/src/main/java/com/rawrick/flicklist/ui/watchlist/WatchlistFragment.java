package com.rawrick.flicklist.ui.watchlist;

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
import com.rawrick.flicklist.data.movie.Movie;
import com.rawrick.flicklist.data.movie.MovieFavorited;
import com.rawrick.flicklist.data.movie.MovieRated;
import com.rawrick.flicklist.data.movie.MovieWatchlisted;
import com.rawrick.flicklist.data.room.FLDatabaseHelper;
import com.rawrick.flicklist.data.util.ActivitySelector;
import com.rawrick.flicklist.data.util.MediaComposer;
import com.rawrick.flicklist.data.util.MediaSorter;
import com.rawrick.flicklist.databinding.FragmentWatchlistBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class WatchlistFragment extends Fragment implements MovieWatchlistItemViewHolder.ViewHolderListener,
        MovieManager.RatedMoviesManagerListener,
        MovieManager.WatchlistedMoviesManagerListener,
        MovieManager.FavoritedMoviesManagerListener {

    private FragmentWatchlistBinding binding;

    private FLDatabaseHelper db;
    private ActivitySelector activitySelector;
    private MediaSorter mediaSorter;

    private MovieManager movieManager;
    private ArrayList<Movie> movies;
    private ArrayList<MovieRated> ratingData;
    private ArrayList<MovieFavorited> favoritesData;
    private ArrayList<MovieWatchlisted> watchlistData;
    private int loadingProgress;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerWatchlistedMovies;
    public MovieWatchlistAdapter movieWatchlistAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWatchlistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initData();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
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
        mediaSorter = new MediaSorter();
        db = FLDatabaseHelper.getInstance(this.getActivity());
        movies = (ArrayList<Movie>) db.getAllMovies();
        movies.removeIf(T -> T.getUserRating() != -1);
        movies = mediaSorter.sortMoviesByTitle(movies);
    }

    private void refreshData() {
        loadingProgress = 0;
        movieManager = new MovieManager(getActivity(), this, this, this);
        movieManager.getAllMovieDataFromAPI();
    }

    private void updateList(int value) {
        if (value == 3) {
            movies = MediaComposer.composeMovie(ratingData, favoritesData, watchlistData);
            db.cleanDB(movies);
            movies.removeIf(T -> T.getUserRating() != -1);
            movies = mediaSorter.sortMoviesByTitle(movies);
            movieWatchlistAdapter.setWatchlistedMovies(movies);
            swipeRefreshLayout.setRefreshing(false);
            loadingProgress = 0;
        }
    }

    /**
     * UI
     */

    private void initUI(View view) {
        // initialize rated movies adapter
        final int spacing = getResources().getDimensionPixelSize(R.dimen.recycler_list_spacing) / 2;
        recyclerWatchlistedMovies = view.findViewById(R.id.watchlist_movies);
        recyclerWatchlistedMovies.setLayoutManager(new LinearLayoutManager(getActivity(), VERTICAL, false));
        recyclerWatchlistedMovies.setPadding(spacing, spacing, spacing, spacing);
        recyclerWatchlistedMovies.setClipToPadding(false);
        recyclerWatchlistedMovies.setClipChildren(false);
        recyclerWatchlistedMovies.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.set(spacing, spacing, spacing, spacing);
            }
        });
        movieWatchlistAdapter = new MovieWatchlistAdapter(getActivity(), this);
        recyclerWatchlistedMovies.setAdapter(movieWatchlistAdapter);
        movieWatchlistAdapter.setWatchlistedMovies(movies);

        swipeRefreshLayout = view.findViewById(R.id.watchlist_movies_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
    }

    @Override
    public void onMovieWatchlistItemClicked(int position) {
        // go to movie detail activity
        activitySelector.startMovieActivity(movies.get(position).getId());
    }

    @Override
    public void onRatedMoviesUpdated() {
        ratingData = movieManager.getRatedMovies();
        loadingProgress++;
        updateList(loadingProgress);
    }

    @Override
    public void onFavoritedMoviesUpdated() {
        favoritesData = movieManager.getFavoritedMovies();
        loadingProgress++;
        updateList(loadingProgress);
    }

    @Override
    public void onWatchlistedMoviesUpdated() {
        watchlistData = movieManager.getWatchlistedMovies();
        loadingProgress++;
        updateList(loadingProgress);
    }
}