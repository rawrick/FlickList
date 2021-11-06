package com.rawrick.flicklist.data.movie;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "moviedetails")
public class MovieDetails {

    @PrimaryKey
    @NonNull
    public final int id;
    @ColumnInfo(name = "isAdult")
    private final boolean isAdult;
    @ColumnInfo(name = "overview")
    private final String overview;
    @ColumnInfo(name = "tagline")
    private final String tagline;
    @ColumnInfo(name = "posterPath")
    private final String posterPath;
    @ColumnInfo(name = "backdropPath")
    private final String backdropPath;
    @ColumnInfo(name = "releaseDate")
    private final String releaseDate;
    @ColumnInfo(name = "runtime")
    private final int runtime;
    @ColumnInfo(name = "title")
    private final String title;
    @ColumnInfo(name = "voteAverage")
    private final float voteAverage;
    @ColumnInfo(name = "userRating")
    private float userRating;
    @ColumnInfo(name = "userFavoriteStatus")
    private boolean userFavoriteStatus;

    @Ignore
    public MovieDetails(boolean isAdult,
                        int id,
                        String overview,
                        String tagline,
                        String posterPath,
                        String backdropPath,
                        String releaseDate,
                        int runtime,
                        String title,
                        float voteAverage) {
        this.isAdult = isAdult;
        this.id = id;
        this.overview = overview;
        this.tagline = tagline;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.releaseDate = releaseDate;
        this.runtime = runtime;
        this.title = title;
        this.voteAverage = voteAverage;
    }

    // used by database
    public MovieDetails(boolean isAdult,
                        int id,
                        String overview,
                        String tagline,
                        String posterPath,
                        String backdropPath,
                        String releaseDate,
                        int runtime,
                        String title,
                        float voteAverage,
                        float userRating,
                        boolean userFavoriteStatus) {
        this.isAdult = isAdult;
        this.id = id;
        this.overview = overview;
        this.tagline = tagline;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.releaseDate = releaseDate;
        this.runtime = runtime;
        this.title = title;
        this.voteAverage = voteAverage;
        this.userRating = userRating;
        this.userFavoriteStatus = userFavoriteStatus;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public int getId() {
        return id;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getTitle() {
        return title;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public float getUserRating() {
        return userRating;
    }

    public String getTagline() {
        return tagline;
    }

    public boolean isUserFavoriteStatus() {
        return userFavoriteStatus;
    }
}