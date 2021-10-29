package com.rawrick.flicklist.ui.watchlist;

import static androidx.recyclerview.widget.RecyclerView.VERTICAL;
import static com.rawrick.flicklist.data.util.api.APIRequest.currentPageWatchlistedMovies;
import static com.rawrick.flicklist.data.util.api.APIRequest.movieID;

import android.content.Intent;
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
import com.rawrick.flicklist.data.util.api.movies.MovieManager;
import com.rawrick.flicklist.databinding.FragmentWatchlistBinding;

public class WatchlistFragment extends Fragment implements MovieManager.WatchlistedMoviesManagerListener, MovieManager.RatedMoviesManagerListener, MovieManager.TrendingMoviesManagerListener, MovieWatchlistItemViewHolder.ViewHolderListener {

    private FragmentWatchlistBinding binding;

    private MovieManager movieManager;
    private int i = 2;
    private int pagesTotal;

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
        movieManager = new MovieManager(getActivity(), this, this, this);
        // gets first page TODO check if rated movies exist
        currentPageWatchlistedMovies = "1";
        movieManager.getWatchlistedMoviesFromAPI();

    }

    private void initUI(View view) {
        // initialize rated movies adapter
        recyclerWatchlistedMovies = view.findViewById(R.id.watchlist_movies);
        recyclerWatchlistedMovies.setLayoutManager(new LinearLayoutManager(getActivity(), VERTICAL, false));
        movieWatchlistAdapter = new MovieWatchlistAdapter(getActivity(), this);
        recyclerWatchlistedMovies.setAdapter(movieWatchlistAdapter);

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
        // saves total pages that have to be fetched
        pagesTotal = movieManager.getWatchlistedMovies().get(0).getPagesTotal();
        while (i <= pagesTotal) {
            // changes page number on API request URL
            currentPageWatchlistedMovies = String.valueOf(i);
            movieManager.getRatedMoviesFromAPI();
            i++;
        }
        // sets adapter once all pages have been fetched
        if (i - 1 == pagesTotal) {
            movieWatchlistAdapter.setWatchlistedMovies(movieManager.getWatchlistedMovies());
        }
    }

    @Override
    public void onMovieWatchlistItemClicked(int position) {
        // go to movie detail activity
        Intent intent = new Intent(this.getActivity(), MovieActivity.class);
        movieID = String.valueOf(movieManager.getWatchlistedMovies().get(position).getId());
        intent.putExtra("id", movieID);
        startActivity(intent);
    }

    //

    @Override
    public void onRatedMoviesUpdated() {

    }

    @Override
    public void onTrendingMoviesUpdated() {

    }

}