package com.example.android.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.popularmovies.adapters.MovieRecyclerViewAdapter;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.utilities.JsonUtils;
import com.example.android.popularmovies.utilities.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieRecyclerViewAdapter.ItemClickListener {

    private RecyclerView mMovieList;
    private Movie[] mMovieData;
    private String sortOrder = "popular";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MovieRecyclerViewAdapter mAdapter;

        //Ref to RecyclerView from XML. Allows us to set the adapter of RV and toggle visibility.
        mMovieList = findViewById(R.id.rv_movies);
        mMovieList.setLayoutManager(new GridLayoutManager(this, numberOfColumns()));
        mAdapter = new MovieRecyclerViewAdapter(this, mMovieData, this);
        mMovieList.setHasFixedSize(true);
        mMovieList.setAdapter(mAdapter);

        //Build Search Query
        makeMovieSearchQuery(sortOrder);

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
            sortOrder = "popular";
            makeMovieSearchQuery(sortOrder);
            setTitle("Popular Movies");
        } else if (sortByClicked == R.id.rating) {
            sortOrder = "top_rated";
            makeMovieSearchQuery(sortOrder);
            setTitle("Top Rated Movies");
        }

        return super.onOptionsItemSelected(item);
    }
    //endregion


    //creates movie search query string
    private void makeMovieSearchQuery(String sortBy) {

        URL movieSearchUrl = NetworkUtils.buildUrl(sortBy);
        new MovieQueryTask().execute(movieSearchUrl);
    }

    @Override
    public void onItemClick(int position) {
        launchMovieDetailActivity(position);
    }


    //region Async Task
    //Async Task to query api on background thread
    //*Reference: Lesson02_05
    class MovieQueryTask extends AsyncTask<URL, Void, Movie[]> {

        @Override
        protected Movie[] doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String movieSearchResults;
            try {
                movieSearchResults = NetworkUtils.getResponseFromHTTPUrl(searchUrl);
                mMovieData = JsonUtils.parseMovieJson(movieSearchResults);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mMovieData;
        }

        @Override
        protected void onPostExecute(Movie[] movie) {

            MovieRecyclerViewAdapter movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(MainActivity.this, movie, MainActivity.this);
            mMovieList.setAdapter(movieRecyclerViewAdapter);
            movieRecyclerViewAdapter.notifyDataSetChanged();

        }
    }
    //endregion

    private void launchMovieDetailActivity(int position) {
        Movie movieToSend = this.mMovieData[position];//new Movie();
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("movie", movieToSend);
        startActivity(intent);
    }
}

