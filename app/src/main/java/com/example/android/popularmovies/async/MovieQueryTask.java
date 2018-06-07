package com.example.android.popularmovies.async;

import android.os.AsyncTask;

import com.example.android.popularmovies.MainActivity;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.utilities.JsonUtils;
import com.example.android.popularmovies.utilities.NetworkUtils;

import java.net.URL;
import java.util.List;

//EXTRACT AsyncTask

public class MovieQueryTask extends AsyncTask<URL, Void, List<Movie>> {

    private List<Movie> mMovieData;
    private final AsyncTaskCompleteListener<List<Movie>> listener;

    public MovieQueryTask(MainActivity.MovieQueryTaskCompleteListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<Movie> doInBackground(URL... urls) {
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
    protected void onPostExecute(List<Movie> movie) {

        listener.onTaskComplete(mMovieData);

    }
}
