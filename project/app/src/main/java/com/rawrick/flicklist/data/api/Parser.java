package com.rawrick.flicklist.data.api;

import android.util.Log;

import com.rawrick.flicklist.data.account.Account;
import com.rawrick.flicklist.data.credits.Cast;
import com.rawrick.flicklist.data.movie.Movie;
import com.rawrick.flicklist.data.movie.MovieRated;
import com.rawrick.flicklist.data.movie.MovieTrending;
import com.rawrick.flicklist.data.movie.MovieWatchlisted;
import com.rawrick.flicklist.data.series.SeriesTrending;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Parser {

    private final static String img300 = "https://image.tmdb.org/t/p/w300";
    private final static String img500 = "https://image.tmdb.org/t/p/w500";

    public static String parseLoginToken(JSONObject response) {
        try {
            boolean success = response.getBoolean("success");
            if (!success) {
                Log.d("FlickListApp", "token creation failed");
                return null;
            }
            String expiration = response.getString("expires_at");
            String token = response.getString("request_token");
            return token;
        } catch (JSONException e) {
            Log.d("FlickListApp", "token parse error");
            e.printStackTrace();
        }
        return null;
    }

    public static String parseLoginSession(JSONObject response) {
        try {
            boolean success = response.getBoolean("success");
            if (!success) {
                Log.d("FlickListApp", "session creation failed");
                return null;
            }
            String id = response.getString("session_id");
            return id;
        } catch (JSONException e) {
            Log.d("FlickListApp", "session parse error");
            e.printStackTrace();
        }
        return null;
    }

    public static String parseLoginGuestSession(JSONObject response) {
        try {
            boolean success = response.getBoolean("success");
            if (!success) {
                Log.d("FlickListApp", "guest session creation failed");
                return null;
            }
            String expiration = response.getString("expires_at");
            String guest_session_id = response.getString("guest_session_id");
            return guest_session_id;
        } catch (JSONException e) {
            Log.d("FlickListApp", "token parse error");
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<MovieTrending> parseTrendingMovies(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray("results");
            ArrayList<MovieTrending> movieArrayList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject result = jsonArray.getJSONObject(i);

                String title = result.getString("title");
                double voteAverage = result.getDouble("vote_average");
                String overview = result.getString("overview");
                String releaseDate = result.getString("release_date");
                int id = result.getInt("id");
                boolean isAdult = result.getBoolean("adult");
                String backdropPath = result.getString("backdrop_path");
                String posterPath = result.getString("poster_path");
                int index = i;
                boolean isSelected;
                isSelected = i == 0;

                // unused
                int vote_count = result.getInt("vote_count");
                //JSONArray genre_ids = result.getJSONArray("genre_ids");
                boolean video = result.getBoolean("video");
                String original_language = result.getString("original_language");
                String original_title = result.getString("original_title");
                int popularity = result.getInt("popularity");
                String media_type = result.getString("media_type");

                String fullPosterPath = img500 + posterPath;
                String fullBackdropPath = img500 + backdropPath;
                movieArrayList.add(new MovieTrending(fullBackdropPath, index, isSelected, isAdult, id, overview, fullPosterPath, releaseDate, title, voteAverage));
            }
            return movieArrayList;
        } catch (JSONException e) {
            Log.d("FlickListApp", "movie trending parse error");
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<SeriesTrending> parseTrendingSeries(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray("results");
            ArrayList<SeriesTrending> seriesArrayList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject result = jsonArray.getJSONObject(i);

                String title = result.getString("name");
                double voteAverage = result.getDouble("vote_average");
                String overview = result.getString("overview");
                String releaseDate = result.getString("first_air_date");
                int id = result.getInt("id");
                String backdropPath = result.getString("backdrop_path");
                String posterPath = result.getString("poster_path");
                int index = i;
                boolean isSelected;
                isSelected = i == 0;

                String fullPosterPath = img500 + posterPath;
                String fullBackdropPath = img500 + backdropPath;
                seriesArrayList.add(new SeriesTrending(fullBackdropPath, index, isSelected, id, overview, fullPosterPath, releaseDate, title, voteAverage));
            }
            return seriesArrayList;
        } catch (JSONException e) {
            Log.d("FlickListApp", "series trending parse error");
            e.printStackTrace();
        }
        return null;
    }

    public static Account parseAccountData(JSONObject response) {
        try {
            JSONObject avatar = response.getJSONObject("avatar");

            JSONObject tmdb = avatar.getJSONObject("tmdb");
            String avatarPath = tmdb.getString("avatar_path");
            String avatarPathFull = img300 + avatarPath;

            JSONObject gravatar = avatar.getJSONObject("gravatar");
            String hash = gravatar.getString("hash");

            String id = String.valueOf(response.getInt("id"));
            Log.d("FlickListApp", "account id:" + id);
            String name = response.getString("name");
            String username = response.getString("username");
            String adult = String.valueOf(response.getBoolean("include_adult"));

            Account accountDetails = new Account(id, name, username, avatarPathFull, hash, adult);
            return accountDetails;
        } catch (JSONException e) {
            Log.d("FlickListApp", "account details parsing error");
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<MovieRated> parseRatedMoviesData(JSONObject response) {
        try {
            int pagesTotal = response.getInt("total_pages");
            JSONArray resultsArray = response.getJSONArray("results");

            ArrayList<MovieRated> ratedMovies = new ArrayList<>();
            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject result = resultsArray.getJSONObject(i);

                int id = result.getInt("id");
                double rating = result.getDouble("rating");
                String title = result.getString("title");
                String releaseDate = result.getString("release_date");
                String releaseYear = releaseDate.substring(0, 4);
                String posterPath = result.getString("poster_path");
                String fullPosterPath = img500 + posterPath;
                String backdropPath = result.getString("backdrop_path");
                String fullBackdropPath = img500 + backdropPath;


                Log.d("FlickListApp", "Parsing movie: " + title + ", with ID: " + id);

                MovieRated ratedMovie = new MovieRated(id, rating, title, releaseYear, fullPosterPath, fullBackdropPath, pagesTotal);
                ratedMovies.add(ratedMovie);
            }
            return ratedMovies;
        } catch (JSONException e) {
            Log.d("FlickListApp", "account details parsing error");
            e.printStackTrace();
        }
        return null;
    }


    public static ArrayList<MovieWatchlisted> parseWatchlistedMoviesData(JSONObject response) {
        try {
            int pagesTotal = response.getInt("total_pages");
            JSONArray resultsArray = response.getJSONArray("results");

            ArrayList<MovieWatchlisted> watchlistedMovies = new ArrayList<>();
            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject result = resultsArray.getJSONObject(i);

                int id = result.getInt("id");
                String title = result.getString("title");
                String releaseDate = result.getString("release_date");
                String releaseYear = releaseDate.substring(0, 4);
                String posterPath = result.getString("poster_path");
                String fullPosterPath = img500 + posterPath;

                Log.d("FlickListApp", "Parsing movie: " + title + ", with ID: " + id);

                MovieWatchlisted watchlistedMovie = new MovieWatchlisted(id, title, releaseYear, fullPosterPath, pagesTotal);
                watchlistedMovies.add(watchlistedMovie);
            }
            return watchlistedMovies;
        } catch (JSONException e) {
            Log.d("FlickListApp", "account details parsing error");
            e.printStackTrace();
        }
        return null;
    }

    public static Movie parseMovieData(JSONObject response) {
        try {
            boolean isAdult = response.getBoolean("adult");
            int id = response.getInt("id");
            String overview = response.getString("overview");
            String posterPath = response.getString("poster_path");
            String backdropPath = response.getString("backdrop_path");
            String releaseDate = response.getString("release_date");
            String title = response.getString("title");
            double voteAverage = response.getDouble("vote_average");
            int runtime = response.getInt("runtime");

            String fullPosterPath = img500 + posterPath;
            String fullBackdropPath = img500 + backdropPath;
            Movie movie = new Movie(isAdult, id, overview, fullPosterPath, fullBackdropPath, releaseDate, runtime, title, voteAverage);
            return movie;
        } catch (JSONException e) {
            Log.d("FlickListApp", "movie details parse error");
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Cast> parseMovieCastData(JSONObject response) {
        try {
            JSONArray castArray = response.getJSONArray("cast");

            ArrayList<Cast> cast = new ArrayList<>();
            for (int i = 0; i < castArray.length(); i++) {
                JSONObject result = castArray.getJSONObject(i);

                boolean adult = result.getBoolean("adult");
                int gender = result.getInt("gender");
                int id = result.getInt("id");
                String knownForDepartment = result.getString("known_for_department");
                String name = result.getString("name");
                String originalName = result.getString("original_name");
                double popularity = result.getDouble("popularity");
                String profilePath = result.getString("profile_path");
                int castID = result.getInt("cast_id");
                String character = result.getString("character");
                String creditID = result.getString("credit_id");
                int order = result.getInt("order");

                String fullProfilePath = img500 + profilePath;
                //Log.d("FlickListApp", "path for " + name + ": " + fullProfilePath);

                Cast movieCastMember = new Cast(adult, gender, id, knownForDepartment, name, originalName, popularity, fullProfilePath, castID, character, creditID, order);
                cast.add(movieCastMember);
            }
            return cast;
        } catch (JSONException e) {
            Log.d("FlickListApp", "movie cast parse error");
            e.printStackTrace();
        }
        return null;
    }

}
