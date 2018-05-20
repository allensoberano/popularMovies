package com.example.android.popularmovies.async;

import android.os.AsyncTask;

import com.example.android.popularmovies.MovieDetailActivity;
import com.example.android.popularmovies.model.Review;
import com.example.android.popularmovies.utilities.JsonUtils;
import com.example.android.popularmovies.utilities.NetworkUtils;

import java.net.URL;

//EXTRACT AsyncTask

public class ReviewQueryTask extends AsyncTask<URL, Void, Review[]> {

    private Review[] mReviewData;
    private final AsyncTaskCompleteListener<Review[]> reviewListener;

    public ReviewQueryTask(MovieDetailActivity.ReviewsCompleteListener listener) {
        reviewListener = listener;
    }

    @Override
    protected Review[] doInBackground(URL... urls) {
        URL searchUrl = urls[0];
        String movieSearchResults;
        try {
            movieSearchResults = NetworkUtils.getResponseFromHTTPUrl(searchUrl);
            mReviewData = JsonUtils.parseReviewJson(movieSearchResults);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mReviewData;
    }

    @Override
    protected void onPostExecute(Review[] reviews) {
        super.onPostExecute(reviews);
        reviewListener.onTaskComplete(mReviewData);
    }
}
