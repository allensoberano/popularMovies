package com.example.android.popularmovies.utilities;

import java.net.URL;

public class MovieDBUtils {

    //creates movie search query string
    public static URL makeMovieSearchQuery(String appendPath) {

        URL movieSearchUrl = NetworkUtils.buildUrl(appendPath);
        return movieSearchUrl;

    }
}
