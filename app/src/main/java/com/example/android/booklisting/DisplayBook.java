package com.example.android.booklisting;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DisplayBook extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>>  {
    public static final String LOG_TAG = DisplayBook.class.getName();
    private BookAdapter mAdapter;
    private TextView mEmptyStateTextView;
    private String USGS_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        Bundle extras = getIntent().getExtras();
        String search_text = extras.getString("search");
        String getmax = extras.getString("max");

        USGS_URL="https://www.googleapis.com/books/v1/volumes?q="+search_text+"&maxResults="+getmax;
        Log.v(LOG_TAG,"-----"+USGS_URL);

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);


        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);

        // Create a fake list of Earthquake locations.
//        //ArrayList<Earthquake> earthquakes = QueryUtils.extractEarthquakes();
//        earthquakeAsyncTask task=new earthquakeAsyncTask();
//        task.execute(USGS_URL);


        //EarthquakeLoader earthquakeLoader=new EarthquakeLoader(this,USGS_URL);
        //LoaderManager loaderManager=getLoaderManager();

        Log.v(LOG_TAG,"-----Before Init Loader()");
        if(isNetworkConnected())
            getSupportLoaderManager().initLoader(0, null, this).forceLoad();
        else {
            ProgressBar progressBar=(ProgressBar)findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);

        }
        Log.v(LOG_TAG,"-----After Init Loader()");

        mAdapter = new BookAdapter(this, new ArrayList<Book>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mAdapter);


        earthquakeListView.setEmptyView(mEmptyStateTextView);

        // Create a new {@link ArrayAdapter} of books
        // Find a reference to the {@link ListView} in the layout
//        ListView earthquakeListView = (ListView) findViewById(R.id.list);
//
//        // Create a new {@link ArrayAdapter} of books
//        QuakeAdapter adapter = new QuakeAdapter(
//                this,books);
//
//        // Set the adapter on the {@link ListView}
//        // so the list can be populated in the user interface
//        earthquakeListView.setAdapter(adapter);
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        Log.v(LOG_TAG,"-----In onCreateLoader()");
        return new BookLoader(this,USGS_URL);

    }
    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        Log.v(LOG_TAG,"-----In onLoadFinished()");

        ProgressBar progressBar=(ProgressBar)findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        mEmptyStateTextView.setText(R.string.no_books);

        mAdapter.clear();
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }




        // Clear the adapter of previous earthquake books
//        mAdapter.clear();
//
//        // If there is a valid list of {@link Book}s, then add them to the adapter's
//        // books set. This will trigger the ListView to update.
//        if (books != null && !books.isEmpty()) {
//            mAdapter.addAll(books);
//        } // Clear the adapter of previous earthquake books
//        mAdapter.clear();
//
//        // If there is a valid list of {@link Book}s, then add them to the adapter's
//        // books set. This will trigger the ListView to update.
//        if (books != null && !books.isEmpty()) {
//            mAdapter.addAll(books);
//        }
    }
    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mAdapter.clear();

    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
