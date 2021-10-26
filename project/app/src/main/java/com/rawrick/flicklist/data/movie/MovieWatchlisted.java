package com.rawrick.flicklist.data.movie;

public class MovieWatchlisted {

    private final int id;
    private final String title;
    private final String releaseYear;
    private final String posterPath;
    private final int pagesTotal;

    public MovieWatchlisted(int id, String title, String releaseYear, String posterPath, int pagesTotal) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.posterPath = posterPath;
        this.pagesTotal = pagesTotal;
    }

    public int getId() {
        return id;
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