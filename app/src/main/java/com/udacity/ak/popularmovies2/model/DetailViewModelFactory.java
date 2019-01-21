package com.udacity.ak.popularmovies2.model;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.udacity.ak.popularmovies2.data.MovieRepository;

public class DetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final MovieRepository mRepository;
    private final Long movieId;

    public DetailViewModelFactory(MovieRepository repository, Long movieId) {
        this.mRepository = repository;
        this.movieId = movieId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new DetailActivityViewModel(mRepository, movieId);
    }

}
