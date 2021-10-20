package com.rawrick.flicklist.data.series;

import java.io.Serializable;

public class Series implements Serializable {

    private final int id;
    private final String overview;
    private final String posterPath;
    private final String releaseDate;
    private final String title;
    private final double voteAverage;

    public Series( int id,
                 String overview,
                 String posterPath,
                 String releaseDate,
                 String title,
                 double voteAverage) {
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
