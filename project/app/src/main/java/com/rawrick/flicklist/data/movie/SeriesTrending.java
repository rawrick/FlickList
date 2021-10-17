package com.rawrick.flicklist.data.movie;

public class SeriesTrending extends Series {

    private final String backdropPath;
    private final int index;
    private final boolean isSelected;

    public SeriesTrending(String backdropPath, int index, boolean isSelected, int id, String overview, String posterPath, String releaseDate, String title, double voteAverage) {
        super(id, overview, posterPath, releaseDate, title, voteAverage);
        this.index = index;
        this.isSelected = isSelected;
        this.backdropPath = backdropPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public int getIndex() {
        return index;
    }

    public boolean isSelected() {
        return isSelected;
    }
}
