package com.example.android.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.popularmovies.data.MovieContract.MoviesFavorites;

public class MovieDbHelper extends SQLiteOpenHelper {
    private static MovieDbHelper sInstance;

    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 2;

    //Constructor that just calls parent constructor
    public MovieDbHelper(Context context){
        //null represents a cursor factory that we won't need yet.
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //SQL Query to create our table for the first time
        final String SQL_CREATE_MOVIE_FAVORITES_TABLE = "CREATE TABLE " + MoviesFavorites.TABLE_NAME + " (" +
                MoviesFavorites._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MoviesFavorites.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                MoviesFavorites.COLUMN_TITLE + " TEXT NOT NULL," +
                MoviesFavorites.COLUMN_POSTER + " TEXT," +
                MoviesFavorites.COLUMN_RELEASE_DATE + " TEXT," +
                MoviesFavorites.COLUMN_USER_RATING + " INTEGER" +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_FAVORITES_TABLE);


    }

    public static synchronized MovieDbHelper getsInstance(Context context){
        
    }

    //Only called when we increment the version number
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MoviesFavorites.TABLE_NAME);

    }

}
