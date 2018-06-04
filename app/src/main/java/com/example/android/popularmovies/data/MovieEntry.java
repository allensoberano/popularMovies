package com.example.android.popularmovies.data;

//Entity Class for Database

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "favoriteMovies")
public class MovieEntry {

    @PrimaryKey(autoGenerate = true)
    private int _id;
    private int movieId;
    private String title;
    private String poster;
    private String releaseDate;
    private int rating;
    private String description;

    @Ignore
    public MovieEntry(int movieId, String title, String poster, String releaseDate, int rating, String description) {
        this.movieId = movieId;
        this.title = title;
        this.poster = poster;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.description = description;
    }

    public MovieEntry(int _id, int movieId, String title, String poster, String releaseDate, int rating, String description) {
        this._id = _id;
        this.movieId = movieId;
        this.title = title;
        this.poster = poster;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.description = description;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }





}
