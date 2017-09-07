package com.annekay.android.androiddevelopers;

import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import static com.annekay.android.androiddevelopers.DevelopersListFragment.LOG_TAG;
import static com.annekay.android.androiddevelopers.DevelopersListFragment.getDevLocation;


/**
 * Created by anneKay on 8/23/2017.
 */

public class JsonLoader extends AsyncTaskLoader<List<Developer>> {
    private int mCurrentPage;
    private int mItemsPerPage;
    private int mTotalPages;
    private static final String baseUrl= "https://api.github.com/search/users";

    public JsonLoader(Context context) {
        super(context);
        // TODO: Finish implementing this constructor

        init();
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

        if (baseUrl.length() < 1 ) {
            return null;
        }


        List<Developer> firstResult = QueryUtil.fetchDeveloperData(loadMore(1));
        List<Developer> secondResult = new ArrayList<>();
        List<Developer> thirdResult = new ArrayList<>();
        if (getHasMoreResults()) {
            secondResult = QueryUtil.fetchDeveloperData(loadMore(2));
            thirdResult = QueryUtil.fetchDeveloperData(loadMore(3));

        }
        List<Developer> results = new ArrayList<>();
        results.addAll(firstResult);
        results.addAll(secondResult);
        results.addAll(thirdResult);
        return results;
    }
    private void init( )
    {
        mCurrentPage = 0;
    }
    private String loadMore(int pageNo){
        Uri baseUri = Uri.parse(baseUrl);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("q", "language:java location:" + getDevLocation());
        uriBuilder.appendQueryParameter("per_page", "100");
        uriBuilder.appendQueryParameter("page", ""+pageNo);
        return uriBuilder.toString();
    }


    public boolean getHasMoreResults( )
    {
        return( mCurrentPage <= mTotalPages );
    }


}
