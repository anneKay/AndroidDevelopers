package com.annekay.android.androiddevelopers;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.annekay.android.androiddevelopers.ui.DeveloperDetailsFragment;

import static android.R.attr.fragment;

public class DeveloperDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_developers);

        // Getting the fragment manager for fragment related operations
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Getting the fragmenttransaction object, which can be used to add, remove or replace a fragment
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //Instantiating the fragment CountryDetailsFragment
        DeveloperDetailsFragment detailsFragment = new DeveloperDetailsFragment();

        // Creating a bundle object to pass the data(the clicked item's position) from the activity to the fragment */
        Bundle b = new Bundle();

        // Setting the data to the bundle object from the Intent*/
        b.putString("userName", getIntent().getStringExtra("userName"));
        b.putString("gitHubUrl", getIntent().getStringExtra("gitHubUrl"));
        b.putByteArray("image", getIntent().getByteArrayExtra("image"));
        // Setting the bundle object to the fragment */
        detailsFragment.setArguments(b);

        // Adding the fragment to the fragment transaction */

        fragmentTransaction.add(R.id.developers_details, detailsFragment);

        // Making this transaction in effect */
        fragmentTransaction.commit();

    }
}
