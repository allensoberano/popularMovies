package com.example.android.popularmovies.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.popularmovies.data.MovieContract.MoviesFavorites;
import com.example.android.popularmovies.model.Movie;

import static android.content.ContentValues.TAG;

public class MovieDbHelper extends SQLiteOpenHelper {
    private static MovieDbHelper sInstance;

    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 3;

    //Constructor that just calls parent constructor
    private MovieDbHelper(Context context){
        //null represents a cursor factory that we won't need yet.
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //SQL Query to create our table for the first time
        final String SQL_CREATE_MOVIE_FAVORITES_TABLE = "CREATE TABLE " + MoviesFavorites.TABLE_NAME + " (" +
                MoviesFavorites._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MoviesFavorites.COLUMN_MOVIE_ID + " INTEGER NOT NULL," +
                MoviesFavorites.COLUMN_TITLE + " TEXT NOT NULL," +
                MoviesFavorites.COLUMN_POSTER + " TEXT," +
                MoviesFavorites.COLUMN_RELEASE_DATE + " TEXT," +
                MoviesFavorites.COLUMN_USER_RATING + " INTEGER" +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_FAVORITES_TABLE);
    }

    //Only called when we increment the version number
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MoviesFavorites.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public static synchronized MovieDbHelper getsInstance(Context context){
        //using application context which will ensure that we don't accidentally leak an activity's context
        //reference: http://bit.ly/6LRzfx
        //reference: https://guides.codepath.com/android/Local-Databases-with-SQLiteOpenHelper#inserting-new-records

        if (sInstance == null){
            sInstance = new MovieDbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public Movie[] getAllMovies(){
        SQLiteDatabase mDb = getWritableDatabase();

        Cursor cursor = mDb.query(MoviesFavorites.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                MoviesFavorites.COLUMN_RELEASE_DATE
                );

        Cursor mCursor = mDb.rawQuery("SELECT * FROM " + MoviesFavorites.TABLE_NAME, null);
        Movie[] mMovieData = new Movie[mCursor.getCount()];

        if (mCursor.moveToFirst()) {
            do {
                Movie movie = new Movie();

                movie.setmId(mCursor.getInt(mCursor.getColumnIndex(MoviesFavorites.COLUMN_MOVIE_ID)));
                movie.setmTitle(mCursor.getString(mCursor.getColumnIndex(MoviesFavorites.COLUMN_TITLE)));
                movie.setmPoster(mCursor.getString(mCursor.getColumnIndex(MoviesFavorites.COLUMN_POSTER)));
                movie.setmReleaseDate(mCursor.getString(mCursor.getColumnIndex(MoviesFavorites.COLUMN_RELEASE_DATE)));
                movie.setmVoteAvg(mCursor.getInt(mCursor.getColumnIndex(MoviesFavorites.COLUMN_USER_RATING)));

                mMovieData[mCursor.getPosition()] = movie;
            }while (mCursor.moveToNext());


        }
        //showMovies(mMovieData);
        return mMovieData;
    }
    public void addMovie(Movie movie){
        SQLiteDatabase mDb = getWritableDatabase();
        mDb.beginTransaction();
        try{
            ContentValues cv = new ContentValues();
            cv.put(MovieContract.MoviesFavorites.COLUMN_MOVIE_ID, movie.getmId());
            cv.put(MovieContract.MoviesFavorites.COLUMN_POSTER, movie.getmPoster());
            cv.put(MovieContract.MoviesFavorites.COLUMN_RELEASE_DATE, movie.getmReleaseDate());
            cv.put(MovieContract.MoviesFavorites.COLUMN_TITLE, movie.getmTitle());
            cv.put(MovieContract.MoviesFavorites.COLUMN_USER_RATING, movie.getmVoteAvg());

            mDb.insertOrThrow(MoviesFavorites.TABLE_NAME, null, cv);
            mDb.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while adding movie to favorites");
        } finally {
            mDb.endTransaction();
        }
    }

    public boolean queryMovie(int movieID){
        SQLiteDatabase mDb = getReadableDatabase();
        Cursor mCursor = mDb.query(MoviesFavorites.TABLE_NAME,
                null,
                MoviesFavorites.COLUMN_MOVIE_ID + " = " + String.valueOf(movieID),
                null,
                null,
                null,
                null
        );

        if (mCursor.moveToFirst()) {
            return true;
            } else {
            return false;
        }
    }



}
