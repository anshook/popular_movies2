package com.udacity.ak.popularmovies2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.ak.popularmovies2.data.MovieReview;

import java.util.List;


public class ReviewAdapter extends
        RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private List<MovieReview> mReviewList;


    void setDataset(List<MovieReview> reviewList) {
        mReviewList = reviewList;
        notifyDataSetChanged();
    }

    ReviewAdapter(List<MovieReview> reviewList) {
        this.mReviewList = reviewList;
    }

    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_view, parent, false);
        return new ReviewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ViewHolder holder, int position) {
        MovieReview review = mReviewList.get(position);
        String author = review.getAuthor();
        String content = review.getContent();
        holder.mAuthorTextView.setText(author);
        holder.mContentTextView.setText(content);
    }

    @Override
    public int getItemCount() {
        return mReviewList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mAuthorTextView;
        TextView mContentTextView;

        ViewHolder(View itemView) {
            super(itemView);
            mAuthorTextView = itemView.findViewById(R.id.author_tv);
            mContentTextView = itemView.findViewById(R.id.content_tv);
        }
    }
}
