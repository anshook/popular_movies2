package com.udacity.ak.popularmovies2.data;

public class MovieReview {
    private String mAuthor;
    private String mContent;


    public String getAuthor() {
        return mAuthor;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public MovieReview(String author, String content) {

        mAuthor = author;
        mContent = content;
    }

}
