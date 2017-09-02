package com.annekay.android.androiddevelopers;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.mode;
import static android.R.attr.orientation;
import static android.R.id.empty;
import static android.support.v7.widget.AppCompatDrawableManager.get;

/**
 * A simple {@link Fragment} subclass.
 */
public class DevelopersListFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Developer>>, SwipeRefreshLayout.OnRefreshListener{
   // private static final String JAVA_DEVELOPER = "https://api.github.com/search/users?q=language:java+location:lagos&page=1&per_page=100";
    private static final String JAVA_DEVELOPERS = "https://api.github.com/search/users?page=1&per_page=100";
    /** Tag for the log messages */
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    private DevelopersAdapter developersAdapter;
    private static String devLocation;
    private static final int JSON_LOADER_ID = 1;
    ProgressBar developerProgressBar;
    public String userNameDetail, gitHubUrlDetails;
    ListView listView;
    TextView feedbackView;
    SwipeRefreshLayout mSwipeRefreshLayout;



    public DevelopersListFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main, container, false);
        setHasOptionsMenu(true);

        feedbackView = (TextView) rootView.findViewById(android.R.id.empty);

        developerProgressBar = (ProgressBar)rootView.findViewById(R.id.determinateBar);
        mSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_container);
        //apply different colors to the swipe refresh layout
        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);

        listView = (ListView) rootView.findViewById(R.id.list);

        //initialize the developersAdapter
        developersAdapter = new DevelopersAdapter(getActivity(), new ArrayList<Developer>());
        listView.setAdapter(developersAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(this);
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


                // Getting the orientation ( Landscape or Portrait ) of the screen
                int orientation = getResources().getConfiguration().orientation;
                int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;


                // Landscape Mode
                if(screenSize >= Configuration.SCREENLAYOUT_SIZE_LARGE && orientation == Configuration.ORIENTATION_LANDSCAPE){
                    // Getting the fragment manager


                    // Getting the fragmenttransaction object, which can be used to add, remove or replace a fragment
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                    Fragment prevFrag = getFragmentManager().findFragmentByTag("developers.details");

                    // Remove the existing detailed fragment object if it exists
                    if(prevFrag!=null)
                        fragmentTransaction.remove(prevFrag);

                    // Instantiating the fragment CountryDetailsFragment */
                    DeveloperDetailsFragment fragment = new DeveloperDetailsFragment();

                    // Creating a bundle object to pass the data(the clicked item's position) from the activity to the fragment */
                    Bundle b = new Bundle();

                    //Setting the data to the bundle object

                    b.putString("userName", userNameDetail);
                    b.putString("gitHubUrl", gitHubUrlDetails);
                    b.putByteArray("image", imageByte);

                    // Setting the bundle object to the fragment
                    fragment.setArguments(b);

                    // Adding the fragment to the fragment transaction
                    fragmentTransaction.add(R.id.detail_fragment_container, fragment,"developers.details");

                    // Adding this transaction to backstack */
                    fragmentTransaction.addToBackStack(null).commit();



                }else{
                    // In Portrait Mode or Square mode */
                    // Creating an intent object to start the CountryDetailsActivity */
                    Intent intent = new Intent(getActivity(), DeveloperDetails.class);

                    // Setting data ( the clicked item's position ) to this intent */

                    intent.putExtra("userName", currentDeveloper.getUserName());
                    intent.putExtra("gitHubUrl", currentDeveloper.getGitHubUrl());
                    intent.putExtra("image", imageByte);
                    startActivity(intent);
                }
            }
        });
        return rootView;
    }

    @Override
    public Loader<List<Developer>> onCreateLoader(int i, Bundle bundle) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        devLocation = sharedPrefs.getString(
                getString(R.string.settings_developer_key),
                getString(R.string.settings_dev_location_default));
        Uri baseUri = Uri.parse(JAVA_DEVELOPERS);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("q", "language:java location:"+devLocation);
        return new JsonLoader(getActivity(), uriBuilder.toString());
        // TODO: Create a new loader for the given URL
        //return new JsonLoader(getActivity(), JAVA_DEVELOPER);
    }
    @Override

    public void onLoadFinished(Loader<List<Developer>> loader, List<Developer> data) {

        // TODO: Update the UI with the result
        developersAdapter.clear();
        developersAdapter.addAll(data);
        developersAdapter.notifyDataSetChanged();
        // check if there is developer data found, if not update the UI with feedback
        if (developersAdapter.getCount() == 0) {
            listView.setVisibility(View.GONE);
            feedbackView.setVisibility(View.VISIBLE);
        } else {
            listView.setVisibility(View.VISIBLE);
            feedbackView.setVisibility(View.GONE);
        } mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        progressBar();
    }


    @Override
    public void onLoaderReset(Loader<List<Developer>> loader) {

        // TODO: Loader reset, so we can clear out our existing data.
        developersAdapter.clear();
    }
//
    @Override
    public void onRefresh() {
        detectNetworkState();
       // feedbackView.setVisibility(View.GONE);
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
            listView.setEmptyView(getActivity().findViewById(android.R.id.empty));
            feedbackView.setVisibility(View.VISIBLE);
            feedbackView.setText("No Internet connection");
            progressBar();
        }
    }
    public void progressBar() {
        developerProgressBar.setVisibility(View.GONE);
    }

   //inflate the menu item if it is present
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public static String getDevLocation(){
        return devLocation;
    }


}
