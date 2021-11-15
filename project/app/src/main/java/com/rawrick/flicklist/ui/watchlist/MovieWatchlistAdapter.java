package com.rawrick.flicklist.ui.watchlist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rawrick.flicklist.R;
import com.rawrick.flicklist.data.movie.Movie;
import com.rawrick.flicklist.data.movie.MovieRated;
import com.rawrick.flicklist.data.movie.MovieTrending;
import com.rawrick.flicklist.data.movie.MovieWatchlisted;
import com.rawrick.flicklist.ui.home.TrendingMoviesViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MovieWatchlistAdapter extends RecyclerView.Adapter<MovieWatchlistItemViewHolder> {

    private final Context context;
    private ArrayList<Movie> movies;
    private final MovieWatchlistItemViewHolder.ViewHolderListener listener;
    private final int selectedPosition = RecyclerView.NO_POSITION;

    public MovieWatchlistAdapter(Context context, MovieWatchlistItemViewHolder.ViewHolderListener listener) {
        this.context = context;
        this.listener = listener;
        movies = new ArrayList<>();
    }

    public void setWatchlistedMovies(ArrayList<Movie> movies) {
        this.movies = movies;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieWatchlistItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_watchlist_item, parent, false);
        return new MovieWatchlistItemViewHolder(context, v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieWatchlistItemViewHolder holder, int position) {
        holder.itemView.setSelected(position == selectedPosition);
        Movie movie = movies.get(position);
        holder.bindView(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}