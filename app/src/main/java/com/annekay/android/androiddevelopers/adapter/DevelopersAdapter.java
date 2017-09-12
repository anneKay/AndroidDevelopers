package com.annekay.android.androiddevelopers.adapter;

import android.app.Activity;
import android.app.ListFragment;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.annekay.android.androiddevelopers.ui.DevelopersListFragment;
import com.annekay.android.androiddevelopers.ui.DevelopersListFragment;
import com.annekay.android.androiddevelopers.R;
import com.annekay.android.androiddevelopers.model.Developer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.orientation;
import static com.annekay.android.androiddevelopers.R.id.dev_location;
import static com.annekay.android.androiddevelopers.ui.DevelopersListFragment.getDevLocation;


/**
 * Created by annekay on 8/23/2017.
 */

public class DevelopersAdapter extends ArrayAdapter<Developer> {

    // Tag for log messages
    private static final String LOG_TAG = DevelopersAdapter.class.getSimpleName();
    List<Developer> mdevelopers;



    // public constructor for the DevelopersAdapter
    public DevelopersAdapter(Activity context, ArrayList<Developer> developers){
        super(context, 0, developers);
        mdevelopers = developers;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listView = convertView;
        ViewHolder holder;
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.list_items, parent, false);
            holder = new ViewHolder();
            holder.userNameView = (TextView) listView.findViewById(R.id.username);
            holder.locationView = (TextView) listView.findViewById(R.id.dev_location);
            holder.developerView = (ImageView) listView.findViewById(R.id.thumbnail);
            listView.setTag(holder);
        } else {
            holder = (ViewHolder)listView.getTag();
        }

        Developer currentDeveloper = getItem(position);
        //String mLocation = SettingsActivity.getInstance().getDevLocation();


        Picasso.with(getContext()).load(currentDeveloper.getThumbnailUrl()).placeholder(R.drawable.ic_action_name)
                .resize(200, 200).into(holder.developerView);
        //find the username textview in the list_view

        holder.userNameView.setText(currentDeveloper.getUserName());
        holder.locationView.setText(getDevLocation());

        return listView;

    }

    private class ViewHolder {
        private TextView userNameView;
        private TextView locationView;
        private ImageView developerView;
    }


}
