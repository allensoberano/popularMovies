package com.example.android.popularmovies.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.android.popularmovies.model.Movie;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM favoriteMovies ORDER BY mReleaseDate")
    //List<MovieEntry> loadAllMovies();
    Movie[] loadAllMovies();

    @Query("SELECT * FROM favoriteMovies WHERE mId LIKE :mId")
    Movie queryMovieById(int mId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie movie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);


}
