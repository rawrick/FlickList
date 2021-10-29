package com.rawrick.flicklist.ui.watchlist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rawrick.flicklist.R;
import com.rawrick.flicklist.data.movie.MovieRated;
import com.rawrick.flicklist.data.movie.MovieTrending;
import com.rawrick.flicklist.data.movie.MovieWatchlisted;
import com.rawrick.flicklist.ui.home.TrendingMoviesViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MovieWatchlistAdapter extends RecyclerView.Adapter<MovieWatchlistItemViewHolder> {

    private final Context context;
    private ArrayList<MovieWatchlisted> moviesWatchlisted;
    private final MovieWatchlistItemViewHolder.ViewHolderListener listener;
    private final int selectedPosition = RecyclerView.NO_POSITION;

    public MovieWatchlistAdapter(Context context, MovieWatchlistItemViewHolder.ViewHolderListener listener) {
        this.context = context;
        this.listener = listener;
        moviesWatchlisted = new ArrayList<>();
    }

    public void setWatchlistedMovies(ArrayList<MovieWatchlisted> moviesWatchlisted) {
        this.moviesWatchlisted = moviesWatchlisted;
        sortMovies();
        this.notifyDataSetChanged();
    }

    private void sortMovies() {
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

    @NonNull
    @Override
    public MovieWatchlistItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        return new MovieWatchlistItemViewHolder(context, v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieWatchlistItemViewHolder holder, int position) {
        holder.itemView.setSelected(position == selectedPosition);
        MovieWatchlisted movieWatchlisted = moviesWatchlisted.get(position);
        holder.bindView(movieWatchlisted);
    }

    @Override
    public int getItemCount() {
        return moviesWatchlisted.size();
    }
}