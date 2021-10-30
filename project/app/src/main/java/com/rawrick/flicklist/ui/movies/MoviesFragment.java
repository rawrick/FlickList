package com.rawrick.flicklist.ui.movies;

import static androidx.recyclerview.widget.RecyclerView.VERTICAL;
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
import com.rawrick.flicklist.data.movie.MovieRated;
import com.rawrick.flicklist.data.room.FLDatabaseHelper;
import com.rawrick.flicklist.data.util.ActivitySelector;
import com.rawrick.flicklist.databinding.FragmentMoviesBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MoviesFragment extends Fragment implements MovieListItemViewHolder.ViewHolderListener {

    private FragmentMoviesBinding binding;

    private FLDatabaseHelper db;
    private ActivitySelector activitySelector;
    ArrayList<MovieRated> moviesRated;

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
        db = new FLDatabaseHelper(getActivity().getApplicationContext());
        moviesRated = (ArrayList<MovieRated>) db.getAllMoviesRated();
        sortDefault();
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
                initData();
                movieListAdapter.setRatedMovies(moviesRated);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onMovieListItemClicked(int position) {
        // go to movie detail activity
        movieID = String.valueOf(moviesRated.get(position).getId());
        activitySelector.startMovieActivity(movieID);
    }
}