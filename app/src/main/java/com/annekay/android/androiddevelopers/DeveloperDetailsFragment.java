package com.annekay.android.androiddevelopers;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeveloperDetailsFragment extends Fragment {


    public DeveloperDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_items, container, false);

        /** Getting the textview object of the layout to set the details */
        TextView tv = (TextView) rootView.findViewById(R.id.username);
        TextView textview = (TextView) rootView.findViewById(R.id.url);
        ImageView thumbNail = (ImageView) rootView.findViewById(R.id.thumbnail);
        Button shareButton  = (Button) rootView.findViewById(R.id.share_button);

        /** Getting the bundle object passed from MainActivity ( in Landscape mode )  or from
         *  CountryDetailsActivity ( in Portrait Mode )
         * */
        Bundle extras = getArguments();
        byte[] b = extras.getByteArray("image");

        Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
        BitmapDrawable background = new BitmapDrawable(getContext().getResources(), bmp);


        /** Getting the clicked item's position and setting corresponding details in the textview of the detailed fragment */
        final String gitHubUrl = extras.getString("gitHubUrl");
        tv.setText(extras.getString("userName"));
        textview.setText(gitHubUrl);


        thumbNail.setBackgroundDrawable(background);
        shareButton.setVisibility(View.VISIBLE);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openWebPage(gitHubUrl);
            }
        });

        return rootView;

    }
    // Method to send an intent to the browser to open up developer web page
//    public void openWebPage(String url) {
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
//            startActivity(intent);
//        } else {
//            Toast.makeText(getActivity(), "No application can handle this request."
//                    + " Please install a web browser", Toast.LENGTH_SHORT).show();
//        }
//    }

}




