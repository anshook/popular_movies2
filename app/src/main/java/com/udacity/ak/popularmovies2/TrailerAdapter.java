package com.udacity.ak.popularmovies2;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.udacity.ak.popularmovies2.data.MovieTrailer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {

    private static final String URI_YOUTUBE_APP = "vnd.youtube:";
    private static final String URI_YOUTUBE_WEB = "http://www.youtube.com/watch?v=";
    private List<MovieTrailer> mTrailerList;

    void setDataset(List<MovieTrailer> trailerList) {
        mTrailerList = trailerList;
        notifyDataSetChanged();
    }

    TrailerAdapter(List<MovieTrailer> trailerList) {
        this.mTrailerList = trailerList;
    }

    @Override
    public TrailerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_view, parent, false);
        return new TrailerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.ViewHolder holder, int position) {
        MovieTrailer trailer = mTrailerList.get(position);
        holder.mTrailerNameView.setText(trailer.getName());
        holder.mVideoKey = trailer.getVideoKey();
    }

    @Override
    public int getItemCount() {
        return mTrailerList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.ib_trailer_play) ImageButton mTrailerPlayButton;
        @BindView(R.id.tv_trailer_name) TextView mTrailerNameView;
        String mVideoKey;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mTrailerPlayButton.setOnClickListener(this);
            mTrailerNameView.setOnClickListener(this);
        }

        @Override
        public void onClick(View itemView) {
            launchTrailer(itemView.getContext(), mVideoKey);
        }

        private void launchTrailer(Context context, String videoKey) {
            Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URI_YOUTUBE_APP + videoKey));
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URI_YOUTUBE_WEB + videoKey));
            try {
                context.startActivity(appIntent);
            } catch (ActivityNotFoundException ex) {
                context.startActivity(browserIntent);
            }
        }
    }
}

