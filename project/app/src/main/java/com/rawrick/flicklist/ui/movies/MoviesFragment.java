package com.rawrick.flicklist.ui.movies;

import static androidx.recyclerview.widget.RecyclerView.VERTICAL;

import android.os.Bundle;
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

import com.rawrick.flicklist.R;
import com.rawrick.flicklist.data.util.MovieManager;
import com.rawrick.flicklist.databinding.FragmentMoviesBinding;

public class MoviesFragment extends Fragment implements MovieManager.RatedMoviesManagerListener, MovieListItemViewHolder.ViewHolderListener, MovieManager.TrendingMoviesManagerListener {

    private FragmentMoviesBinding binding;

    private SwipeRefreshLayout swipeRefreshLayout;
    // series data & adapter
    private MovieManager movieManager;
    private RecyclerView recyclerRatedMovies;
    public MovieListAdapter movieListAdapter;
    // Views for featured trending movie
    TextView movieTitle;
    TextView movieRating;
    TextView movieReleaseYear;
    ImageView moviePoster;

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
        movieManager = new MovieManager(getActivity(), this, this);
        movieManager.getRatedMoviesFromAPI();
    }

    private void initUI(View view) {
        // initialize rated movies adapter
        recyclerRatedMovies = view.findViewById(R.id.movie_list);
        recyclerRatedMovies.setLayoutManager(new LinearLayoutManager(getActivity(), VERTICAL, false));
        movieListAdapter = new MovieListAdapter(getActivity(), this);
        recyclerRatedMovies.setAdapter(movieListAdapter);
/*
        rM = movieManager.getRatedMovies();
        for (int i = 0; i < rM.size(); i++) {
            MovieRated movieRated = rM.get(i);


            Log.d("FlickListApp", "id: " + movieRated.getId() + ", title: " + movieRated.getTitle() + ", rating: " + movieRated.getRating());


 */
        swipeRefreshLayout = view.findViewById(R.id.movies_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRatedMoviesUpdated() {
        movieListAdapter.setRatedMovies(movieManager.getRatedMovies());
    }

    @Override
    public void onMovieListItemClicked(int position) {
        // go to movie detail activity
    }

    @Override
    public void onTrendingMoviesUpdated() {
        // empty
    }
}