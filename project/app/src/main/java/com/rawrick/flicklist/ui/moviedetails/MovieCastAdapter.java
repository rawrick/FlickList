package com.rawrick.flicklist.ui.moviedetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rawrick.flicklist.R;
import com.rawrick.flicklist.data.credits.Cast;
import com.rawrick.flicklist.data.movie.MovieRated;
import com.rawrick.flicklist.ui.movies.MovieListItemViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MovieCastAdapter extends RecyclerView.Adapter<MovieCastViewHolder> {


    private final Context context;
    private ArrayList<Cast> movieCast;
    private final MovieCastViewHolder.ViewHolderListener listener;
    private final int selectedPosition = RecyclerView.NO_POSITION;

    public MovieCastAdapter(Context context, MovieCastViewHolder.ViewHolderListener listener) {
        this.context = context;
        this.listener = listener;
        movieCast = new ArrayList<>();
    }

    public void setMovieCast(ArrayList<Cast> movieCast) {
        this.movieCast = movieCast;
        sortMovies();
        this.notifyDataSetChanged();
    }

    private void sortMovies() {
        Collections.sort(movieCast, CompDefault);
    }

    Comparator<Cast> CompDefault = (C1, C2) -> {
        int O1 = C1.getOrder();
        int O2 = C2.getOrder();

        return Integer.compare(O1, O2);
    };

    @NonNull
    @Override
    public MovieCastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_item, parent, false);
        return new MovieCastViewHolder(context, v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieCastViewHolder holder, int position) {
        holder.itemView.setSelected(position == selectedPosition);
        Cast castMember = movieCast.get(position);
        holder.bindView(castMember);
    }

    @Override
    public int getItemCount() {
        return movieCast.size();
    }
}