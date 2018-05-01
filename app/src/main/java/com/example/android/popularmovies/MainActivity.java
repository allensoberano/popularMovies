package com.example.android.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.popularmovies.utilities.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        makeMovieSearchQuery();

    }

    private void makeMovieSearchQuery(){
        String movieQuery = "";
        URL movieSearchUrl = NetworkUtils.buildUrl(movieQuery);
    }
}
