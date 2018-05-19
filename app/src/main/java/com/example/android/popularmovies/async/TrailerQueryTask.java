package com.example.android.popularmovies.async;

import android.os.AsyncTask;

import com.example.android.popularmovies.MovieDetailActivity;
import com.example.android.popularmovies.model.Trailer;
import com.example.android.popularmovies.utilities.JsonUtils;
import com.example.android.popularmovies.utilities.NetworkUtils;

import java.net.URL;

public class TrailerQueryTask extends AsyncTask<URL, Void, Trailer[]> {

    private Trailer[] mTrailerData;
    private AsyncTaskCompleteListener<Trailer[]> trailerListener;

    public TrailerQueryTask(MovieDetailActivity.TrailersCompleteListener listener){
       this.trailerListener = listener;

    }

    @Override
    protected Trailer[] doInBackground(URL... urls) {
        URL searchUrl = urls[0];
        String movieSearchResults;
        try {
            movieSearchResults = NetworkUtils.getResponseFromHTTPUrl(searchUrl);
            mTrailerData = JsonUtils.parseTrailerJson(movieSearchResults);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mTrailerData;
    }

    @Override
    protected void onPostExecute(Trailer[] trailer) {

        trailerListener.onTaskComplete(mTrailerData);

    }
}
