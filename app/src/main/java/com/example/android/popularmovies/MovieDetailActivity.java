package com.example.android.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        Movie movieSent = (Movie) getIntent().getParcelableExtra("movie");
        populateDetailActivity(movieSent);

    }

    private void populateDetailActivity(Movie movieSent){
        ImageView mMoviePoster = findViewById(R.id.iv_movie_image);
        TextView mMovieTitle = findViewById(R.id.tv_movie_title);
        RatingBar mRating = findViewById(R.id.rb_rating);
        TextView mReleaseDate = findViewById(R.id.tv_release_date);
        TextView mDescription = findViewById(R.id.tv_description);


        mMovieTitle.setText(movieSent.getmTitle());
        mRating.setRating((float)movieSent.getmVoteAvg()/2);
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
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = fmt.parse(dateToConvert);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat fmtOut = new SimpleDateFormat("MM-dd-yyyy");
        return fmtOut.format(date);
    }

//    private void setPoster(){
//
//    }
}
