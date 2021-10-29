package com.rawrick.flicklist.ui.movies;

import static androidx.recyclerview.widget.RecyclerView.VERTICAL;

import static com.rawrick.flicklist.data.util.APIRequest.currentPageRatedMovies;
import static com.rawrick.flicklist.data.util.APIRequest.movieID;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.rawrick.flicklist.MainActivity;
import com.rawrick.flicklist.MovieActivity;
import com.rawrick.flicklist.R;
import com.rawrick.flicklist.data.movie.MovieRated;
import com.rawrick.flicklist.data.room.FLDatabaseHelper;
import com.rawrick.flicklist.data.util.MovieManager;
import com.rawrick.flicklist.data.util.MovieProvider;
import com.rawrick.flicklist.databinding.FragmentMoviesBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MoviesFragment extends Fragment implements MovieListItemViewHolder.ViewHolderListener {

    private FragmentMoviesBinding binding;

    private FLDatabaseHelper db;
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

    private void initData() {
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

    private void initUI(View view) {
        // initialize rated movies adapter
        recyclerRatedMovies = view.findViewById(R.id.movie_list);
        recyclerRatedMovies.setLayoutManager(new LinearLayoutManager(getActivity(), VERTICAL, false));
        movieListAdapter = new MovieListAdapter(getActivity(), this);
        recyclerRatedMovies.setAdapter(movieListAdapter);
        movieListAdapter.setRatedMovies(moviesRated);

        swipeRefreshLayout = view.findViewById(R.id.movies_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }



    @Override
    public void onMovieListItemClicked(int position) {
        // go to movie detail activity
        Intent intent = new Intent(this.getActivity(), MovieActivity.class);
        movieID = String.valueOf(moviesRated.get(position).getId());
        intent.putExtra("id", movieID);
        startActivity(intent);
    }

}