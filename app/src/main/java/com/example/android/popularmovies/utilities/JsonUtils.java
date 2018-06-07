package com.example.android.popularmovies.utilities;

import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.model.Review;
import com.example.android.popularmovies.model.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {


    private List<Movie> movieArray;

    public static List<Movie> parseMovieJson(String json) throws JSONException {

        JSONObject movieDetails = new JSONObject(json);
        JSONArray moviesResults = movieDetails.getJSONArray("results");

        //Movie[] movieArray = new Movie[moviesResults.length()];
        List<Movie> movieArray = new ArrayList<>();


        //loop through results array, create movie object and return in MovieArray
        for (int i = 0; i <moviesResults.length(); i++){

            JSONObject movieData = moviesResults.getJSONObject(i);
            movieArray.add(createMovieObject(movieData));
            //movieArray[i] = createMovieObject(movieData);
        }

        return movieArray;
    }

    public static Trailer[] parseTrailerJson(String json) throws JSONException {
        JSONObject allDetails = new JSONObject(json);
        JSONObject trailersObj = allDetails.getJSONObject("videos");
        JSONArray trailersResultsObj = trailersObj.getJSONArray("results");

        Trailer[] trailersArray = new Trailer[trailersResultsObj.length()];

        for (int i = 0; i <trailersResultsObj.length(); i++){
            JSONObject trailerData = trailersResultsObj.getJSONObject(i);
            trailersArray[i] = createTrailerObject(trailerData);
        }

        return trailersArray;
    }

    private static Trailer createTrailerObject(JSONObject trailerData){

        final String TRAILER_ID = "id";
        final String ISO = "iso_639_1";
        final String KEY = "key";
        final String NAME = "name";
        final String SITE = "site";
        final String SIZE = "size";
        final String TYPE = "type";

        Trailer trailer = new Trailer();

        trailer.setId(trailerData.optString(TRAILER_ID));
        trailer.setIso_639_1(trailerData.optString(ISO));
        trailer.setKey(trailerData.optString(KEY));
        trailer.setName(trailerData.optString(NAME));
        trailer.setSite(trailerData.optString(SITE));
        trailer.setSize(trailerData.optInt(SIZE));
        trailer.setType(trailerData.optString(TYPE));

        return trailer;
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

        final String AUTHOR = "author";
        final String CONTENT = "content";

        Review review = new Review();

        review.setmAuthor(reviewData.optString(AUTHOR));
        review.setmContent(reviewData.optString(CONTENT));

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

