package com.annekay.android.androiddevelopers;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static com.annekay.android.androiddevelopers.R.string.details;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeveloperDetailsFragment extends Fragment {
    String gitHubUrl, userName;


    public DeveloperDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.details_items, container, false);
        setHasOptionsMenu(true);


        // Getting the textview object of the layout to set the details
        TextView tv = (TextView) rootView.findViewById(R.id.username);
        TextView textview = (TextView) rootView.findViewById(R.id.url);
        TextView locationText= (TextView) rootView.findViewById(R.id.dev_location);
        ImageView thumbNail = (ImageView) rootView.findViewById(R.id.thumbnail);
        //Button shareButton  = (Button) rootView.findViewById(R.id.share_button);

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
        tv.setText("@"+userName);
        textview.setText(gitHubUrl);
        //get an instance of the DeveloperListFragment
        DevelopersListFragment fragment = new DevelopersListFragment();
        locationText.setText(fragment.getDevLocation());


        thumbNail.setBackgroundDrawable(background);
//        shareButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //openWebPage(gitHubUrl);
//            }
//        });

        return rootView;

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




