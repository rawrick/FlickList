package com.rawrick.flicklist.data.util;


import static com.rawrick.flicklist.data.tools.URL.trendingSeriesWeekURL;

import android.content.Context;
import android.util.Log;

import com.rawrick.flicklist.data.series.SeriesTrending;

import org.json.JSONObject;

import java.util.ArrayList;

import static com.rawrick.flicklist.data.util.APIRequest.key;


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
        APIRequest request = new APIRequest(trendingSeriesWeekURL + "?api_key=" + key, context);
        request.send(listener);
    }

    public interface DataListener {
        void onTrendingSeriesDataAvailable(ArrayList<SeriesTrending> data
        );
    }
}
