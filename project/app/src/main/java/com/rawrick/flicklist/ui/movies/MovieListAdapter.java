package com.rawrick.flicklist.ui.movies;

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
import com.rawrick.flicklist.ui.home.TrendingMoviesViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListItemViewHolder> {

    private final Context context;
    private ArrayList<Movie> movies;
    private final MovieListItemViewHolder.ViewHolderListener listener;
    private final int selectedPosition = RecyclerView.NO_POSITION;

    public MovieListAdapter(Context context, MovieListItemViewHolder.ViewHolderListener listener) {
        this.context = context;
        this.listener = listener;
        movies = new ArrayList<>();
    }

    public void setRatedMovies(ArrayList<Movie> movies) {
        this.movies = movies;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        return new MovieListItemViewHolder(context, v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieListItemViewHolder holder, int position) {
        holder.itemView.setSelected(position == selectedPosition);
        Movie movie = movies.get(position);
        holder.bindView(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}