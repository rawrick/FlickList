package com.rawrick.flicklist.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rawrick.flicklist.R;
import com.rawrick.flicklist.data.movie.MovieTrending;

import java.util.ArrayList;        import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rawrick.flicklist.R;
import com.rawrick.flicklist.data.movie.MovieTrending;
import com.rawrick.flicklist.data.movie.SeriesTrending;

import java.util.ArrayList;

public class TrendingSeriesAdapter extends RecyclerView.Adapter<TrendingSeriesViewHolder> {

    private final Context context;
    private ArrayList<SeriesTrending> seriesTrending;
    private final TrendingSeriesViewHolder.ViewHolderListener listener;

    public TrendingSeriesAdapter(Context context, TrendingSeriesViewHolder.ViewHolderListener listener) {
        this.context = context;
        this.listener = listener;
        seriesTrending = new ArrayList<>();
    }

    public void setSeriesTrending(ArrayList<SeriesTrending> seriesTrending) {
        this.seriesTrending = seriesTrending;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TrendingSeriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_trending_series_item, parent, false);
        return new TrendingSeriesViewHolder(context, v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TrendingSeriesViewHolder holder, int position) {
        SeriesTrending series = seriesTrending.get(position);
        holder.bindView(series);
    }

    @Override
    public int getItemCount() {
        return seriesTrending.size();
    }
}
