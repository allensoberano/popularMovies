package com.example.android.popularmovies.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
@Entity(tableName = "favoriteMovies")
public class Movie implements Parcelable {


    @PrimaryKey(autoGenerate = false)
    public int mId;
    public String mTitle;
    public String mPoster;
    public String mReleaseDate;
    public int mRating;
    public String mDescription;

    @Ignore
    private float mVoteAvg;


    public Movie(int mId, String mTitle, String mPoster, String mReleaseDate, int mRating, String mDescription) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mPoster = mPoster;
        this.mReleaseDate = mReleaseDate;
        this.mRating = mRating;
        this.mDescription = mDescription;
    }

//    public Movie(int _id, int mId, String mTitle, String mPoster, String mReleaseDate, int mRating, String mDescription) {
//        this._id = _id;
//        this.mId = mId;
//        this.mTitle = mTitle;
//        this.mPoster = mPoster;
//        this.mReleaseDate = mReleaseDate;
//        this.mRating = mRating;
//        this.mDescription = mDescription;
//    }

    //Constructor
    @Ignore
    //just a test
    public Movie() {

    }


//    public int get_id() {
//        return _id;
//    }
//
//    public void set_id(int _id) {
//        this._id = _id;
//    }

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


    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public float getmVoteAvg() {
        return mVoteAvg;
    }

    public void setmVoteAvg(float mVoteAvg) {
        this.mVoteAvg = mVoteAvg;
    }


    //region Parcelable
    @Override
    public int describeContents() {
        return 0;
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
