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
import com.rawrick.flicklist.data.movie.MovieWatchlisted;
import com.rawrick.flicklist.data.room.FLDatabaseHelper;
import com.rawrick.flicklist.data.util.ActivitySelector;
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
    private ArrayList<MovieWatchlisted> moviesWatchlisted;
    private MovieManager movieManager;
    private int moviesWatchlistedPagesTotal;
    private int moviesWatchlistedPageCurrent;

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
        db = FLDatabaseHelper.getInstance(this.getActivity());
        moviesWatchlisted = (ArrayList<MovieWatchlisted>) db.getAllMoviesWatchlisted();
        sortDefault();
    }

    private void refreshData() {
        movieManager = new MovieManager(getActivity(), this, this, this);
        moviesWatchlistedPageCurrent = 2;
        movieManager.getWatchlistedMoviesFromAPI(1);
    }

    private void sortDefault() {
        Collections.sort(moviesWatchlisted, CompDefault);
    }

    Comparator<MovieWatchlisted> CompDefault = (M1, M2) -> {
        String T1 = M1.getTitle();
        String T2 = M2.getTitle();
        String t1 = ignoreArticles(T1);
        String t2 = ignoreArticles(T2);
        return t1.compareTo(t2);
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
        movieWatchlistAdapter.setWatchlistedMovies(moviesWatchlisted);

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
        activitySelector.startMovieActivity(moviesWatchlisted.get(position).getId());
    }

    @Override
    public void onWatchlistedMoviesUpdated() {
        // saves total pages that have to be fetched
        moviesWatchlistedPagesTotal = movieManager.getWatchlistedMoviesTotalPages();
        while (moviesWatchlistedPageCurrent <= moviesWatchlistedPagesTotal) {
            // changes page number on API request URL
            movieManager.getWatchlistedMoviesFromAPI(moviesWatchlistedPageCurrent);
            moviesWatchlistedPageCurrent++;
        }
        // sets adapter once all pages have been fetched
        if (moviesWatchlistedPageCurrent - 1 == moviesWatchlistedPagesTotal) {
            ArrayList<MovieWatchlisted> moviesFromAPI = movieManager.getWatchlistedMovies();
            for (MovieWatchlisted movieWatchlisted : moviesFromAPI) {
                db.addOrUpdateMovieWatchlisted(movieWatchlisted);
            }
            ArrayList<MovieWatchlisted> moviesFromDB = (ArrayList<MovieWatchlisted>) db.getAllMoviesWatchlisted();
            for (MovieWatchlisted movieWatchlistedFromDB : moviesFromDB) {
                if (!moviesFromAPI.contains(movieWatchlistedFromDB)) {
                    Log.d("dbwl", "moviesFromAPI contains " + movieWatchlistedFromDB.getTitle() + ": " + moviesFromAPI.contains(movieWatchlistedFromDB));
                    Log.d("dbwl", "deleted from db: " + movieWatchlistedFromDB.getTitle());
                    db.deleteMovieWatchlisted(movieWatchlistedFromDB);
                }
            }
            moviesWatchlisted = (ArrayList<MovieWatchlisted>) db.getAllMoviesWatchlisted();
            sortDefault();
            movieWatchlistAdapter.setWatchlistedMovies(moviesWatchlisted);
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onRatedMoviesUpdated() {
    }

    @Override
    public void onFavoritedMoviesUpdated() {
    }
}