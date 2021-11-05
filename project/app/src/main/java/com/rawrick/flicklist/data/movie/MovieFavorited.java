package com.rawrick.flicklist.data.movie;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "moviesfavorited")
public class MovieFavorited {

    @PrimaryKey
    @NonNull
    public final int id;
    @ColumnInfo(name = "isFavorited")
    private boolean isFavorited;
    @ColumnInfo(name = "pagesTotal")
    private final int pagesTotal;

    @Ignore
    public MovieFavorited(int id, int pagesTotal) {
        this.id = id;
        this.pagesTotal = pagesTotal;
    }

    // used by database
    public MovieFavorited(int id, boolean isFavorited, int pagesTotal) {
        this.id = id;
        this.isFavorited = isFavorited;
        this.pagesTotal = pagesTotal;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        MovieFavorited other = (MovieFavorited) obj;
        if (id == 0) {
            if (other.id != 0)
                return false;
        } else if (!(id == other.id))
            return false;
        return true;
    }

    public int getId() {
        return id;
    }

    public boolean isFavorited() {
        return isFavorited;
    }

    public int getPagesTotal() {
        return pagesTotal;
    }
}