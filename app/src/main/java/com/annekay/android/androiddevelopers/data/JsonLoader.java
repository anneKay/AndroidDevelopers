package com.annekay.android.androiddevelopers.data;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.annekay.android.androiddevelopers.util.QueryUtil;
import com.annekay.android.androiddevelopers.model.Developer;
import java.util.List;
import static com.annekay.android.androiddevelopers.ui.DevelopersListFragment.LOG_TAG;



/**
 * Created by annekay on 8/23/2017.
 */

public class JsonLoader extends AsyncTaskLoader<List<Developer>> {
   private String mUrl;

    public JsonLoader(Context context, String url) {
        super(context);
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

        if (mUrl.length() < 1 ) {
            return null;
        }


        List<Developer> result = QueryUtil.fetchDeveloperData(mUrl);

        return result;
    }



}
