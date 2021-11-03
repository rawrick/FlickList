package com.rawrick.flicklist.data.movie;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "moviesrated")
public class MovieRated implements Comparable<MovieRated> {

    @PrimaryKey
    @NonNull
    public final int id;
    @ColumnInfo(name = "rating")
    private final float rating;
    @ColumnInfo(name = "title")
    private final String title;
    @ColumnInfo(name = "releaseYear")
    private final String releaseYear;
    @ColumnInfo(name = "posterPath")
    private final String posterPath;
    @ColumnInfo(name = "backdropPath")
    private final String backdropPath;
    @ColumnInfo(name = "pagesTotal")
    private final int pagesTotal;
    @ColumnInfo(name = "watchDate")
    private String watchDate;


    @Ignore
    public MovieRated(int id, float rating, String title, String releaseYear, String posterPath, String backdropPath, int pagesTotal) {
        this.id = id;
        this.rating = rating;
        this.title = title;
        this.releaseYear = releaseYear;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.pagesTotal = pagesTotal;
    }

    // used for DB
    public MovieRated(int id, float rating, String title, String releaseYear, String posterPath, String backdropPath, int pagesTotal, String watchDate) {
        this.id = id;
        this.rating = rating;
        this.title = title;
        this.releaseYear = releaseYear;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.pagesTotal = pagesTotal;
        this.watchDate = watchDate;
    }

    public int getId() {
        return id;
    }

    public float getRating() {
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

    public String getBackdropPath() {
        return backdropPath;
    }

    public int getPagesTotal() {
        return pagesTotal;
    }

    public String getWatchDate() {
        return watchDate;
    }

    @Override
    public int compareTo(MovieRated o) {
        return 0;
    }
}