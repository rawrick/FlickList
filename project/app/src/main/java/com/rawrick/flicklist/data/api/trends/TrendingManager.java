package com.rawrick.flicklist.data.api.trends;

import android.content.Context;

import com.rawrick.flicklist.data.api.movies.MovieProvider;
import com.rawrick.flicklist.data.api.series.SeriesProvider;
import com.rawrick.flicklist.data.movie.MovieTrending;
import com.rawrick.flicklist.data.series.SeriesTrending;

import java.util.ArrayList;

public class TrendingManager {

    private ArrayList<MovieTrending> moviesTrending;
    private ArrayList<SeriesTrending> seriesTrending;
    private final Context context;
    private final TrendingManager.TrendingMoviesManagerListener listenerTrendingMovies;
    private final TrendingManager.TrendingSeriesManagerListener listenerTrendingSeries;

    public TrendingManager(Context context,
                           TrendingManager.TrendingMoviesManagerListener listenerTrendingMovies,
                           TrendingManager.TrendingSeriesManagerListener listenerTrendingSeries) {
        this.context = context;
        this.listenerTrendingMovies = listenerTrendingMovies;
        this.listenerTrendingSeries = listenerTrendingSeries;
    }

    /**
     * Trending Movies
     */

    public void getTrendingMoviesFromAPI() {
        MovieProvider provider = new MovieProvider(context);
        provider.getDataForMoviesTrending(new MovieProvider.DataListener() {
            @Override
            public void onTrendingMovieDataAvailable(ArrayList<MovieTrending> data) {
                moviesTrending = data;
                listenerTrendingMovies.onTrendingMoviesUpdated();
            }
        });
    }

    public ArrayList<MovieTrending> getMoviesTrending() {
        return moviesTrending;
    }

    public interface TrendingMoviesManagerListener {
        void onTrendingMoviesUpdated();
    }

    /**
     * Trending Series
     */

    public void getTrendingSeriesFromAPI() {
        SeriesProvider provider = new SeriesProvider(context);
        provider.getDataForSeriesTrending(new SeriesProvider.DataListener() {
            @Override
            public void onTrendingSeriesDataAvailable(ArrayList<SeriesTrending> data) {
                seriesTrending = data;
                listenerTrendingSeries.onTrendingSeriesUpdated();
            }
        });
    }

    public ArrayList<SeriesTrending> getTrendingSeries() {
        return seriesTrending;
    }

    public interface TrendingSeriesManagerListener {
        void onTrendingSeriesUpdated();
    }
}