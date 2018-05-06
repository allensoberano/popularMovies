package com.example.android.popularmovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.popularmovies.adapters.MovieRecyclerViewAdapater;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.utilities.JsonUtils;
import com.example.android.popularmovies.utilities.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private MovieRecyclerViewAdapater mAdapter;
    private RecyclerView mMovieList;
    private Movie[] mMovieData;

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



        makeMovieSearchQuery();


    }

    //creates movie search query string
    private void makeMovieSearchQuery() {
        String movieQuery = "";
        URL movieSearchUrl = NetworkUtils.buildUrl(movieQuery);

        String movieSearchResults = null;
        new MovieQueryTask().execute(movieSearchUrl);
    }

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

            //Movie[] movie = JsonUtils.parseMovieJson(jsonString);

            MovieRecyclerViewAdapater movieRecyclerViewAdapater = new MovieRecyclerViewAdapater(MainActivity.this, movie);
            mMovieList.setAdapter(movieRecyclerViewAdapater);
           movieRecyclerViewAdapater.notifyDataSetChanged();



        }
    }
}

