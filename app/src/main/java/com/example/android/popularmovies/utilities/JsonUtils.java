package com.example.android.popularmovies.utilities;

import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.model.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {


    public static Movie[] parseMovieJson(String json) throws JSONException {

        JSONObject movieDetails = new JSONObject(json);
        JSONArray moviesResults = movieDetails.getJSONArray("results");

        Movie[] movieArray = new Movie[moviesResults.length()];

        //loop through results array, create movie object and return in MovieArray
        for (int i = 0; i <moviesResults.length(); i++){

            JSONObject movieData = moviesResults.getJSONObject(i);
            movieArray[i] = createMovieObject(movieData);
        }

        return movieArray;
    }

    public static Review[] parseReviewJson(String json) throws JSONException {
        JSONObject allDetails = new JSONObject(json);
        JSONObject reviewsObj = allDetails.getJSONObject("reviews");
        JSONArray reviewResultsObj = reviewsObj.getJSONArray("results");

        Review[] reviewsArray = new Review[reviewResultsObj.length()];

        for (int i = 0; i < reviewResultsObj.length(); i++){
            JSONObject reviewData = reviewResultsObj.getJSONObject(i);
            reviewsArray[i] = createReviewObject(reviewData);
        }

        return reviewsArray;
    }

    private static Review createReviewObject(JSONObject reviewData){

        final String REVIEW_ID = "id";
        final String AUTHOR = "author";
        final String CONTENT = "content";
        final String URL_STRING = "url";

        Review review = new Review();

        review.setmId(reviewData.optString(REVIEW_ID));
        review.setmAuthor(reviewData.optString(AUTHOR));
        review.setmContent(reviewData.optString(CONTENT));
        review.setmUrl(reviewData.optString(URL_STRING));

        return review;


    }

    private static Movie createMovieObject(JSONObject movieData){
        //Constants for the OptStrings for easier reference and updates
        final String MOVIE_ID = "id";
        final String MOVIE_RATING = "rating";
        final String MOVIE_VOTE_AVG = "vote_average";
        final String MOVIE_TITLE = "title";
        final String MOVIE_POSTER = "poster_path";
        final String MOVIE_OVERVIEW = "overview";
        final String MOVIE_RELEASE_DATE = "release_date";

        Movie movie = new Movie();

        //sets movie data points
        movie.setmId(movieData.optInt(MOVIE_ID));
        movie.setmRating(movieData.optInt(MOVIE_RATING));
        movie.setmVoteAvg(movieData.optLong(MOVIE_VOTE_AVG));
        movie.setmTitle(movieData.optString(MOVIE_TITLE));
        movie.setmPoster(movieData.optString(MOVIE_POSTER));
        movie.setmDescription(movieData.optString(MOVIE_OVERVIEW));
        movie.setmReleaseDate(movieData.optString(MOVIE_RELEASE_DATE));

        return movie;

    }
}

