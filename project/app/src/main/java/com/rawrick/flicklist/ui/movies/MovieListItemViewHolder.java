package com.rawrick.flicklist.ui.movies;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rawrick.flicklist.R;
import com.rawrick.flicklist.data.movie.MovieRated;

public class MovieListItemViewHolder extends RecyclerView.ViewHolder {

    private final Context context;
    private final ImageView thumbnail;
    private final ViewHolderListener listener;

    public MovieListItemViewHolder(Context context, @NonNull View itemView, MovieListItemViewHolder.ViewHolderListener listener) {
        super(itemView);
        this.context = context;
        this.listener = listener;
        thumbnail = itemView.findViewById(R.id.movie_list_item_poster);
    }

    public void bindView(MovieRated movie) {

        Glide.with(context)
                .load(movie.getPosterPath())
                .centerCrop()
                .into(thumbnail);



    }

    public interface ViewHolderListener {
        void onMovieListItemClicked(int position);
    }
}
