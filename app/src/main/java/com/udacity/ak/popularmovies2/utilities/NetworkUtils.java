package com.udacity.ak.popularmovies2.utilities;


import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import com.udacity.ak.popularmovies2.BuildConfig;

/**
 * These utilities will be used to communicate with the weather servers.
 */
public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    //Replace the variable MOVIEDB_API_KEY with your key from themoviedb.org
    private static final String MOVIEDB_API_KEY = BuildConfig.MOVIEDB_API_KEY;

    private static final String QUERY_PARAM_KEY = "api_key";

    private static final String MOVIE_BASE_URL =
            "http://api.themoviedb.org/3/movie";

    private static final String REVIEWS_PATH = "reviews";
    private static final String VIDEOS_PATH = "videos";

    public final static String SORT_BY_POPULAR = "popular";
    public final static String SORT_BY_TOP_RATED = "top_rated";

    /**
     * Builds the URL used to fetch popular movies from TheMovieDB.
     *
     * @param sortBy sort options for movies - popular/top_rated.
     * @return The URL for TheMovieDB API call.
     */
    public static URL buildUrl(String sortBy) {
        Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendPath(sortBy)
                .appendQueryParameter(QUERY_PARAM_KEY, MOVIEDB_API_KEY).build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.e(TAG, "Error building URL:", e);
        }
        return url;
    }

    /**
     * Builds the URL used to fetch Movie reviews from TheMovieDB.
     *
     * @param id of the movie.
     * @return The URL for TheMovieDB API call.
     */
    public static URL buildReviewUrl(Long id) {
        Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendPath(id.toString())
                .appendPath(REVIEWS_PATH)
                .appendQueryParameter(QUERY_PARAM_KEY, MOVIEDB_API_KEY).build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.e(TAG, "Error building URL:", e);
        }
        return url;
    }

    /**
     * Builds the URL used to fetch Movie videos from TheMovieDB.
     *
     * @param id of the movie.
     * @return The URL for TheMovieDB API call.
     */
    public static URL buildVideoUrl(Long id) {
        Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendPath(id.toString())
                .appendPath(VIDEOS_PATH)
                .appendQueryParameter(QUERY_PARAM_KEY, MOVIEDB_API_KEY).build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.e(TAG, "Error building URL:", e);
        }
        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}

