package com.rawrick.flicklist.data.movie;

public class MovieWatched {

    private final Movie movie;
    private final int id;
    private final boolean isRated;

    public MovieWatched(Movie movie,
                 int id,
                 boolean isRated) {
        this.movie = movie;
        this.id = id;
        this.isRated = isRated;
    }


}
