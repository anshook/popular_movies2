package com.udacity.ak.popularmovies2.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.udacity.ak.popularmovies2.AppExecutors;
import com.udacity.ak.popularmovies2.data.database.FavoriteMovieDao;
import com.udacity.ak.popularmovies2.data.database.MovieDatabase;
import com.udacity.ak.popularmovies2.utilities.MovieJsonUtils;
import com.udacity.ak.popularmovies2.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieRepository {
    private static final String TAG = MovieRepository.class.getSimpleName();

    private static final Object LOCK = new Object();
    private static MovieRepository rInstance;
    private AppExecutors mExecutors;
    private MovieDatabase movieDB;
    private FavoriteMovieDao favoriteMovieDao;
    private LiveData<List<Movie>> listFavoriteMovies = new MutableLiveData<>();
    private MutableLiveData<List<Long>> listFavoriteMovieId = new MutableLiveData<>();
    private MutableLiveData<List<Movie>> listPopularMovies = new MutableLiveData<>();
    private MutableLiveData<List<Movie>> listTopRatedMovies = new MutableLiveData<>();

    private MovieRepository(Application application) {
        mExecutors = AppExecutors.getInstance();
        movieDB = MovieDatabase.getDatabase(application);
        favoriteMovieDao = movieDB.favoriteMovieDao();
        loadFavoriteMovies();
        loadPopularMovies();
        loadTopRatedMovies();
    }

    public synchronized static MovieRepository getInstance(
            Application application) {
        if (rInstance == null) {
            synchronized (LOCK) {
                rInstance = new MovieRepository(application);
            }
        }
        return rInstance;
    }

    public LiveData<List<Movie>> getFavoriteMovies() {
        return listFavoriteMovies;
    }

    public LiveData<List<Movie>> getPopularMovies() {
        return listPopularMovies;
    }

    public LiveData<List<Movie>> getTopRatedMovies() {
        return listTopRatedMovies;
    }

    public MutableLiveData<List<Long>> getAllFavoriteIds() {
        mExecutors.diskIO().execute(() -> {
            listFavoriteMovieId.postValue(favoriteMovieDao.getAllFavoriteIds());
        });
        return listFavoriteMovieId;
    }

    public void loadFavoriteMovies() {
        mExecutors.diskIO().execute(() -> {
            listFavoriteMovies = favoriteMovieDao.loadAllFavorites();
        });
    }

    public void deleteFavorite(Long id) {
        mExecutors.diskIO().execute(() -> {
            favoriteMovieDao.deleteById(id);
        });
    }

    public void addFavorite(Movie favorite) {
        mExecutors.diskIO().execute(() -> {
            long ret = favoriteMovieDao.insertFavorite(favorite);
        });
    }

    public void loadPopularMovies() {
        mExecutors.networkIO().execute(() -> {
            String httpResponse = null;
            try {
                URL url = NetworkUtils.buildUrl(NetworkUtils.SORT_BY_POPULAR);
                httpResponse = NetworkUtils.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                Log.e(TAG, "Error reading from server:", e);
            }

            List<Movie> movieList = new ArrayList<Movie>();
            try {
                if (httpResponse != null && !httpResponse.isEmpty()) {
                    movieList = MovieJsonUtils.getMovieListFromJson(httpResponse);
                }
                else {
                    Log.e(TAG, "Couldn't get json from server.");
                }
            } catch (JSONException e) {
                Log.e(TAG, "Json parsing error", e);
            }
            listPopularMovies.postValue(movieList);

        });
    }

    public void loadTopRatedMovies() {
        mExecutors.networkIO().execute(() -> {
            String httpResponse = null;
            try {
                URL url = NetworkUtils.buildUrl(NetworkUtils.SORT_BY_TOP_RATED);
                httpResponse = NetworkUtils.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                Log.e(TAG, "Error reading from server:", e);
            }

            List<Movie> movieList = new ArrayList<Movie>();
            try {
                if (httpResponse != null && !httpResponse.isEmpty()) {
                    movieList = MovieJsonUtils.getMovieListFromJson(httpResponse);
                }
                else {
                    Log.e(TAG, "Couldn't get json from server.");
                }
            } catch (JSONException e) {
                Log.e(TAG, "Json parsing error", e);
            }
            listTopRatedMovies.postValue(movieList);

        });
    }

    public MutableLiveData<List<MovieReview>> getMovieReviews(Long id) {
        MutableLiveData<List<MovieReview>> listMovieReviews = new MutableLiveData<>();
        mExecutors.networkIO().execute(() -> {
            String httpResponse = null;
            try {
                URL url = NetworkUtils.buildReviewUrl(id);
                httpResponse = NetworkUtils.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                Log.e(TAG, "Error reading from server:", e);
            }

            List<MovieReview> reviewList = new ArrayList<MovieReview>();
            try {
                if (httpResponse != null && !httpResponse.isEmpty()) {
                    reviewList = MovieJsonUtils.getReviewListFromJson(httpResponse);
                }
                else {
                    Log.e(TAG, "Couldn't get json from server.");
                }
            } catch (JSONException e) {
                Log.e(TAG, "Json parsing error", e);
            }
            listMovieReviews.postValue(reviewList);

        });
        return listMovieReviews;
    }

    public MutableLiveData<List<MovieTrailer>> getMovieTrailers(Long id) {
        MutableLiveData<List<MovieTrailer>> listMovieTrailers = new MutableLiveData<>();
        mExecutors.networkIO().execute(() -> {
            String httpResponse = null;
            try {
                URL url = NetworkUtils.buildVideoUrl(id);
                httpResponse = NetworkUtils.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                Log.e(TAG, "Error reading from server:", e);
            }

            List<MovieTrailer> trailerList = new ArrayList<MovieTrailer>();
            try {
                if (httpResponse != null && !httpResponse.isEmpty()) {
                    trailerList = MovieJsonUtils.getVideoListFromJson(httpResponse);
                }
                else {
                    Log.e(TAG, "Couldn't get json from server.");
                }
            } catch (JSONException e) {
                Log.e(TAG, "Json parsing error", e);
            }
            listMovieTrailers.postValue(trailerList);

        });
        return listMovieTrailers;
    }




}
