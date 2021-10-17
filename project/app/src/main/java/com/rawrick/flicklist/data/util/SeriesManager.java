package com.rawrick.flicklist.data.util;

import android.content.Context;

import com.rawrick.flicklist.data.movie.MovieTrending;
import com.rawrick.flicklist.data.movie.SeriesTrending;

import java.util.ArrayList;

public class SeriesManager {


    private ArrayList<SeriesTrending> seriesTrending;
    private final Context context;
    private final SeriesManagerListener listener;

    public SeriesManager(Context context, SeriesManagerListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void getTrendingSeriesFromAPI() {
        SeriesProvider provider = new SeriesProvider(context);
        provider.getDataForSeriesTrending(new SeriesProvider.DataListener() {
            @Override
            public void onTrendingSeriesDataAvailable(ArrayList<SeriesTrending> data) {
                seriesTrending = data;
                listener.onTrendingSeriesUpdated();
            }
        });
    }


    public ArrayList<SeriesTrending> getTrendingSeries() {
        return seriesTrending;
    }

    public interface SeriesManagerListener {
        void onTrendingSeriesUpdated();
    }

}