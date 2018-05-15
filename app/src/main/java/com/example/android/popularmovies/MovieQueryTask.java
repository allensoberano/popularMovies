package com.example.android.popularmovies;

import android.os.AsyncTask;

import com.example.android.popularmovies.async.AsyncTaskCompleteListener;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.utilities.JsonUtils;
import com.example.android.popularmovies.utilities.NetworkUtils;

import java.net.URL;

//EXTRACT AsyncTask

public class MovieQueryTask extends AsyncTask<URL, Void, Movie[]> {

    private Movie[] mMovieData;
    private AsyncTaskCompleteListener<Movie[]> listener;

    public MovieQueryTask(MainActivity.MovieQueryTaskCompleteListener listener) {
        this.listener = listener;
    }

    @Override
    protected Movie[] doInBackground(URL... urls) {
        URL searchUrl = urls[0];
        String movieSearchResults;
        try {
            movieSearchResults = NetworkUtils.getResponseFromHTTPUrl(searchUrl);
            mMovieData = JsonUtils.parseMovieJson(movieSearchResults);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mMovieData;
    }

    @Override
    protected void onPostExecute(Movie[] movie) {

        listener.onTaskComplete(mMovieData);

    }
}
