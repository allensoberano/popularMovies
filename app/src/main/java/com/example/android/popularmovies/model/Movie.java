package com.example.android.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
public class Movie implements Parcelable {

    private int mId;
    private String mTitle;
    private String mPoster;
    private String mReleaseDate;
    private int mRating;
    private String mDescription;
    private float mVoteAvg;

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

    public int getmRating() { return mRating; }

    public void setmRating(int mRating) {
        this.mRating = mRating;
    }

    public float getmVoteAvg() {
        return mVoteAvg;
    }

    public void setmVoteAvg(float mVoteAvg) {
        this.mVoteAvg = mVoteAvg;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }


    //region Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    //Constructor
    public Movie() {

    }

    //Constructor
    //Using 'in' variable, we retrieve the values we originally wrote into the Parcel.
    //is private so only the CREATOR field can access
    private Movie(Parcel in) {
        mId = in.readInt();
        mTitle = in.readString();
        mPoster = in.readString();
        mReleaseDate = in.readString();
        mRating = in.readInt();
        mVoteAvg = in.readFloat();
        mDescription = in.readString();
    }

    //int flags used more for arrays
    //Values we want to save to the Parcel.
    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(mId);
        dest.writeString(mTitle);
        dest.writeString(mPoster);
        dest.writeString(mReleaseDate);
        dest.writeInt(mRating);
        dest.writeFloat(mVoteAvg);
        dest.writeString(mDescription);

    }

    //To Receive and Decode Parcel
    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    //endregion


}
