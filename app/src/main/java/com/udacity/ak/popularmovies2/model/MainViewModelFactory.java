package com.udacity.ak.popularmovies2.model;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.udacity.ak.popularmovies2.data.MovieRepository;
import com.udacity.ak.popularmovies2.model.MainActivityViewModel;

public class MainViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final MovieRepository mRepository;

    public MainViewModelFactory(MovieRepository repository) {
        this.mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MainActivityViewModel(mRepository);
    }
}
