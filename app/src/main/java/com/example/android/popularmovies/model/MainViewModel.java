package com.example.android.popularmovies.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.popularmovies.data.AppDatabase;

import java.util.List;

public class MainViewModel extends AndroidViewModel{
    //cache our list of movie objects wrapped in a live data object

    //Constant for logging
    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<Movie>> movies;

    public MainViewModel(@NonNull Application application) {
        super(application);

        AppDatabase database = AppDatabase.getsInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from the Database");
        movies = database.movieDao().loadAllMovies();
    }

    public LiveData<List<Movie>> getMovies(){

        return movies;
    }
}
