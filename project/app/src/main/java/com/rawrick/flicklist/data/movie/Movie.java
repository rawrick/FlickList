package com.rawrick.flicklist.data.movie;


import java.io.Serializable;

public class Movie implements Serializable {

    private final boolean adult;
    private final int id;
    private final String overview;
    private final String posterPath;
    private final String releaseDate;
    private final String title;
    private final double voteAverage;

    public Movie(boolean adult,
                 int id,
                 String overview,
                 String posterPath,
                 String releaseDate,
                 String title,
                 double voteAverage) {
        this.adult = adult;
        this.id = id;
        this.overview = overview;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.title = title;
        this.voteAverage = voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public int getId() {
        return id;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getOverview() {
        return overview;
    }
}

