package com.example.android.popularmovies.data;

//Reference Lesson 12

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

//Grouped tasks avoids the effects of tasks starvation (ex: disk reads don't wait behind web service requests).
public class AppExecutors {

    //Singleton Instantiation
    private static final Object LOCK = new Object();
    private static AppExecutors sInstance;
    private final Executor diskIO;
    //private final Executor mainThread;
    //private final Executor networkIO;

    private AppExecutors(Executor diskIO, Executor networkIO, Executor mainThread){
        this.diskIO = diskIO;

        //NOT USED BUT KEEPING IN FOR REFERENCE
        //this.networkIO = networkIO;
        //this.mainThread = mainThread;
    }

    public static AppExecutors getsInstance(){
        if (sInstance == null) {
            synchronized (LOCK){
                sInstance = new AppExecutors(Executors.newSingleThreadExecutor(),
                Executors.newFixedThreadPool(3),
                new MainThreadExecutor());
            }
        }
        return sInstance;
    }


    public Executor diskIO(){
        return diskIO;
    }

    //NOT USED BUT KEEPING FOR REFERENCE

//    public Executor mainThread(){
//        return mainThread;
//    }

//    public Executor networkIO(){
//        return networkIO;
//    }

    private static class MainThreadExecutor implements Executor {
        private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable runnable) {
            mainThreadHandler.post(runnable);
        }
    }
}
