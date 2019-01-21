package com.udacity.ak.popularmovies2.utilities;

import android.content.Context;

import com.udacity.ak.popularmovies2.data.Movie;
import com.udacity.ak.popularmovies2.data.MovieReview;
import com.udacity.ak.popularmovies2.data.MovieTrailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility functions to handle TheMovieDb JSON data.
 */

public final class MovieJsonUtils {

    private static String N_RESULTS = "results";
    private static String N_ID = "id";
    private static String N_TITLE = "original_title";
    private static String N_VOTE_AVG = "vote_average";
    private static String N_POSTER_PATH = "poster_path";
    private static String N_OVERVIEW = "overview";
    private static String N_RELEASE_DT = "release_date";
    private static String N_CONTENT = "content";
    private static String N_AUTHOR = "author";
    private static String N_NAME = "name";
    private static String N_KEY = "key";


    /**
     * Parse the JSON and convert it into Movie objects.
     *
     * @param moviesJsonStr The JSON to parse into Movie ArrayList.
     *
     * @return A List of Movie objects parsed from the JSON.
     */
    public static List<Movie> getMovieListFromJson(String moviesJsonStr) throws JSONException{
        DecimalFormat df = new DecimalFormat("0.0#");
        if (moviesJsonStr != null) {
            List<Movie> movieList = new ArrayList<Movie>();
            JSONObject jsonObj = new JSONObject(moviesJsonStr);

            // Getting JSON Array node
            JSONArray movies = jsonObj.getJSONArray(N_RESULTS);

            // looping through All Movies
            for (int i = 0; i < movies.length(); i++) {
                JSONObject m = movies.getJSONObject(i);
                Movie movieObj = new Movie(m.getLong(N_ID));
                movieObj.setUserRating(df.format(m.getDouble(N_VOTE_AVG)));
                movieObj.setPosterImagePath(m.getString(N_POSTER_PATH));
                movieObj.setOriginalTitle(m.getString(N_TITLE));
                movieObj.setSynopsis(m.getString(N_OVERVIEW));
                movieObj.setReleaseDate(m.getString(N_RELEASE_DT));

                movieList.add(movieObj);;
            }
            return movieList;
        }
        return null;
    }


    public static List<MovieReview> getReviewListFromJson(String reviewsJsonStr) throws JSONException{
        if (reviewsJsonStr != null) {
            List<MovieReview> reviewList = new ArrayList<>();
            JSONObject jsonObj = new JSONObject(reviewsJsonStr);

            JSONArray reviews = jsonObj.getJSONArray(N_RESULTS);

            for (int i = 0; i < reviews.length(); i++) {
                JSONObject r = reviews.getJSONObject(i);
                String author = r.getString(N_AUTHOR);
                String content = r.getString(N_CONTENT);
                MovieReview reviewObj = new MovieReview(author, content);
                reviewList.add(reviewObj);
            }
            return reviewList;
        }
        return null;
    }

    public static List<MovieTrailer> getVideoListFromJson(String videosJsonStr) throws JSONException{
        if (videosJsonStr != null) {
            List<MovieTrailer> videoList = new ArrayList<>();
            JSONObject jsonObj = new JSONObject(videosJsonStr);

            JSONArray videos = jsonObj.getJSONArray(N_RESULTS);

            for (int i = 0; i < videos.length(); i++) {
                JSONObject v = videos.getJSONObject(i);
                String name = v.getString(N_NAME);
                String videoKey = v.getString(N_KEY);
                MovieTrailer trailerObj = new MovieTrailer(name, videoKey);
                videoList.add(trailerObj);
            }
            return videoList;
        }
        return null;
    }
}
