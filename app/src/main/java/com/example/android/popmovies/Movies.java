package com.example.android.popmovies;

import java.util.ArrayList;

public class Movies {

    private String mImageUrl;
    private int mMovieId;
    private String mTitle;
    private String mOverview;
    private String mReleaseDate ;
    private String mRuntime;
    private String mVote_average;
    private ArrayList<String> mReviews;
    private ArrayList<String> mTrailers;

    public Movies() {
        mImageUrl = "";
        mMovieId = 0;
        mTitle = "";
        mOverview = "";
        mReleaseDate = "";
        mVote_average = "";
        mRuntime = "";
        mReviews = new ArrayList<>();
        mTrailers = new ArrayList<>();
    }

    public Movies(String mImageUrl, int mMovieId) {
        this.mImageUrl = mImageUrl;
        this.mMovieId = mMovieId;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public int getmMovieId() {
        return mMovieId;
    }

    public void setmMovieId(int mMovieId) {
        this.mMovieId = mMovieId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmOverview() {
        return mOverview;
    }

    public void setmOverview(String mOverview) {
        this.mOverview = mOverview;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public void setmReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    public String getmVote_average() {
        return mVote_average;
    }

    public void setmVote_average(String mVote_average) {
        this.mVote_average = mVote_average;
    }

    public ArrayList<String> getmReviews() {
        return mReviews;
    }

    public void setmReviews(ArrayList<String> mReviews) {
        this.mReviews = mReviews;
    }

    public ArrayList<String> getmTrailers() {
        return mTrailers;
    }

    public void setmTrailers(ArrayList<String> mTrailers) {
        this.mTrailers = mTrailers;
    }

    public String getmRuntime() {
        return mRuntime;
    }

    public void setmRuntime(String mRuntime) {
        this.mRuntime = mRuntime;
    }

    @Override
    public boolean equals(Object obj){

        if(this instanceof Movies){

            Movies other = (Movies) obj;

            return this.getmMovieId() == other.getmMovieId();
        }

        return false;
    }

}
