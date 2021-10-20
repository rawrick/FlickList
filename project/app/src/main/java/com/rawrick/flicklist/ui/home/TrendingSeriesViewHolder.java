package com.rawrick.flicklist.ui.home;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rawrick.flicklist.R;

import com.rawrick.flicklist.data.series.SeriesTrending;

public class TrendingSeriesViewHolder extends RecyclerView.ViewHolder {


    private final Context context;
    public final View trendingSeriesView;
    private final ImageView thumbnail;
    private final ViewHolderListener listener;

    public TrendingSeriesViewHolder(Context context, @NonNull View trendingSeriesView, ViewHolderListener listener) {
        super(trendingSeriesView);
        this.context = context;
        this.trendingSeriesView = trendingSeriesView;
        thumbnail = trendingSeriesView.findViewById(R.id.home_trending_series_image);
        this.listener = listener;
    }

    public void bindView(SeriesTrending series) {

        Glide.with(context)
                .load(series.getPosterPath())
                .centerCrop()
                .into(thumbnail);

        trendingSeriesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onTrendingSeriesClicked(getAdapterPosition());

            }
        });
    }

    public interface ViewHolderListener {
        void onTrendingSeriesClicked(int position);
    }
}