package com.example.android.popularmovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.popularmovies.adapters.MovieRecyclerViewAdapater;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.utilities.JsonUtils;
import com.example.android.popularmovies.utilities.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private MovieRecyclerViewAdapater mAdapter;
    private RecyclerView mMovieList;
    private Movie[] mMovieData;
    private String sortOrder = "popular";

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ref to RecyclerView from XML. Allows us to set the adapater of RV and toggle visibility.
        mMovieList = (RecyclerView) findViewById(R.id.rv_movies);
        mMovieList.setLayoutManager(new GridLayoutManager(this, 2));

        mAdapter = new MovieRecyclerViewAdapater(this, mMovieData);
        mMovieList.setHasFixedSize(true);
        mMovieList.setAdapter(mAdapter);

        //Build Search Query
        makeMovieSearchQuery(sortOrder);

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

        String movieSearchResults = null;
        new MovieQueryTask().execute(movieSearchUrl);
    }

    //region Async Task
    //Asnyc Task to query api on background thread
    //*Reference: Lesson02_05
    public class MovieQueryTask extends AsyncTask<URL, Void, Movie[]> {

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

            MovieRecyclerViewAdapater movieRecyclerViewAdapater = new MovieRecyclerViewAdapater(MainActivity.this, movie);
            mMovieList.setAdapter(movieRecyclerViewAdapater);
            movieRecyclerViewAdapater.notifyDataSetChanged();

        }
    }
    //endregion
}

