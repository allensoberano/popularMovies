package com.example.android.popularmovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
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
import com.example.android.popularmovies.model.MainViewModel;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.utilities.NetworkUtils;

import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieRecyclerViewAdapter.ItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mMovieList;
    private List<Movie> mMovieData;
    private String appendPath = "popular";
    private final static String LIST_STATE_KEY = "rv_state";
    private final static String LAST_SCREEN = "last_screen";
    private Parcelable rvState;
    private String mLastScreen;

    //CONSTANTS
    private final static String POPULAR_MOVIES = "Popular Movies";
    private final static String TOP_RATED_MOVIES = "Top Rated Movies";
    private final static String FAVORITE_MOVIES = "Favorite Movies";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setupPopularMovies();
        mMovieList = findViewById(R.id.rv_movies);
        mMovieList.setLayoutManager(new GridLayoutManager(this, numberOfColumns()));

        if (savedInstanceState != null) {
            rvState = savedInstanceState.getParcelable(LIST_STATE_KEY);
            mLastScreen = savedInstanceState.getString(LAST_SCREEN);

            if (mLastScreen != null) {
                switch ((mLastScreen)) {
                    case POPULAR_MOVIES:
                        setupPopularMovies();
                        setTitle(POPULAR_MOVIES);
                        break;
                    case TOP_RATED_MOVIES:
                        setupPopularMovies();
                        appendPath = "top_rated";
                        makeMovieSearchQuery(appendPath);
                        setTitle(TOP_RATED_MOVIES);
                        break;
                    case FAVORITE_MOVIES:
                        setTitle(FAVORITE_MOVIES);
                        getAllFavoriteMovies();
                        break;
                }
            }
        } else {
            getAllFavoriteMovies();
            setupPopularMovies();
        }

    }

    private void setupPopularMovies() {
        //Ref to RecyclerView from XML. Allows us to set the adapter of RV and toggle visibility.
        mMovieList = findViewById(R.id.rv_movies);
        mMovieList.setLayoutManager(new GridLayoutManager(this, numberOfColumns()));
        MovieRecyclerViewAdapter mAdapter = new MovieRecyclerViewAdapter(mMovieData, this);
        mMovieList.setHasFixedSize(true);
        mMovieList.setAdapter(mAdapter);

        //Build Search Query
        URL movieSearchUrl = NetworkUtils.buildMoviesURL(appendPath);

        //Run Query
        new MovieQueryTask(new MovieQueryTaskCompleteListener()).execute(movieSearchUrl);
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

        // save list state
        rvState = mMovieList.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(LIST_STATE_KEY, rvState);
        outState.putString(LAST_SCREEN, String.valueOf(getTitle()));

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //Retrieve previous state
        if (savedInstanceState != null)
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
                mLastScreen = POPULAR_MOVIES;
                makeMovieSearchQuery("popular");
                setTitle(POPULAR_MOVIES);
                break;
            case R.id.rating:
                mLastScreen = TOP_RATED_MOVIES;
                makeMovieSearchQuery("top_rated");
                setTitle(TOP_RATED_MOVIES);
                break;
            case R.id.favorites:
                mLastScreen = FAVORITE_MOVIES;
                getAllFavoriteMovies();
                setTitle(FAVORITE_MOVIES);
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
        Movie movieToSend;
        if (getTitle() == "Favorite Movies") {
            MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
            LiveData<List<Movie>> movies = viewModel.getMovies();
            movieToSend = movies.getValue().get(position);//new Movie();

        } else {
            movieToSend = this.mMovieData.get(position);//new Movie();
        }

        mLastScreen = getTitle().toString();
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("movie", movieToSend);
        startActivity(intent);

    }

    private void getAllFavoriteMovies() {

                MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
                viewModel.getMovies().observe(MainActivity.this, new Observer<List<Movie>>() {
                    @Override
                    public void onChanged(@Nullable List<Movie> movies) {
                        if(mLastScreen != null) {
                            if (mLastScreen.equals(FAVORITE_MOVIES)) {
                                Log.d(TAG, "Updating movies from LiveData in ViewModel");
                                MovieRecyclerViewAdapter movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(movies, MainActivity.this);
                                mMovieList.setAdapter(movieRecyclerViewAdapter);
                                movieRecyclerViewAdapter.notifyDataSetChanged();
                            }
                        }

                    }
                });


    }
}

