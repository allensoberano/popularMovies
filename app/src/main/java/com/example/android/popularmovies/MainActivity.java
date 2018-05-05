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

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private MovieRecyclerViewAdapater mAdapter;
    private RecyclerView mMovieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //Ref to RecyclerView from XML. Allows us to set the adapater of RV and toggle visibility.
        mMovieList = (RecyclerView) findViewById(R.id.rv_movies);
        mMovieList.setLayoutManager(new GridLayoutManager(this, 3));
        mMovieList.setHasFixedSize(true);

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
    public class MovieQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String movieSearchResults = null;
            try {
                movieSearchResults = NetworkUtils.getResponseFromHTTPUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return movieSearchResults;
        }

        @Override
        protected void onPostExecute(String jsonString) {
            if (jsonString != null && !jsonString.equals("")) {
                //mTextView.setText(s)
                //Check what S is

                try {
                    Movie[] movie = JsonUtils.parseMovieJson(jsonString);
                    MovieRecyclerViewAdapater movieRecyclerViewAdapater = new MovieRecyclerViewAdapater(MainActivity.this, movie);

                    //mAdapter = new MovieRecyclerViewAdapater();

                    mMovieList.setAdapter(movieRecyclerViewAdapater);

                    int a = 0;


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
