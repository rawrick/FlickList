package com.rawrick.flicklist.data.movie;


import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.io.Serializable;
import java.util.ArrayList;

public class Movie implements Serializable {

    private final static String imageUrl = "https://image.tmdb.org/t/p/w500/";

    private final String id,
            title,
            overview,
            releaseDate,
            posterPath;
    private final int runtime;
    private final boolean adult;

    public Movie(String id,
                 String title,
                 String overview,
                 Integer runtime,
                 String releaseDate,
                 boolean adult,
                 String posterPath) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.runtime = runtime;
        this.releaseDate =
                releaseDate;
        this.adult = adult;
        this.posterPath = posterPath;
    }

    public static ArrayList<Movie> fromJSONString(String jsonString) {
        ArrayList<Movie> movies = new ArrayList<>();
        JsonArray jsonArray = new Gson().fromJson(jsonString, JsonArray.class);
        jsonArray.forEach(jsonElement -> {
            movies.add(new Gson().fromJson(jsonElement.getAsJsonObject().toString(), Movie.class));
        });
        return movies;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return overview;
    }

    public Integer getAuthor() {
        return runtime;
    }

    public String getWallpaperURLString() {
        return releaseDate;
    }

    public boolean getAudioURLString() {
        return adult;
    }

    public String getPosterPath() {
        return posterPath;
    }
}

