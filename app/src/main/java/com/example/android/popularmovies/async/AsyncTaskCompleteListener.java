package com.example.android.popularmovies.async;

/**
 * This is a useful callback mechanism so we can abstract our AsyncTasks out into separate, re-usable
 * and testable classes yet still retain a hook back into the calling activity. Basically, it'll make classes
 * cleaner and easier to unit test.
 *
 * @param <T>
 */
//*Reference: http://www.jameselsey.co.uk/blogs/techblog/extracting-out-your-asynctasks-into-separate-classes-makes-your-code-cleaner/
public interface AsyncTaskCompleteListener<T>
{
        /**
         * Invoked when the AsyncTask has completed its execution.
         * @param result The resulting object from the AsyncTask.
         */
        public void onTaskComplete(T result);
}
