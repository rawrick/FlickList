package com.rawrick.flicklist.ui.home;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rawrick.flicklist.R;
import com.rawrick.flicklist.data.movie.Movie;


public class TrendingMoviesViewHolder extends RecyclerView.ViewHolder {

    private final Context context;
    public final View trendingMovieView;
    private final ImageView thumbnail;
    private final ViewHolderListener listener;

    public TrendingMoviesViewHolder(Context context, @NonNull View trendingMovieView, ViewHolderListener listener) {
        super(trendingMovieView);
        this.context = context;
        this.trendingMovieView = trendingMovieView;
        thumbnail = trendingMovieView.findViewById(R.id.home_trending_movie_item);
        this.listener = listener;
    }

    public void bindView(Movie movie) {

        Log.d("glide", movie.getPosterPath());
        Glide.with(context)
                .load(movie.getPosterPath())
                .centerCrop()
                .into(thumbnail);

        trendingMovieView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onViewClicked(getAdapterPosition());
            }
        });
    }

    public interface ViewHolderListener {
        void onViewClicked(int position);
    }
}