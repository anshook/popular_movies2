package com.udacity.ak.popularmovies2.model;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.udacity.ak.popularmovies2.data.Movie;
import com.udacity.ak.popularmovies2.data.MovieRepository;
import com.udacity.ak.popularmovies2.data.MovieReview;
import com.udacity.ak.popularmovies2.data.MovieTrailer;

import java.util.ArrayList;
import java.util.List;

public class DetailActivityViewModel extends ViewModel {

    private MovieRepository mRepository;
    private MutableLiveData<List<Long>> mListFavoriteMovieIds;
    private LiveData<List<MovieReview>> mListReviews;
    private LiveData<List<MovieTrailer>> mListTrailers;

    public DetailActivityViewModel(MovieRepository repository, Long id) {
        mRepository = repository;
        mListFavoriteMovieIds = mRepository.getAllFavoriteIds();
        mListReviews =  mRepository.getMovieReviews(id);
        mListTrailers = mRepository.getMovieTrailers(id);
    }

    public LiveData<List<MovieReview>> getMovieReviews() { return mListReviews;}

    public LiveData<List<MovieTrailer>> getMovieTrailers() { return mListTrailers;}

    public LiveData<List<Long>> getFavoriteMovieIds() {
        if(mListFavoriteMovieIds.getValue() == null) {
            mListFavoriteMovieIds.setValue(new ArrayList<>());
        }
        return mListFavoriteMovieIds;
    }

    public void addFavorite(Movie favorite) {
        mRepository.addFavorite(favorite);
    }

    public void removeFavorite(Long id) {
        mRepository.deleteFavorite(id);
    }

}

