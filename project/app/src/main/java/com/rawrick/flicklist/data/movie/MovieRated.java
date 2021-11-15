package com.rawrick.flicklist.data.movie;

public class MovieRated implements Comparable<MovieRated> {

    public final int id;
    private final String title; // title
    private final String titleOriginal; // original_title
    private final String overview; // overview
    private final String releaseDate; // release_date
    //@ColumnInfo(name = "genreIDs")
    //private final int[] genreIDs; // genre_ids
    private final boolean isAdult; // adult
    private final String language; // original_language
    private final float popularity; // popularity
    private final float voteAverage; // vote_average
    private final String posterPath; // poster_path
    private final String backdropPath; // backdrop_path
    private final float userRating; // rating
    private final int currentPage;
    private final int pagesTotal;

    public MovieRated(int id,
                      String title,
                      String titleOriginal,
                      String overview,
                      String releaseDate,
                      //int[] genreIDs,
                      boolean isAdult,
                      String language,
                      float popularity,
                      float voteAverage,
                      String posterPath,
                      String backdropPath,
                      float userRating,
                      int currentPage,
                      int pagesTotal) {
        this.id = id;
        this.title = title;
        this.titleOriginal = titleOriginal;
        this.overview = overview;
        this.releaseDate = releaseDate;
        //this.genreIDs = genreIDs;
        this.isAdult = isAdult;
        this.language = language;
        this.popularity = popularity;
        this.voteAverage = voteAverage;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.userRating = userRating;
        this.currentPage = currentPage;
        this.pagesTotal = pagesTotal;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        MovieRated other = (MovieRated) obj;
        if (id == 0) {
            if (other.id != 0)
                return false;
        } else if (!(id == other.id))
            return false;
        return true;
    }

    @Override
    public int compareTo(MovieRated o) {
        return 0;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTitleOriginal() {
        return titleOriginal;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public String getLanguage() {
        return language;
    }

    public float getPopularity() {
        return popularity;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public float getUserRating() {
        return userRating;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPagesTotal() {
        return pagesTotal;
    }
}