package com.rawrick.flicklist.data.api;

public class URL {

    // Images
    private final static String imgURL = "https://image.tmdb.org/t/p/";
    public final static String backdrop300 = imgURL + "w300";
    public final static String backdrop700 = imgURL + "w780";
    public final static String backdrop1280 = imgURL + "w1280";
    public final static String backdropOriginal = imgURL + "original";
    public final static String poster92 = imgURL + "w92";
    public final static String poster154 = imgURL + "w154";
    public final static String poster185 = imgURL + "w185";
    public final static String poster342 = imgURL + "w342";
    public final static String poster500 = imgURL + "w500";
    public final static String poster780 = imgURL + "w780";
    public final static String posterOriginal = imgURL + "original";
    public final static String profile45 = imgURL + "w45";
    public final static String profile185 = imgURL + "w185";
    public final static String profile632 = imgURL + "h632";
    public final static String profileOriginal = imgURL + "original";


    public static final String trendingMoviesWeekURL = "https://api.themoviedb.org/3/trending/movie/week";
    public static final String trendingMoviesDayURL = "https://api.themoviedb.org/3/trending/movie/day";
    public static final String trendingSeriesWeekURL = "https://api.themoviedb.org/3/trending/tv/week";
    public static final String trendingSeriesDayURL = "https://api.themoviedb.org/3/trending/tv/day";

    public static final String movieURL = "https://api.themoviedb.org/3/movie/";

    public static final String authenticationTokenNew = "https://api.themoviedb.org/3/authentication/token/new";
    public static final String authenticationSessionNew = "https://api.themoviedb.org/3/authentication/session/new";
    public static final String authenticationSessionGuestNew = "https://api.themoviedb.org/3/authentication/guest_session/new";

    public static final String accountURL = "https://api.themoviedb.org/3/account";
}
