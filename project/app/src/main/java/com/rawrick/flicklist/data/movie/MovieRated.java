package com.rawrick.flicklist.data.movie;

public class MovieRated {

    private final int id;
    private final double rating;
    private final String title;
    private final String releaseYear;
    private final String posterPath;
    private final int pagesTotal;
// private final boolean isFavourite
    // private final String watchDate


    public MovieRated(int id, double rating, String title, String releaseYear, String posterPath, int pagesTotal) {
        this.id = id;
        this.rating = rating;
        this.title = title;
        this.releaseYear = releaseYear;
        this.posterPath = posterPath;
        this.pagesTotal = pagesTotal;
    }

    public int getId() {
        return id;
    }

    public double getRating() {
        return rating;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public int getPagesTotal() {
        return pagesTotal;
    }
}