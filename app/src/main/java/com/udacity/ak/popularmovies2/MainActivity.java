package com.udacity.ak.popularmovies2;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import com.udacity.ak.popularmovies2.data.Movie;
import com.udacity.ak.popularmovies2.data.MovieRepository;
import com.udacity.ak.popularmovies2.model.MainActivityViewModel;
import com.udacity.ak.popularmovies2.model.MainViewModelFactory;
import com.udacity.ak.popularmovies2.utilities.InternetCheck;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.gv_movies) GridView mMoviesGridView;
    @BindView(R.id.tv_empty) TextView mEmptyView;
    @BindView(R.id.spinner_movies_sort) Spinner mSortOptionsSpinner;

    private MainActivityViewModel mViewModel;
    private MovieRepository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mSortOptionsSpinner.setOnItemSelectedListener(this);

        // Set up view model
        mRepository = MovieRepository.getInstance(this.getApplication());
        MainViewModelFactory factory = new MainViewModelFactory(mRepository);
        mViewModel = ViewModelProviders.of(this, factory).get(MainActivityViewModel.class);

        mViewModel.getCurrentMovies().observe(this, movieList -> {
            MovieAdapter movieAdapter=new MovieAdapter(MainActivity.this, movieList);
            mMoviesGridView.setAdapter(movieAdapter);
            mMoviesGridView.setEmptyView(mEmptyView);

            mMoviesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Movie selectedMovie = (Movie)parent.getItemAtPosition(position);
                    launchMovieDetailActivity(selectedMovie);
                }
            });
        });

    }

    private AlertDialog.Builder buildDialog(Context ctx) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage(getString(R.string.no_connection));

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
               dialog.dismiss();
            }
        });
        return builder;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedSortOption = parent.getItemAtPosition(position).toString();
        new InternetCheck((Boolean internet) -> {
            if(!internet) {
                buildDialog(MainActivity.this).show();
            }
            else {
                if(selectedSortOption.equals(getString(R.string.sort_option_popular)))
                {
                    mViewModel.getPopularMovies().observe(this, list -> {
                        mViewModel.setPopularMovies();
                    });
                }
                else if(selectedSortOption.equals(getString(R.string.sort_option_top_rated)))
                {
                    mViewModel.getTopRatedMovies().observe(this, list -> {
                        mViewModel.setTopRatedMovies();
                    });
                }
                else if(selectedSortOption.equals(getString(R.string.sort_option_favorite))) {
                    mViewModel.getFavoriteMovies().observe(this, list -> {
                        mViewModel.setFavoriteMovies();
                    });
                }
            }
        });

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //do nothing
    }

    private void launchMovieDetailActivity(Movie selectedMovie) {
        Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.PARCEL_DATA, selectedMovie);
        startActivity(intent);
    }
}
