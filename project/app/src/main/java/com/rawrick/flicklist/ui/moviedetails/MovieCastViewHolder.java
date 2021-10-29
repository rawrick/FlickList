package com.rawrick.flicklist.ui.moviedetails;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rawrick.flicklist.R;
import com.rawrick.flicklist.data.credits.Cast;

public class MovieCastViewHolder extends RecyclerView.ViewHolder {
    private final Context context;
    private final MovieCastViewHolder.ViewHolderListener listener;
    private final ImageView profile;
    private final TextView name;
    private final TextView character;

    public MovieCastViewHolder(Context context, @NonNull View itemView, MovieCastViewHolder.ViewHolderListener listener) {
        super(itemView);
        this.context = context;
        this.listener = listener;

        profile = itemView.findViewById(R.id.cast_profile);
        name = itemView.findViewById(R.id.cast_name);
        character = itemView.findViewById(R.id.cast_character);
    }

    public void bindView(Cast cast) {
        Log.d("FlickListApp", "bindView for " + cast.getName() + ": " + cast.getProfilePath());
        if (!cast.getProfilePath().endsWith("null")) {
            Glide.with(context)
                    .load(cast.getProfilePath())
                    .centerCrop()
                    .into(profile);
        } else {
            Glide.with(context)
                    .load(R.drawable.tmdb_logo)
                    .fitCenter()
                    .into(profile);
        }

        name.setText(cast.getName());
        character.setText(cast.getCharacter());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMovieCastItemClicked(getAdapterPosition());
            }
        });

    }

    public interface ViewHolderListener {
        void onMovieCastItemClicked(int position);
    }
}