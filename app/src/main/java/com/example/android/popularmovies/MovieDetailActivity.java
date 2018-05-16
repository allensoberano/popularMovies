package com.example.android.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.android.popularmovies.adapters.ReviewRVAdapter;
import com.example.android.popularmovies.async.AsyncTaskCompleteListener;
import com.example.android.popularmovies.async.ReviewQueryTask;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.model.Review;
import com.example.android.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MovieDetailActivity extends AppCompatActivity {

    private Movie mMovieSent;
    private int mId;
    private String appendPath;
    private Review[] mReviewData;
    private RecyclerView mReviewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        mMovieSent = getIntent().getParcelableExtra("movie");

        mId = mMovieSent.getmId();
        appendPath = mId + "/reviews";

        ReviewRVAdapter mAdapter;

        //Ref to RecyclerView from XML. Allows us to set the adapter of RV and toggle visibility.
        mReviewList = findViewById(R.id.rv_reviews);
        mReviewList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ReviewRVAdapter(this, mReviewData);
        mReviewList.setHasFixedSize(true);
        mReviewList.setAdapter(mAdapter);

        //Build Search Query
        URL movieSearchUrl = NetworkUtils.buildUrl(appendPath);
        //Run Query
        new ReviewQueryTask(new ReviewsCompleteListener()).execute(movieSearchUrl);


        populateDetailActivity(mMovieSent);

    }

    //region AsyncTask Listener
    public class ReviewsCompleteListener implements AsyncTaskCompleteListener<Review[]> {

        @Override
        public void onTaskComplete(Review[] result) {
            mReviewData = result;
            showReviews(mReviewData);
            //showMovies(mMovieData);
        }
    }
    //endregion


    //populates UI activity with data
    private void populateDetailActivity(Movie movieSent){
        ImageView mMoviePoster = findViewById(R.id.iv_movie_image);
        TextView mMovieTitle = findViewById(R.id.tv_movie_title);
        RatingBar mRating = findViewById(R.id.rb_rating);
        TextView mReleaseDate = findViewById(R.id.tv_release_date);
        TextView mDescription = findViewById(R.id.tv_description);


        mMovieTitle.setText(movieSent.getmTitle());
        mRating.setRating(movieSent.getmVoteAvg()/2);
        mReleaseDate.setText(convertDate(movieSent.getmReleaseDate()));
        mDescription.setText(movieSent.getmDescription());

        String POSTER_PATH = NetworkUtils.posterURL();

        Picasso.with(this)
                .load(POSTER_PATH.concat(movieSent.getmPoster()))
                .into(mMoviePoster);

    }

    //Converts the date to more familiar format
    //*Reference: Stack Overflow: https://stackoverflow.com/questions/9277747/android-simpledateformat-how-to-use-it
    private String convertDate(String dateToConvert){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date date = null;
        try {
            date = fmt.parse(dateToConvert);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat fmtOut = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
        return fmtOut.format(date);
    }

    private void showReviews(Review[] review){
        ReviewRVAdapter reviewRVAdapater = new ReviewRVAdapter(MovieDetailActivity.this, review);
        reviewRVAdapater.notifyDataSetChanged();
    }

}
