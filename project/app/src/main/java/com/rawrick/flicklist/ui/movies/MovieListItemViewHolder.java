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
import com.rawrick.flicklist.data.movie.MovieRated;
import com.rawrick.flicklist.data.room.FLDatabaseHelper;

public class MovieListItemViewHolder extends RecyclerView.ViewHolder {

    private final Context context;
    private final ViewHolderListener listener;
    private final TextView title;
    private final TextView rating;
    private final TextView releaseYear;
    private final ImageView thumbnail;
    private final ImageView favourite;
    private final FLDatabaseHelper db;

    public MovieListItemViewHolder(Context context, @NonNull View itemView, MovieListItemViewHolder.ViewHolderListener listener) {
        super(itemView);
        this.context = context;
        this.listener = listener;
        title = itemView.findViewById(R.id.movie_list_item_title);
        rating = itemView.findViewById(R.id.movie_list_item_rating);
        releaseYear = itemView.findViewById(R.id.movie_list_item_watch_date);
        thumbnail = itemView.findViewById(R.id.movie_list_item_poster);
        favourite = itemView.findViewById(R.id.movie_list_item_fav);
        db = FLDatabaseHelper.getInstance(context);
    }

    public void bindView(MovieRated movie) {
        Glide.with(context)
                .load(movie.getPosterPath())
                .centerCrop()
                .into(thumbnail);

        title.setText(movie.getTitle());
        String getRating = String.valueOf(movie.getRating());
        String ratingFormatted;
        if (getRating.endsWith(".0")) {
            ratingFormatted = getRating.substring(0, getRating.length() - 2);
        } else {
            ratingFormatted = getRating;
        }
        rating.setText(ratingFormatted);
        Log.d("viewholderdebug", "is " + db.getMovieRatedForID(movie.getId()).getTitle() + " favourite: "
                + db.isMovieFavoritedForID(db.getMovieRatedForID(movie.getId()).getId()));
        if (db.isMovieFavoritedForID(db.getMovieRatedForID(movie.getId()).getId())) {
            favourite.setVisibility(View.VISIBLE);
        } else {
            favourite.setVisibility(View.INVISIBLE);
        }
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
