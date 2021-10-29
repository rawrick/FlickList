package com.rawrick.flicklist.data.movie;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "movieswatchlisted")
public class MovieWatchlisted {

    @PrimaryKey
    @NonNull
    public final int id;
    @ColumnInfo(name = "title")
    private final String title;
    @ColumnInfo(name = "releaseYear")
    private final String releaseYear;
    @ColumnInfo(name = "posterPath")
    private final String posterPath;
    @ColumnInfo(name = "pagesTotal")
    private final int pagesTotal;
    @ColumnInfo(name = "isTrue")
    private boolean isTrue;

    @Ignore
    public MovieWatchlisted(int id, String title, String releaseYear, String posterPath, int pagesTotal) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.posterPath = posterPath;
        this.pagesTotal = pagesTotal;
    }

    // used for DB
    public MovieWatchlisted(int id, String title, String releaseYear, String posterPath, int pagesTotal, boolean isTrue) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.posterPath = posterPath;
        this.pagesTotal = pagesTotal;
        this.isTrue = isTrue;
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

    public boolean isTrue() {
        return isTrue;
    }
}