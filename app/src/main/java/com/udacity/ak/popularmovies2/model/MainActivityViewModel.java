package com.udacity.ak.popularmovies2.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.udacity.ak.popularmovies2.data.Movie;
import com.udacity.ak.popularmovies2.data.MovieRepository;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private MovieRepository mRepository;
    private MutableLiveData<List<Movie>> mListDisplayMovies;
    private LiveData<List<Movie>> mListFavoriteMovies;
    private LiveData<List<Movie>> mListPopularMovies;
    private LiveData<List<Movie>> mListTopRatedMovies;

    public MainActivityViewModel(MovieRepository repository) {
        mRepository = repository;

        mListDisplayMovies = new MutableLiveData<>();
        mListFavoriteMovies = mRepository.getFavoriteMovies();
        mListPopularMovies =  mRepository.getPopularMovies();
        mListTopRatedMovies = mRepository.getTopRatedMovies();
    }

    public LiveData<List<Movie>> getCurrentMovies() { return mListDisplayMovies;}

    public LiveData<List<Movie>> getPopularMovies() { return mListPopularMovies;}

    public LiveData<List<Movie>> getTopRatedMovies() { return mListTopRatedMovies;}

    public LiveData<List<Movie>> getFavoriteMovies() { return mListFavoriteMovies;}

    public void setFavoriteMovies() {
        if(mListFavoriteMovies.getValue() != null) {
            mListDisplayMovies.setValue(mListFavoriteMovies.getValue());
        }
        else {
            mListDisplayMovies.setValue(new ArrayList<>());
        }
    }

    public void setPopularMovies() {
        if(mListPopularMovies.getValue() != null) {
            mListDisplayMovies.setValue(mListPopularMovies.getValue());
        }
        else {
            mListDisplayMovies.setValue(new ArrayList<>());
        }
    }

    public void setTopRatedMovies() {
        if(mListTopRatedMovies.getValue() != null) {
            mListDisplayMovies.setValue(mListTopRatedMovies.getValue());
        }
        else {
            mListDisplayMovies.setValue(new ArrayList<>());
        }
    }
}
