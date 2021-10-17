package com.rawrick.flicklist.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rawrick.flicklist.R;
import com.rawrick.flicklist.data.movie.MovieTrending;

import java.util.ArrayList;

public class TrendingMoviesAdapter extends RecyclerView.Adapter<TrendingMoviesViewHolder> {

    private final Context context;
    private ArrayList<MovieTrending> moviesTrending;
    private final TrendingMoviesViewHolder.ViewHolderListener listener;
    private final int selectedPosition = RecyclerView.NO_POSITION;

    public TrendingMoviesAdapter(Context context, TrendingMoviesViewHolder.ViewHolderListener listener) {
        this.context = context;
        this.listener = listener;
        moviesTrending = new ArrayList<>();
    }

    public void setMoviesTrending(ArrayList<MovieTrending> moviesTrending) {
        this.moviesTrending = moviesTrending;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TrendingMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_trending_movies_item, parent, false);
        return new TrendingMoviesViewHolder(context, v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TrendingMoviesViewHolder holder, int position) {
        holder.itemView.setSelected(position == selectedPosition);
        MovieTrending audioBook = moviesTrending.get(position);
        holder.bindView(audioBook);
    }

    @Override
    public int getItemCount() {
        return moviesTrending.size();
    }
}