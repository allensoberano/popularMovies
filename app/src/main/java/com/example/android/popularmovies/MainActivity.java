package com.example.android.popularmovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.popularmovies.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        makeMovieSearchQuery();

    }

    //creates movie search query string
    private void makeMovieSearchQuery(){
        String movieQuery = "";
        URL movieSearchUrl = NetworkUtils.buildUrl(movieQuery);

        String movieSearchResults = null;
        new MovieQueryTask().execute(movieSearchUrl);
    }

    //Asnyc Task to query api on background thread
    //*Reference: Lesson02_05
    public class MovieQueryTask extends AsyncTask<URL, Void, String>{

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
        protected void onPostExecute(String jsonString){
            if (jsonString != null && !jsonString.equals("")) {
                //mTextView.setText(s)
                //Check what S is
            }
        }
    }
}
