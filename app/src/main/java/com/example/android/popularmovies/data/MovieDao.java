package com.example.android.popularmovies.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.android.popularmovies.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM favoriteMovies ORDER BY mReleaseDate")
    LiveData<List<Movie>> loadAllMovies();


    @Query("SELECT * FROM favoriteMovies WHERE mId LIKE :mId")
    Movie queryMovieById(int mId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);


}
