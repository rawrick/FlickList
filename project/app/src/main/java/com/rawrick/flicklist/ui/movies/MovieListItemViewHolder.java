package com.rawrick.flicklist.ui.movies;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rawrick.flicklist.R;
import com.rawrick.flicklist.data.movie.Movie;
import com.rawrick.flicklist.data.movie.MovieRated;
import com.rawrick.flicklist.data.room.FLDatabaseHelper;

public class MovieListItemViewHolder extends RecyclerView.ViewHolder {

    private final Context context;
    private final ViewHolderListener listener;
    private final FLDatabaseHelper db;
    private final TextView title;
    private final TextView rating;
    private final TextView releaseYear;
    private final ImageView thumbnail;
    private final ImageView favourite;

    public MovieListItemViewHolder(Context context, @NonNull View itemView, MovieListItemViewHolder.ViewHolderListener listener) {
        super(itemView);
        this.context = context;
        this.listener = listener;
        this.db = FLDatabaseHelper.getInstance(context);
        title = itemView.findViewById(R.id.movie_list_item_title);
        rating = itemView.findViewById(R.id.movie_list_item_rating);
        releaseYear = itemView.findViewById(R.id.movie_list_item_watch_date);
        thumbnail = itemView.findViewById(R.id.movie_list_item_poster);
        favourite = itemView.findViewById(R.id.movie_list_item_fav);
    }

    public void bindView(Movie movie) {
        Glide.with(context)
                .load(movie.getPosterPath())
                .centerCrop()
                .into(thumbnail);

        title.setText(movie.getTitle());
        String getRating = String.valueOf(movie.getUserRating());
        String ratingFormatted;
        if (getRating.endsWith(".0")) {
            ratingFormatted = getRating.substring(0, getRating.length() - 2);
        } else {
            ratingFormatted = getRating;
        }
        rating.setText(ratingFormatted);
        if (movie.isFavourite()) {
            favourite.setVisibility(View.VISIBLE);
        } else {
            favourite.setVisibility(View.INVISIBLE);
        }
        releaseYear.setText(movie.getReleaseDate());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMovieListItemClicked(getAdapterPosition());
            }
        });

    }

    public interface ViewHolderListener {
        void onMovieListItemClicked(int position);
    }
}
