package com.example.android.popularmovies.utilities;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtils {

    //Constants to build URL String for movies
    //* Reference: Lesson02_03 Exercise build URL
    final static String MOVIEDB_BASE_URL = "https://api.themoviedb.org/3/movie/550";
    final static String PARAM_QUERY = "api_key";
    final static String PRAM_SORT = "sort";

    //Will need to provide YOUR OWN API_Key
    final static String API_KEY = "####################";

    //Constants to build URL String for movie poster
    final static String MOVIEDB_POSTER_BASE_URL = "http://imge.tmdb.org/t/p/";
    final static String PARAM_SIZE = "w185//";
    final static String IMAGE_REF = "/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg";

    //* Reference: Lesson 02_03
    public static URL buildUrl(String movieDBSearchQuery){
        //*** CURRENTLY movieDBSearchQuery is not used until I need to put a parameter

        Uri builtUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        return url;
    }


}
