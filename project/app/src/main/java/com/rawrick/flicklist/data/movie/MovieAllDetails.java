package com.rawrick.flicklist.data.movie;

public class MovieAllDetails extends Movie {

    /*
        // information from tmdb API
    private final boolean adult;
    private final String backdropPath;
    private final String belongs_to_collection;
    private final Integer budget;
    private final int genres_count;
    private final int genre1id;
    private final String genre1name;
    private final int genre2id;
    private final String genre2name;
    private final String homepage;
    private final int id;
    private final String imdb_id;
    private final String original_language;
    private final String original_title;
    private final String overview;
    private final int popularity;
    private final String posterPath;
    // production companies: id logopath name origincountry
    // production countries: iso name
    private final String releaseDate;
    private final int revenue;
    private final int runtime;
    // spoken languages: englishname iso name
    private final String status;
    private final String tagline;
    private final String title;
    private final boolean video;
    private final int voteAverage;
    private final int voteCount;
     */


    public MovieAllDetails(boolean adult,
                           String backdropPath,
                           String belongs_to_collection,
                           int budget,
                           int genres_count,
                           int genre1id,
                           String genre1name,
                           int genre2id,
                           String genre2name,
                           String homepage,
                           int id,
                           String imdb_id,
                           String original_language,
                           String original_title,
                           String overview,
                           int popularity,
                           String posterPath,
                           String releaseDate,
                           int revenue,
                           int runtime,
                           String status,
                           String tagline,
                           String title,
                           boolean video,
                           int voteAverage,
                           int voteCount) {
        super(adult,
                id,
                overview,
                posterPath,
                releaseDate,
                title,
                voteAverage);
    }
}
