package com.annekay.android.androiddevelopers;

import android.app.Activity;
import android.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.annekay.android.androiddevelopers.R.id.dev_location;


/**
 * Created by HP on 8/23/2017.
 */

public class DevelopersAdapter extends ArrayAdapter<Developer> {

    // Tag for log messages
    private static final String LOG_TAG = DevelopersAdapter.class.getSimpleName();


    // public constructor for the DevelopersAdapter
    public DevelopersAdapter(Activity context, ArrayList<Developer> developers){
        super(context, 0, developers);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listView = convertView;
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.list_items, parent, false);
        }

        Developer currentDeveloper = getItem(position);
        //String mLocation = SettingsActivity.getInstance().getDevLocation();

        ImageView devImageView = (ImageView) listView.findViewById(R.id.thumbnail);
        // thumbnail image
        Picasso.with(getContext()).load(currentDeveloper.getThumbnailUrl()).placeholder(R.mipmap.ic_launcher)
                .resize(50, 50)
                .transform(new ImageTrans())
                .into(devImageView);
        //find the username textview in the list_view
        TextView userName = (TextView) listView.findViewById(R.id.username);
        userName.setText(currentDeveloper.getUserName());
        DevelopersListFragment fragment = new DevelopersListFragment();
        TextView developerLocation = (TextView) listView.findViewById(dev_location);
        developerLocation.setText(fragment.getDevLocation());

        return listView;

    }


}
