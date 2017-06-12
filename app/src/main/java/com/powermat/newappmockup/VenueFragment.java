package com.powermat.newappmockup;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.powermat.newappmockup.models.VenueItem;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class VenueFragment extends Fragment {
    VenueItem venueItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_scrolling, container, false);
        //   ((MainActivity) getActivity()).getSupportActionBar().getDisplayOptions()
        ImageView mypic = (ImageView) rootView.findViewById(R.id.mypic);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);
        Bundle b = getArguments();
        if (b != null) {
            String transitionName = null;


            venueItem = (VenueItem) b.getSerializable("venueItem");
            collapsingToolbar.setTitle(venueItem.isInStore() ? "WELCOME TO \n" + venueItem.getDetails().toUpperCase() : "" + venueItem.getDetails().toUpperCase());

            collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
            collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.white));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                transitionName = b.getString("transitionName");

                if (transitionName != null)
                    mypic.setTransitionName(transitionName);
            }
            Picasso.with(getActivity()).load(venueItem.getPicUrl()).into(mypic);


            FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fasb);

            if (venueItem.isInStore())
                fab.setVisibility(View.GONE);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentRoute = new Intent(Intent.ACTION_VIEW);
                    String uri = "geo:0,0?q=" +
                            android.net.Uri.encode(String.format(Locale.ENGLISH, "%s@%f,%f", venueItem.getDetails(),
                                    venueItem.getLatLng().latitude, venueItem.getLatLng().longitude), "UTF-8");
                    intentRoute.setData(Uri.parse(uri));
                    startActivity(intentRoute);
                }
            });
        }


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).toggle.setDrawerIndicatorEnabled(false);
    }
}





