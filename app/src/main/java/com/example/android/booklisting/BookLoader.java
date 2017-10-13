package com.example.android.booklisting;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vikash on 17/6/17.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {
    private static final String LOG_TAG = BookLoader.class.getName();

    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new {@link BookLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public BookLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public ArrayList<Book> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        Log.v(LOG_TAG,"--------loadinBAckground");
        Log.v(LOG_TAG,"---------"+mUrl);

        // Perform the network request, parse the response, and extract a list of earthquakes.
        ArrayList<Book> books = QueryUtils.fetchEarthquakeData(mUrl);
        Log.v(LOG_TAG,"--------AfterloadinBAckground");
        return books;
    }
}


