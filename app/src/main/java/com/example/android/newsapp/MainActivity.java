package com.example.android.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Monkey>> {

    private static final String LOG_TAG = MainActivity.class.getName();

    /** URL for monkey data from the Guardian monkey news dataset */
    private static final String GUARDIAN_REQUEST_URL =
            "http://content.guardianapis.com/search?q=monkeys&api-key=test";

    /**
     * Constant value for the monkey loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int MONKEY_LOADER_ID = 1;

    /** Adapter for the list of monkeys */
    private MonkeyAdapter mAdapter;

    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find a reference to the {@link ListView} in the layout
        ListView monkeyListView = (ListView) findViewById(R.id.list);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_state);
        monkeyListView.setEmptyView(mEmptyStateTextView);

        // Create a new adapter that takes an empty list of monkeys as input
        mAdapter = new MonkeyAdapter(this, new ArrayList<Monkey>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        monkeyListView.setAdapter(mAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected monkey.
        monkeyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current monkey that was clicked on
                Monkey currentMonkey = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri monkeyUri = Uri.parse(currentMonkey.getUrl());

                // Create a new intent to view the monkey URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, monkeyUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(MONKEY_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.progress_bar);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_net);
        }
    }

    @Override
    public Loader<List<Monkey>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new MonkeyLoader(this, GUARDIAN_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Monkey>> loader, List<Monkey> monkeys) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.progress_bar);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No monkeys found."
        mEmptyStateTextView.setText(R.string.no_results);

        // Clear the adapter of previous monkey data
        mAdapter.clear();

        // If there is a valid list of {@link Monkey}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (monkeys != null && !monkeys.isEmpty()) {
            mAdapter.addAll(monkeys);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Monkey>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
}