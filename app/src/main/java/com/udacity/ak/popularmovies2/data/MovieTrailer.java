package com.udacity.ak.popularmovies2.data;

public class MovieTrailer {
    private String mName;
    private String mVideoKey;

    public MovieTrailer(String name, String videoKey) {
        mName = name;
        mVideoKey = videoKey;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getVideoKey() {
        return mVideoKey;
    }
}
