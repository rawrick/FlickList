package com.rawrick.flicklist.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rawrick.flicklist.R;
import com.rawrick.flicklist.data.movie.Movie;
import com.rawrick.flicklist.data.movie.MovieTrending;

import java.util.ArrayList;

public class TrendingMoviesAdapter extends RecyclerView.Adapter<TrendingMoviesViewHolder> {

    private final Context context;
    private ArrayList<MovieTrending> audioBooks;
    private final TrendingMoviesViewHolder.ViewHolderListener listener;

    public TrendingMoviesAdapter(Context context, TrendingMoviesViewHolder.ViewHolderListener listener) {
        this.context = context;
        this.listener = listener;
        audioBooks = new ArrayList<>();
    }

    public void setAudioBooks(ArrayList<MovieTrending> audioBooks) {
        this.audioBooks = audioBooks;
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
        Movie audioBook = audioBooks.get(position);
        holder.bindView(audioBook);
    }

    @Override
    public int getItemCount() {
        return audioBooks.size();
    }
}