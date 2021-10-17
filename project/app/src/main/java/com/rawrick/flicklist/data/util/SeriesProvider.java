package com.rawrick.flicklist.data.util;


import android.content.Context;
import android.util.Log;

import com.rawrick.flicklist.data.movie.MovieTrending;
import com.rawrick.flicklist.data.movie.SeriesTrending;

import org.json.JSONObject;

import java.util.ArrayList;

public class SeriesProvider {

    private final Context context;
    private ArrayList<SeriesTrending> seriesData;

    public SeriesProvider(Context context) {
        this.context = context;
    }

    public void getDataForSeriesTrending(DataListener listener) {
        if (seriesData == null) {
            updateSeriesTrendingData(new APIRequest.ResponseListener() {
                @Override
                public void onResponse(JSONObject response) {
                    seriesData = Parser.parseTrendingSeries(response);
                    listener.onTrendingSeriesDataAvailable(seriesData);
                }
                @Override
                public void onError() {
                    Log.d("FlickListApp", "No Connection");
                }
            });
        } else {
            listener.onTrendingSeriesDataAvailable(seriesData);
        }
    }

    private void updateSeriesTrendingData(APIRequest.ResponseListener listener) {
        APIRequest request = new APIRequest(APIRequest.Route.SERIES_TRENDING_WEEK_DATA, context);
        request.send(listener);
    }

    public interface DataListener {
        void onTrendingSeriesDataAvailable(ArrayList<SeriesTrending> data
        );
    }
}
