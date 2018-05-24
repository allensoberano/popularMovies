package com.example.android.popularmovies;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.android.popularmovies.adapters.ReviewRVAdapter;
import com.example.android.popularmovies.adapters.TrailerRVAdapter;
import com.example.android.popularmovies.async.AsyncTaskCompleteListener;
import com.example.android.popularmovies.async.ReviewQueryTask;
import com.example.android.popularmovies.async.TrailerQueryTask;
import com.example.android.popularmovies.data.MovieContract;
import com.example.android.popularmovies.data.MovieContract.MoviesFavorites;
import com.example.android.popularmovies.data.MovieDbHelper;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.model.Review;
import com.example.android.popularmovies.model.Trailer;
import com.example.android.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.support.v7.widget.RecyclerView.VERTICAL;

public class MovieDetailActivity extends AppCompatActivity implements TrailerRVAdapter.ItemClickListener {

    private Review[] mReviewData;
    private RecyclerView mReviewList;
    private Trailer[] mTrailerData;
    private RecyclerView mTrailerList;
    private Boolean mFavorited = false;
    private Movie mMovieSent;
    private SQLiteDatabase mDb;
    private Movie[] mMovieData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        mMovieSent = getIntent().getParcelableExtra("movie");
        int mId = mMovieSent.getmId();

        //Build Search Query
        URL movieSearchUrl = NetworkUtils.buildReviewsTrailersURL(mId);

        //region Trailer Adapter
        TrailerRVAdapter mTrailerAdapter;
        mTrailerList = findViewById(R.id.rv_trailers);

        mTrailerList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mTrailerAdapter = new TrailerRVAdapter(mTrailerData, this);
        mTrailerList.setHasFixedSize(true);
        mTrailerList.setAdapter(mTrailerAdapter);

        new TrailerQueryTask(new TrailersCompleteListener()).execute(movieSearchUrl);
        //endregion

        //region Review Adapter
        ReviewRVAdapter mReviewAdapter;

        //Ref to RecyclerView from XML. Allows us to set the adapter of RV and toggle visibility.
        mReviewList = findViewById(R.id.rv_reviews);
        mReviewList.setLayoutManager(new LinearLayoutManager(this));
        mReviewAdapter = new ReviewRVAdapter(mReviewData);
        mReviewList.setHasFixedSize(true);
        mReviewList.setAdapter(mReviewAdapter);
        //endregion

        //Run Query
        new ReviewQueryTask(new ReviewsCompleteListener()).execute(movieSearchUrl);

        populateDetailActivity(mMovieSent);
        MovieDbHelper dbHelper = new MovieDbHelper(this);
        mDb = dbHelper.getWritableDatabase();
        //Cursor cursor = getAllFavoriteMovies();



        //REFERENCE: Android Documentation: https://developer.android.com/guide/topics/ui/floating-action-button
        final FloatingActionButton fab = findViewById(R.id.fab_fav);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setmFavorited(view, fab);

            }
        });


    }


    @Override
    public void onItemClick(int position) {
        //put intent here
        viewTrailerIntent(position);

    }

    //region AsyncTask Listener Reviews
    public class ReviewsCompleteListener implements AsyncTaskCompleteListener<Review[]> {

        @Override
        public void onTaskComplete(Review[] result) {
            mReviewData = result;
            showReviews(mReviewData);
        }
    }
    //endregion

    //region AsyncTask Listener Trailers
    public class TrailersCompleteListener implements AsyncTaskCompleteListener<Trailer[]> {

        @Override
        public void onTaskComplete(Trailer[] result) {
            mTrailerData = result;
            showTrailers(mTrailerData);
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

        Picasso.get()
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
        ReviewRVAdapter reviewRVAdapter = new ReviewRVAdapter(review);
        mReviewList.setAdapter(reviewRVAdapter);
        addDivider();
        reviewRVAdapter.notifyDataSetChanged();

        //display number of reviews. So if there aren't any, it won't look like they are missing. Just shows "0 Reviews"
        TextView mReviewLabel = findViewById(R.id.tv_review_label);
        String reviewLabel = mReviewData.length + " Reviews";
        mReviewLabel.setText(reviewLabel);

    }

    private void addDivider(){
        DividerItemDecoration itemDecor = new DividerItemDecoration(mReviewList.getContext(), VERTICAL);
        mReviewList.addItemDecoration(itemDecor);

    }

    //region Trailers
    private void showTrailers(Trailer[] trailer){
        TrailerRVAdapter trailerRVAdapter = new TrailerRVAdapter(trailer, MovieDetailActivity.this);
        mTrailerList.setAdapter(trailerRVAdapter);
        trailerRVAdapter.notifyDataSetChanged();

    }

    private void viewTrailerIntent(int position) {

        String YOUTUBE_PACKAGE = "com.google.android.youtube";
        String movieKey = mTrailerData[position].getKey();

        URL youtubeURL = NetworkUtils.buildYoutubeURL(movieKey);
        Intent trailerToView = new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(youtubeURL)));
        trailerToView.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        trailerToView.setPackage(YOUTUBE_PACKAGE);
        startActivity(trailerToView);
    }
    //endregion

    //region Floating Action Button
    private void setmFavorited(View view, FloatingActionButton fab){
        if (mFavorited){
            //unfavorited
            mFavorited = false;
            fab.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            snackbarAlert(view,"Favorite Removed");
            fabColor(fab);

        } else {
            //favorited
            mFavorited = true;
            fab.setImageResource(R.drawable.ic_favorite_black_24dp);
            snackbarAlert(view,"Movie Favorited");
            fabColor(fab);
            addMovie(mMovieSent);
        }
    }

    private void fabColor(FloatingActionButton fab){
        if (mFavorited){
            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
        } else {
            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryDark)));
        }
    }

    private void snackbarAlert(View view, String message){
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }
    //endregion



    public long addMovie(Movie movie){

        ContentValues cv = new ContentValues();
        cv.put(MovieContract.MoviesFavorites.COLUMN_MOVIE_ID, movie.getmId());
        cv.put(MovieContract.MoviesFavorites.COLUMN_POSTER, movie.getmPoster());
        cv.put(MovieContract.MoviesFavorites.COLUMN_RELEASE_DATE, movie.getmReleaseDate());
        cv.put(MovieContract.MoviesFavorites.COLUMN_TITLE, movie.getmTitle());
        cv.put(MovieContract.MoviesFavorites.COLUMN_USER_RATING, movie.getmVoteAvg());

        return mDb.insert(MoviesFavorites.TABLE_NAME, null, cv);
    }


}
