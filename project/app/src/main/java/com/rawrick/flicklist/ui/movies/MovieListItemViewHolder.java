package com.rawrick.flicklist.ui.movies;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rawrick.flicklist.R;
import com.rawrick.flicklist.data.movie.MovieRated;

public class MovieListItemViewHolder extends RecyclerView.ViewHolder {

    private final Context context;
    private final ViewHolderListener listener;
    private final TextView title;
    private final TextView rating;
    private final TextView releaseYear;
    private final ImageView thumbnail;

    public MovieListItemViewHolder(Context context, @NonNull View itemView, MovieListItemViewHolder.ViewHolderListener listener) {
        super(itemView);
        this.context = context;
        this.listener = listener;
        title = itemView.findViewById(R.id.movie_list_item_title);
        rating = itemView.findViewById(R.id.movie_list_item_rating);
        releaseYear = itemView.findViewById(R.id.movie_list_item_watch_date);
        thumbnail = itemView.findViewById(R.id.movie_list_item_poster);
    }

    public void bindView(MovieRated movie) {
        Glide.with(context)
                .load(movie.getPosterPath())
                .centerCrop()
                .into(thumbnail);

        title.setText(movie.getTitle());
        rating.setText(String.valueOf(movie.getRating()));
        releaseYear.setText(movie.getReleaseYear());

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
