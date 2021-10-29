package com.rawrick.flicklist.ui.watchlist;

import static androidx.recyclerview.widget.RecyclerView.VERTICAL;
import static com.rawrick.flicklist.data.api.APIRequest.currentPageWatchlistedMovies;
import static com.rawrick.flicklist.data.api.APIRequest.movieID;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.rawrick.flicklist.MovieActivity;
import com.rawrick.flicklist.R;
import com.rawrick.flicklist.data.api.movies.MovieManager;
import com.rawrick.flicklist.data.movie.MovieRated;
import com.rawrick.flicklist.data.movie.MovieWatchlisted;
import com.rawrick.flicklist.data.room.FLDatabaseHelper;
import com.rawrick.flicklist.databinding.FragmentWatchlistBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class WatchlistFragment extends Fragment implements MovieManager.WatchlistedMoviesManagerListener,
        MovieManager.RatedMoviesManagerListener,
        MovieWatchlistItemViewHolder.ViewHolderListener {

    private FragmentWatchlistBinding binding;

    private FLDatabaseHelper db;
    private ArrayList<MovieWatchlisted> moviesWatchlisted;

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

    private void initData() {
        db = new FLDatabaseHelper(getActivity().getApplicationContext());
        moviesWatchlisted = (ArrayList<MovieWatchlisted>) db.getAllMoviesWatchlisted();
        sortDefault();
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
                onRatedMoviesUpdated();
                movieWatchlistAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onWatchlistedMoviesUpdated() {

    }

    @Override
    public void onMovieWatchlistItemClicked(int position) {
        // go to movie detail activity
        Intent intent = new Intent(this.getActivity(), MovieActivity.class);
        movieID = String.valueOf(moviesWatchlisted.get(position).getId());
        intent.putExtra("id", movieID);
        startActivity(intent);
    }

    //

    @Override
    public void onRatedMoviesUpdated() {
    }

}