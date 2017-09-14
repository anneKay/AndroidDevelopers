package com.annekay.android.androiddevelopers.ui;


import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.annekay.android.androiddevelopers.R;

import org.w3c.dom.Text;

import static android.R.attr.name;
import static android.R.attr.screenSize;
import static com.annekay.android.androiddevelopers.ui.DevelopersListFragment.getDevLocation;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeveloperDetailsFragment extends Fragment {
    String gitHubUrl, userName;

    public DeveloperDetailsFragment() {
        // Required empty public constructor
    }

    @SuppressWarnings("deprecated")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.details_items, container, false);
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        int orientation = getResources().getConfiguration().orientation;
        int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;


        // Landscape Mode
        if (screenSize >= Configuration.SCREENLAYOUT_SIZE_LARGE && orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

            setHasOptionsMenu(true);


        // Getting the textview object of the layout to set the details

        TextView textview = (TextView) rootView.findViewById(R.id.url);
        TextView locationText= (TextView) rootView.findViewById(R.id.dev_location);
        ImageView thumbNail = (ImageView) rootView.findViewById(R.id.thumbnail);

        /** Getting the bundle object passed from MainActivity ( in Landscape mode )  or from
         *  DeveloperDetailsActivity ( in Portrait Mode )
         * */
        Bundle extras = getArguments();
        byte[] b = extras.getByteArray("image");

       Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
        BitmapDrawable background = new BitmapDrawable(getContext().getResources(), bmp);

        // Getting the clicked item's position and setting corresponding details in the textview of the detailed fragment
        gitHubUrl = extras.getString("gitHubUrl");
        userName = extras.getString("userName");

        textview.setText(gitHubUrl);
        locationText.setText(getDevLocation());

        // Getting the orientation ( Landscape or Portrait ) of the screen
        //int orientation = getResources().getConfiguration().orientation;

        // Landscape Mode
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            TextView tv = (TextView) rootView.findViewById(R.id.username);
            tv.setText("@"+userName);
        } else{
            ((CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar_layout)).setTitle(userName);
        }

            thumbNail.setBackgroundDrawable(background);
        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage(gitHubUrl);
            }
        });

        return rootView;

    }

    public void openWebPage(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(getActivity(), "No application can handle this request."
                    + " Please install a web browser", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.detail, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share) {
            Intent share = new Intent(android.content.Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_SUBJECT, "Java Developers");
            share.putExtra(Intent.EXTRA_TEXT, "Checkout this awesome developer @" + userName +"\n"+ gitHubUrl);
            startActivity(Intent.createChooser(share, "Share with"));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}




