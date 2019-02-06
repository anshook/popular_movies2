package com.udacity.ak.popularmovies2;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.TooltipCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;
import com.udacity.ak.popularmovies2.data.Movie;
import com.udacity.ak.popularmovies2.data.MovieRepository;
import com.udacity.ak.popularmovies2.model.DetailActivityViewModel;
import com.udacity.ak.popularmovies2.model.DetailViewModelFactory;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String PARCEL_DATA = "parcel_data";
    private static final String MOVIE_POSTER_BASE_URL =
            "http://image.tmdb.org/t/p/w185";

    @BindView(R.id.poster_iv) ImageView mPosterImageView;
    @BindView(R.id.original_title_tv) TextView mTitleText;
    @BindView(R.id.synopsis_tv) TextView mSynopsisText;
    @BindView(R.id.rating_tv) TextView mRatingText;
    @BindView(R.id.release_date_tv) TextView mReleaseDateText;
    @BindView(R.id.favorite_tb) ToggleButton mFavoriteToggleButton;
    @BindString(R.string.favorite_button_tooltip_text) String sAddFavoriteTooltip;
    @BindString(R.string.unfavorite_button_tooltip_text) String sRemFavoriteTooltip;
    @BindView(R.id.review_view) RecyclerView mReviewRecyclerView;
    @BindView(R.id.trailer_view) RecyclerView mTrailerRecyclerView;
    @BindView(R.id.tv_no_reviews) TextView mReviewEmptyView;
    @BindView(R.id.tv_no_trailers) TextView mTrailerEmptyView;


    Movie movie;
    private DetailActivityViewModel mViewModel;
    private MovieRepository mRepository;
    private ReviewAdapter mReviewAdapter;
    private TrailerAdapter mTrailerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        movie = (Movie) getIntent().getParcelableExtra(PARCEL_DATA);

        // Set up view model
        mRepository = MovieRepository.getInstance(this.getApplication());
        DetailViewModelFactory factory = new DetailViewModelFactory(mRepository, movie.getId());
        mViewModel = ViewModelProviders.of(this, factory).get(DetailActivityViewModel.class);

        mViewModel.getFavoriteMovieIds().observe(this, favIdList -> {
            Boolean isFavorite = false;
            if(favIdList!=null && favIdList.contains(movie.getId())) {
                isFavorite = true;
            }
            mFavoriteToggleButton.setChecked(isFavorite);
            setFavoriteBtnTooltip(isFavorite);

            mFavoriteToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        mViewModel.addFavorite(movie);
                    } else {
                        mViewModel.removeFavorite(movie.getId());
                    }
                    setFavoriteBtnTooltip(isChecked);
                }
            });
        });

        mViewModel.getMovieReviews().observe(this, reviewList -> {
            mReviewAdapter = new ReviewAdapter(reviewList);
            mReviewRecyclerView.setAdapter(mReviewAdapter);
            mReviewRecyclerView.setHasFixedSize(true);
            mReviewRecyclerView.setNestedScrollingEnabled(false);

            if(mReviewAdapter.getItemCount()==0){
                mReviewEmptyView.setVisibility(View.VISIBLE);
            }
            else {
                mReviewEmptyView.setVisibility(View.GONE);
            }
        });

        mViewModel.getMovieTrailers().observe(this, trailerList -> {
            mTrailerAdapter = new TrailerAdapter(trailerList);
            mTrailerRecyclerView.setAdapter(mTrailerAdapter);
            mTrailerRecyclerView.setHasFixedSize(true);
            mTrailerRecyclerView.setNestedScrollingEnabled(false);

            if(mTrailerAdapter.getItemCount()==0){
                mTrailerEmptyView.setVisibility(View.VISIBLE);
            }
            else {
                mTrailerEmptyView.setVisibility(View.GONE);
            }
        });

        populateUI();
    }

    private void setFavoriteBtnTooltip(Boolean isFavorite){
        String tButtonTooltip = sAddFavoriteTooltip;
        if(isFavorite){
            tButtonTooltip = sRemFavoriteTooltip;
        }

        if (Build.VERSION.SDK_INT >= 26) {
            mFavoriteToggleButton.setTooltipText(tButtonTooltip);
        }
        else {
            TooltipCompat.setTooltipText(mFavoriteToggleButton, tButtonTooltip);
        }
    }

    private void populateUI() {
        Picasso.with(this).load(MOVIE_POSTER_BASE_URL+movie.getPosterImagePath())
                .placeholder(R.drawable.ic_wait)
                .error(R.drawable.error)
                .into(mPosterImageView);

        mTitleText.setText(replaceEmptyValue(movie.getOriginalTitle()));
        mSynopsisText.setText(replaceEmptyValue(movie.getSynopsis()));
        mRatingText.setText(replaceEmptyValue(movie.getUserRating()));
        mReleaseDateText.setText(replaceEmptyValue(movie.getReleaseDate()));
    }

    private String replaceEmptyValue(String value)
    {
        if(value==null || value.trim().equals(""))
            return getResources().getString(R.string.blank_movie_detail);

        return value;
    }
}
