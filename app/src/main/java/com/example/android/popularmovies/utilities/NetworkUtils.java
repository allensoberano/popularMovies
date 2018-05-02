package com.example.android.popularmovies.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    //Constants to build URL String for movies
    //* Reference: Lesson02_03 Exercise build URL
    final static String MOVIEDB_BASE_URL = "https://api.themoviedb.org/3/movie/popular";
    final static String PARAM_QUERY = "api_key";
    final static String PRAM_SORT = "sort";

    //Will need to provide YOUR OWN API_Key
    final static String API_KEY = "7147147683690403e3dea3545f41f5f4";

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


    //Helper Method
    //* Reference: Lesson02-04
    public static String getResponseFromHTTPUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }


}