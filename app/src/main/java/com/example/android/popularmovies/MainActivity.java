package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.popularmovies.adapters.MovieRecyclerViewAdapter;
import com.example.android.popularmovies.async.AsyncTaskCompleteListener;
import com.example.android.popularmovies.async.MovieQueryTask;
import com.example.android.popularmovies.data.AppDatabase;
import com.example.android.popularmovies.data.AppExecutors;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.utilities.NetworkUtils;

import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieRecyclerViewAdapter.ItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mMovieList;
    private List<Movie> mMovieData;
    private String appendPath = "popular";
    private AppDatabase mDb;
    public final static String LIST_STATE_KEY = "rv_state";
    Parcelable rvState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ref to RecyclerView from XML. Allows us to set the adapter of RV and toggle visibility.
        mMovieList = findViewById(R.id.rv_movies);
        mMovieList.setLayoutManager(new GridLayoutManager(this, numberOfColumns()));
        MovieRecyclerViewAdapter mAdapter = new MovieRecyclerViewAdapter(mMovieData, this);
        mMovieList.setHasFixedSize(true);
        mMovieList.setAdapter(mAdapter);

        if(savedInstanceState != null)
            rvState = savedInstanceState.getParcelable(LIST_STATE_KEY);

        //Build Search Query
        URL movieSearchUrl = NetworkUtils.buildMoviesURL(appendPath);
        //Run Query
        new MovieQueryTask(new MovieQueryTaskCompleteListener()).execute(movieSearchUrl);

        mDb = AppDatabase.getsInstance(getApplicationContext());

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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //outState.putParcelableArray(LIST_STATE_KEY, mMovieData);

        // save list state
        rvState = mMovieList.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(LIST_STATE_KEY, rvState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //mMovieData = (Arr) savedInstanceState.getParcelableArray(LIST_STATE_KEY);

        //Retrieve previous state
        if(savedInstanceState != null)
            rvState = savedInstanceState.getParcelable(LIST_STATE_KEY);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (rvState != null)
           mMovieList.getLayoutManager().onRestoreInstanceState(rvState);
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
        switch (sortByClicked) {
            case R.id.popular:
                appendPath = "popular";
                makeMovieSearchQuery(appendPath);
                setTitle("Popular Movies");
                break;
            case R.id.rating:
                appendPath = "top_rated";
                makeMovieSearchQuery(appendPath);
                setTitle("Top Rated Movies");
                break;
            case R.id.favorites:
                getAllMovies();
                setTitle("Favorite Movies");
                break;
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

    private void showMovies(List<Movie> movie) {
        MovieRecyclerViewAdapter movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(movie, MainActivity.this);
        mMovieList.setAdapter(movieRecyclerViewAdapter);
        movieRecyclerViewAdapter.notifyDataSetChanged();
    }

    //region AsyncTask Listener
    public class MovieQueryTaskCompleteListener implements AsyncTaskCompleteListener<List<Movie>> {

        @Override
        public void onTaskComplete(List<Movie> result) {
            mMovieData = result;
            showMovies(mMovieData);
        }


    }
    //endregion

    private void launchMovieDetailActivity(int position) {
        Movie movieToSend = this.mMovieData.get(position);//new Movie();
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("movie", movieToSend);
        startActivity(intent);
    }

    private void getAllMovies() {

        AppExecutors.getsInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Actively retrieving movies from database");
                final List<Movie> movies = mDb.movieDao().loadAllMovies();
                mMovieData = movies;

                //simplify this later
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MovieRecyclerViewAdapter movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(mMovieData, MainActivity.this);
                        mMovieList.setAdapter(movieRecyclerViewAdapter);
                        movieRecyclerViewAdapter.notifyDataSetChanged();
                    }
                });

            }
        });

    }
}

