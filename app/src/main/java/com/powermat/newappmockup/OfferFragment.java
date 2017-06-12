package com.powermat.newappmockup;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.powermat.newappmockup.models.VenueItem;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class OfferFragment extends Fragment {
    VenueItem venueItem;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.offer_layout, container, false);
            return rootView;
    }
}





