package com.rawrick.flicklist.data.movie;

public class MovieRated {

    private final int id;
    private final int rating;

    public MovieRated(int id, int rating){
        this.id = id;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public int getRating() {
        return rating;
    }
}
