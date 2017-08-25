package com.annekay.android.androiddevelopers;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
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
    ProgressBar developerProgressBar;
    public String thumbNailUrl, userName, gitHubUrl, userNameDetail, gitHubUrlDetails;
    ListView listView;
    TextView feedbackView;


    public DevelopersListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_list, container, false);

        feedbackView = (TextView) rootView.findViewById(android.R.id.empty);
        developerProgressBar = (ProgressBar)rootView.findViewById(R.id.determinateBar);

        listView = (ListView) rootView.findViewById(R.id.list);

        //initialize the developersAdapter
        developersAdapter = new DevelopersAdapter(getActivity(), new ArrayList<Developer>());
        listView.setAdapter(developersAdapter);

        detectNetworkState();

         /*
        * create an onclick listener to the developers in the listview
        * each click on the items takes the user to the developer's profile details
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Developer currentDeveloper = developersAdapter.getItem(position);

                BitmapDrawable bd = (BitmapDrawable) ((ImageView) view.findViewById(R.id.thumbnail))
                        .getDrawable();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bd.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] imageByte = baos.toByteArray();

                userNameDetail = currentDeveloper.getUserName();
                gitHubUrlDetails = currentDeveloper.getGitHubUrl();


                // Getting the orientation ( Landscape or Portrait ) of the screen */
                int orientation = getResources().getConfiguration().orientation;


                // Landscape Mode */
                if(orientation == Configuration.ORIENTATION_LANDSCAPE ){
                    // Getting the fragment manager


                    /** Getting the fragmenttransaction object, which can be used to add, remove or replace a fragment */
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                    /** Getting the existing detailed fragment object, if it already exists.
                     *  The fragment object is retrieved by its tag name
                     * */
                    Fragment prevFrag = getFragmentManager().findFragmentByTag("developers.details");

                    /** Remove the existing detailed fragment object if it exists */
                    if(prevFrag!=null)
                        fragmentTransaction.remove(prevFrag);

                    /** Instantiating the fragment CountryDetailsFragment */
                    DeveloperDetailsFragment fragment = new DeveloperDetailsFragment();

                    /** Creating a bundle object to pass the data(the clicked item's position) from the activity to the fragment */
                    Bundle b = new Bundle();

                    /** Setting the data to the bundle object */

                    b.putString("userName", userNameDetail);
                    b.putString("gitHubUrl", gitHubUrlDetails);
                    b.putByteArray("image", imageByte);
                    //b.putString("profileImage", bitmap);
                    //b.putString("profileImage", currentDeveloper.getThumbnailUrl());
//                    developer.setGitHubUrl(currentDeveloper.getGitHubUrl());
//                    developer.setThumbnailUrl(currentDeveloper.getThumbnailUrl());
//                    developer.setUserName(currentDeveloper.getUserName());

                    /** Setting the bundle object to the fragment */
                    fragment.setArguments(b);

                    /** Adding the fragment to the fragment transaction */
                    fragmentTransaction.add(R.id.detail_fragment_container, fragment,"developers.details");

                    /** Adding this transaction to backstack */
                    fragmentTransaction.addToBackStack(null).commit();

                    /** Making this transaction in effect */
                    // fragmentTransaction.commit();
                }else{
                    /** Portrait Mode or Square mode */
                    /** Creating an intent object to start the CountryDetailsActivity */
                    Intent intent = new Intent(getActivity(), DeveloperDetails.class);

                    /** Setting data ( the clicked item's position ) to this intent */

                    intent.putExtra("userName", currentDeveloper.getUserName());
                    intent.putExtra("gitHubUrl", currentDeveloper.getGitHubUrl());
                    intent.putExtra("image", imageByte);
                    // intent.putExtra("imageUrl", currentDeveloper.getThumbnailUrl());

                    /** Starting the activity by passing the implicit intent */
                    startActivity(intent);
                }
            }
        });


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
            feedbackView.setVisibility(View.VISIBLE);
            progressBar();
        }
    }
    public void progressBar(){
        developerProgressBar.setVisibility(View.GONE);
    }


}
