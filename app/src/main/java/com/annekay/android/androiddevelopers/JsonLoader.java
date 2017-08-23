package com.annekay.android.androiddevelopers;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;
import static com.annekay.android.androiddevelopers.DevelopersListFragment.LOG_TAG;


/**
 * Created by HP on 8/23/2017.
 */

public class JsonLoader extends AsyncTaskLoader<List<Developer>> {
    String mUrl;
    public JsonLoader(Context context, String url) {
        super(context);
        // TODO: Finish implementing this constructor
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG, "TEST: onStartLoading() called...");
        forceLoad();
    }

    @Override
    public List<Developer> loadInBackground() {
        Log.i(LOG_TAG, "TEST: LoadInBackground() called...");
        // TODO: Implement this method
        // Don't perform the request if there are no URLs, or the first URL is null.

        if (mUrl.length() < 1 || mUrl == null) {
            return null;
        }
        List<Developer> results = QueryUtil.fetchDeveloperData(mUrl);
        return results;
    }
}
