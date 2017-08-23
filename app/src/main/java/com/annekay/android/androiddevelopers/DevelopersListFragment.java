package com.annekay.android.androiddevelopers;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DevelopersListFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Developer>>{
    private static final String JAVA_DEVELOPERS = "https://api.github.com/search/users?q=language:java+location:lagos";
    /** Tag for the log messages */
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    private DevelopersAdapter developersAdapter;
    private static final int JSON_LOADER_ID = 1;
    public String thumbNailUrl, userName, gitHubUrl, userNameDetail, gitHubUrlDetails;
    ListView listView;
    TextView feedbackView;


    public DevelopersListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main, container, false);

        feedbackView = (TextView) rootView.findViewById(android.R.id.empty);

        listView = (ListView) rootView.findViewById(R.id.list);

        //initialize the developersAdapter
        developersAdapter = new DevelopersAdapter(getActivity(), new ArrayList<Developer>());
        listView.setAdapter(developersAdapter);

        detectNetworkState();

        return rootView;
    }

    @Override
    public Loader<List<Developer>> onCreateLoader(int i, Bundle bundle) {
        // TODO: Create a new loader for the given URL
        return new JsonLoader(getActivity(), JAVA_DEVELOPERS);


    }
    @Override

    public void onLoadFinished(Loader<List<Developer>> loader, List<Developer> data) {

        // TODO: Update the UI with the result
        developersAdapter.clear();
        if (data != null && !data.isEmpty()) {
//
            developersAdapter.addAll(data);
        }else {
            listView.setEmptyView(getView().findViewById(android.R.id.empty));
        }
        progressBar();
    }

    @Override
    public void onLoaderReset(Loader<List<Developer>> loader) {

        // TODO: Loader reset, so we can clear out our existing data.
        developersAdapter.clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        progressBar();
    }

    // method to detect network connectivity
    public void detectNetworkState(){
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(isConnected){
            getLoaderManager().initLoader(JSON_LOADER_ID, null, this).forceLoad();
        }else {
            listView.setEmptyView(getView().findViewById(android.R.id.empty));
            feedbackView.setText("No Internet Connection");
            progressBar();
        }
    }
    public void progressBar(){
        ProgressBar earthquakeProgress = (ProgressBar) getView().findViewById(R.id.determinateBar);
        earthquakeProgress.setVisibility(View.GONE);
    }


}
