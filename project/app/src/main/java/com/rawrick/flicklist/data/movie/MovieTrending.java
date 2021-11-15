package com.rawrick.flicklist.data.movie;

public class MovieTrending {

    private final boolean isAdult;
    private final int id;
    private final String overview;
    private final String posterPath;
    private final String releaseDate;
    private final String title;
    private final double voteAverage;
    private final String backdropPath;
    private final int index;
    private final boolean isSelected;

    public MovieTrending(String backdropPath, int index, boolean isSelected, boolean isAdult, int id, String overview, String posterPath, String releaseDate, String title, double voteAverage) {
        this.isAdult = isAdult;
        this.id = id;
        this.overview = overview;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.title = title;
        this.voteAverage = voteAverage;
        this.index = index;
        this.isSelected = isSelected;
        this.backdropPath = backdropPath;
    }

    public int getIndex() {
        return index;
    }

    public boolean isSelected() {
        return isSelected;
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

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
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
}
