package com.rawrick.flicklist.data.movie;



public class MovieFavorited {

    public final int id;
    private final int currentPage;
    private final int pagesTotal;


    public MovieFavorited(int id, int currentPage, int pagesTotal) {
        this.id = id;
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

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPagesTotal() {
        return pagesTotal;
    }
}