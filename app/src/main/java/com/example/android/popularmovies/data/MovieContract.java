package com.example.android.popularmovies.data;

import android.provider.BaseColumns;

public class MovieContract {

    public static final class MoviesFavorites implements BaseColumns {
        //movie poster, synopsis, user rating, and release date, and display them even when offline.
        public static final String TABLE_NAME = "moviesFavorites";
        public static final String COLUMN_MOVIE_ID = "movieID";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTER = "poster";
        public static final String COLUMN_USER_RATING = "userRating";
        public static final String COLUMN_RELEASE_DATE = "releaseDate";
    }

}
