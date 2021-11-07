package com.rawrick.flicklist.data.util;

import com.rawrick.flicklist.data.movie.Movie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MediaSorter {

    public ArrayList<Movie> sortMoviesByTitle(ArrayList<Movie> movies) {
        Collections.sort(movies, CompMovieTitle);
        return movies;
    }

    public ArrayList<Movie> sortMoviesByRating(ArrayList<Movie> movies) {
        Collections.sort(movies, CompMovieRating);
        return movies;
    }

    Comparator<Movie> CompMovieTitle = (M1, M2) -> {
        String T1 = M1.getTitle();
        String T2 = M2.getTitle();
        String t1 = ignoreArticles(T1);
        String t2 = ignoreArticles(T2);
        return t1.compareTo(t2);
    };

    Comparator<Movie> CompMovieRating = (M1, M2) -> {
        double R1 = M1.getUserRating();
        double R2 = M2.getUserRating();
        String T1 = M1.getTitle();
        String T2 = M2.getTitle();
        String t1 = ignoreArticles(T1);
        String t2 = ignoreArticles(T2);

        if (R1 > R2) {
            return -1;
        }
        if (R1 < R2) {
            return 1;
        }
        if (R1 == R2) {
            return t1.compareTo(t2);
        }
        return 0;
    };

    private String ignoreArticles(String input) {
        if (input.startsWith("The ")) {
            return input.substring(4);
        } else if (input.startsWith("A ")) {
            return input.substring(2);
        } else {
            return input;
        }
    }
}