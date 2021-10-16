package com.rawrick.flicklist.data.movie;

public class MovieTrending extends Movie {

    private final String backdropPath;

    public MovieTrending(boolean adult, int id, String overview, String posterPath, String backdropPath, String releaseDate, String title, double voteAverage) {
        super(adult, id, overview, posterPath, releaseDate, title, voteAverage);
        this.backdropPath = backdropPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }
}
