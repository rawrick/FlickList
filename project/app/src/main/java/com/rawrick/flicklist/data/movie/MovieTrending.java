package com.rawrick.flicklist.data.movie;

public class MovieTrending extends Movie {

    private final String backdropPath;
    private final int index;
    private final boolean isSelected;

    public MovieTrending(String backdropPath, int index, boolean isSelected, boolean isAdult, int id, String overview, String posterPath, String releaseDate, String title, double voteAverage) {
        super(isAdult, id, overview, posterPath, releaseDate, title, voteAverage);
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
}
