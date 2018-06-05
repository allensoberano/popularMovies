package com.example.android.popularmovies.data;

//Entity Class for Database

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "favoriteMovies")
class MovieEntry {

    @PrimaryKey(autoGenerate = true)
//    private int _id;
    private int movieId;
    private String title;
    private String poster;
    private String releaseDate;
    private int rating;
    private String description;

}
