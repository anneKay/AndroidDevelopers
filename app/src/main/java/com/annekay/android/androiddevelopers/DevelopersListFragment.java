package com.annekay.android.androiddevelopers;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DevelopersListFragment extends Fragment {
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

        return rootView;
    }

}
