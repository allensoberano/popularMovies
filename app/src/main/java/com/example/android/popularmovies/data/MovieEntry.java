package com.example.android.popularmovies.data;

//Entity Class for Database

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

//
@Entity(tableName = "favoriteMovies")
public class MovieEntry {

    @PrimaryKey(autoGenerate = true)
//    private int _id;
    private int mId;
    private String mTitle;
    private String mPoster;
    private String mReleaseDate;
    private int mRating;

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmPoster() {
        return mPoster;
    }

    public void setmPoster(String mPoster) {
        this.mPoster = mPoster;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public void setmReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    public int getmRating() {
        return mRating;
    }

    public void setmRating(int mRating) {
        this.mRating = mRating;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    private String mDescription;




//    private int movieId;
//    private String title;
//    private String poster;
//    private String releaseDate;
//    private int rating;
//    private String description;


}
