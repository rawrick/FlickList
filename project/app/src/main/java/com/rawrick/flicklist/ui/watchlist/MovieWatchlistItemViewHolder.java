package com.rawrick.flicklist.ui.watchlist;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rawrick.flicklist.R;
import com.rawrick.flicklist.data.movie.MovieRated;
import com.rawrick.flicklist.data.movie.MovieWatchlisted;

public class MovieWatchlistItemViewHolder extends RecyclerView.ViewHolder {

    private final Context context;
    private final ViewHolderListener listener;
    private final TextView title;
    private final TextView releaseYear;
    private final ImageView thumbnail;

    public MovieWatchlistItemViewHolder(Context context, @NonNull View itemView, MovieWatchlistItemViewHolder.ViewHolderListener listener) {
        super(itemView);
        this.context = context;
        this.listener = listener;
        title = itemView.findViewById(R.id.movie_watchlist_item_title);
        releaseYear = itemView.findViewById(R.id.movie_watchlist_item_release_date);
        thumbnail = itemView.findViewById(R.id.movie_watchlist_item_poster);
    }

    public void bindView(MovieWatchlisted movie) {
        Glide.with(context)
                .load(movie.getPosterPath())
                .centerCrop()
                .into(thumbnail);

        title.setText(movie.getTitle());
        releaseYear.setText(movie.getReleaseYear());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMovieWatchlistItemClicked(getAdapterPosition());
            }
        });

    }

    public interface ViewHolderListener {
        void onMovieWatchlistItemClicked(int position);
    }
}
