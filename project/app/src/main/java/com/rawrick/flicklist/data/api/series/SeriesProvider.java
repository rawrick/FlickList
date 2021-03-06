package com.rawrick.flicklist.data.api.series;


import static com.rawrick.flicklist.data.api.URL.trendingSeriesWeekURL;

import android.content.Context;
import android.util.Log;

import com.rawrick.flicklist.data.series.SeriesTrending;
import com.rawrick.flicklist.data.api.APIRequest;
import com.rawrick.flicklist.data.api.Parser;

import org.json.JSONObject;

import java.util.ArrayList;

import static com.rawrick.flicklist.data.api.APIRequest.APIkey;


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
        APIRequest request = new APIRequest(trendingSeriesWeekURL + "?api_key=" + APIkey, context);
        request.get(listener);
    }

    public interface DataListener {
        void onTrendingSeriesDataAvailable(ArrayList<SeriesTrending> data
        );
    }
}
