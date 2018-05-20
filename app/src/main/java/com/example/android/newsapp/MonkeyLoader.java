package com.example.android.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Loads a list of monkeys by using an AsyncTask to perform the
 * network request to the given URL.
 */

public class MonkeyLoader extends AsyncTaskLoader<List<Monkey>> {

    /** Tag for log messages */
    private static final String LOG_TAG = MonkeyLoader.class.getName();

    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new {@link MonkeyLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public MonkeyLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG, "Test: onStartLoading called from MonkeyLoader");
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<Monkey> loadInBackground() {
        Log.i(LOG_TAG, "Test: loadInBackground called from MonkeyLoader");

        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of Monkeys.
        List<Monkey> monkeys = QueryUtils.fetchMonkeyData(mUrl);
        return monkeys;
    }
}
