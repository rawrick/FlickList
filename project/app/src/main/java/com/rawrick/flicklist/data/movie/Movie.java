package com.rawrick.flicklist.data.movie;




public class Movie {

    private final boolean isAdult;
    private final int id;
    private final String overview;
    private final String posterPath;
    private final String backdropPath;
    private final String releaseDate;
    private final String title;
    private final double voteAverage;

    public Movie(boolean isAdult,
                 int id,
                 String overview,
                 String posterPath,
                 String backdropPath,
                 String releaseDate,
                 String title,
                 double voteAverage) {
        this.isAdult = isAdult;
        this.id = id;
        this.overview = overview;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.releaseDate = releaseDate;
        this.title = title;
        this.voteAverage = voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public boolean isAdult() {
        return isAdult;
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

