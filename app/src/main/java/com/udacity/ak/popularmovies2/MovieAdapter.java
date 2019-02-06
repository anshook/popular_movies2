package com.udacity.ak.popularmovies2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.udacity.ak.popularmovies2.data.Movie;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {
    private static final String MOVIE_POSTER_BASE_URL =
            "http://image.tmdb.org/t/p/w185";

    public MovieAdapter(@NonNull Context context, @NonNull List<Movie> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Movie movie = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.gridview_list, parent, false);
        }

        ImageView moviePosterView = (ImageView) convertView.findViewById(R.id.iv_grid_poster);
        Picasso.with(getContext()).load(MOVIE_POSTER_BASE_URL+movie.getPosterImagePath())
                .placeholder(R.drawable.ic_wait)
                .error(R.drawable.error)
                .into(moviePosterView);

        return convertView;
    }
}
