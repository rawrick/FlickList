package com.rawrick.flicklist.data.movie;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "movies")
public class Movie {

    @PrimaryKey
    @NonNull
    public final int id;
    @ColumnInfo(name = "title")
    private final String title; // title
    @ColumnInfo(name = "titleOriginal")
    private final String titleOriginal; // original_title
    @ColumnInfo(name = "overview")
    private final String overview; // overview
    @ColumnInfo(name = "releaseDate")
    private final String releaseDate; // release_date
    @ColumnInfo(name = "genreIDs")
    private final int[] genreIDs; // genre_ids
    @ColumnInfo(name = "isAdult")
    private final boolean isAdult; // adult
    @ColumnInfo(name = "language")
    private final String language; // original_language
    @ColumnInfo(name = "popularity")
    private final float popularity; // popularity
    @ColumnInfo(name = "voteAverage")
    private final float voteAverage; // vote_average
    @ColumnInfo(name = "posterPath")
    private final String posterPath; // poster_path
    @ColumnInfo(name = "backdropPath")
    private final String backdropPath; // backdrop_path
    @ColumnInfo(name = "userRating")
    private final float userRating; // rating
    @ColumnInfo(name = "isFavourite")
    private final boolean isFavourite; // n.a.

    public Movie(int id,
                 String title,
                 String titleOriginal,
                 String overview,
                 String releaseDate,
                 int[] genreIDs,
                 boolean isAdult,
                 String language,
                 float popularity,
                 float voteAverage,
                 String posterPath,
                 String backdropPath,
                 float userRating,
                 boolean isFavourite) {
        this.id = id;
        this.title = title;
        this.titleOriginal = titleOriginal;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.genreIDs = genreIDs;
        this.isAdult = isAdult;
        this.language = language;
        this.popularity = popularity;
        this.voteAverage = voteAverage;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.userRating = userRating;
        this.isFavourite = isFavourite;
    }
}