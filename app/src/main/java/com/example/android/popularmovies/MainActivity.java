package com.example.android.popularmovies;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.popularmovies.adapters.MovieRecyclerViewAdapter;
import com.example.android.popularmovies.async.AsyncTaskCompleteListener;
import com.example.android.popularmovies.async.MovieQueryTask;
import com.example.android.popularmovies.data.MovieContract.MoviesFavorites;
import com.example.android.popularmovies.data.MovieDbHelper;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.utilities.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieRecyclerViewAdapter.ItemClickListener {

    private RecyclerView mMovieList;
    private Movie[] mMovieData;
    private String appendPath = "popular";
    private SQLiteDatabase mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MovieRecyclerViewAdapter mAdapter;

        //Ref to RecyclerView from XML. Allows us to set the adapter of RV and toggle visibility.
        mMovieList = findViewById(R.id.rv_movies);
        mMovieList.setLayoutManager(new GridLayoutManager(this, numberOfColumns()));
        mAdapter = new MovieRecyclerViewAdapter(mMovieData, this);
        mMovieList.setHasFixedSize(true);
        mMovieList.setAdapter(mAdapter);

        //Build Search Query
        URL movieSearchUrl = NetworkUtils.buildMoviesURL(appendPath);
        //Run Query
        new MovieQueryTask(new MovieQueryTaskCompleteListener()).execute(movieSearchUrl);

        MovieDbHelper dbHelper = new MovieDbHelper(this);
        mDb = dbHelper.getWritableDatabase();



        //passing resulting cursor count to adapter
        //mAdapter = new MovieRecyclerViewAdapter(this, cursor.getCount());

    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // You can change this divider to adjust the size of the poster
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2;
        return nColumns;
    }

    //region Menu
    //*Reference Lesson 02_06 Add Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int sortByClicked = item.getItemId();
        if (sortByClicked == R.id.popular) {
            appendPath = "popular";
            makeMovieSearchQuery(appendPath);
            setTitle("Popular Movies");
        } else if (sortByClicked == R.id.rating) {
            appendPath = "top_rated";
            makeMovieSearchQuery(appendPath);
            setTitle("Top Rated Movies");
        } else if (sortByClicked == R.id.favorites) {
            getAllFavoriteMovies();
            setTitle("Favorite Movies");
        }

        return super.onOptionsItemSelected(item);
    }
    //endregion


    //creates movie search query string
    private void makeMovieSearchQuery(String appendPath) {
        URL movieSearchUrl = NetworkUtils.buildMoviesURL(appendPath);
        new MovieQueryTask(new MovieQueryTaskCompleteListener()).execute(movieSearchUrl);

    }

    @Override
    public void onItemClick(int position) {
        launchMovieDetailActivity(position);
    }

    private void showMovies(Movie[] movie){
        MovieRecyclerViewAdapter movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(movie, MainActivity.this);
        mMovieList.setAdapter(movieRecyclerViewAdapter);
        movieRecyclerViewAdapter.notifyDataSetChanged();
    }

    //region AsyncTask Listener
    public class MovieQueryTaskCompleteListener implements AsyncTaskCompleteListener<Movie[]>{

        @Override
        public void onTaskComplete(Movie[] result) {
            mMovieData = result;
            showMovies(mMovieData);
        }
    }
    //endregion

    private void launchMovieDetailActivity(int position) {
        Movie movieToSend = this.mMovieData[position];//new Movie();
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("movie", movieToSend);
        startActivity(intent);
    }

    private Movie[] getAllFavoriteMovies(){
        Cursor mCursor = mDb.rawQuery("SELECT * FROM " + MoviesFavorites.TABLE_NAME, null);
        mMovieData = new Movie[mCursor.getCount()];

        if (mCursor.moveToFirst()) {
            do {
                Movie movie = new Movie();

                movie.setmId(mCursor.getInt(mCursor.getColumnIndex(MoviesFavorites.COLUMN_MOVIE_ID)));
                movie.setmTitle(mCursor.getString(mCursor.getColumnIndex(MoviesFavorites.COLUMN_TITLE)));
                movie.setmPoster(mCursor.getString(mCursor.getColumnIndex(MoviesFavorites.COLUMN_POSTER)));
                movie.setmReleaseDate(mCursor.getString(mCursor.getColumnIndex(MoviesFavorites.COLUMN_RELEASE_DATE)));
                movie.setmVoteAvg(mCursor.getInt(mCursor.getColumnIndex(MoviesFavorites.COLUMN_USER_RATING)));

                mMovieData[mCursor.getPosition()] = movie;
            }while (mCursor.moveToNext());


        }
        showMovies(mMovieData);
        return mMovieData;
    }
}

