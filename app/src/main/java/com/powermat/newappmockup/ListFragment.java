package com.powermat.newappmockup;

/**
 * Created by mchouchana on 5/9/2017.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.powermat.newappmockup.models.VenueItem;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class ListFragment extends Fragment {
    /**
     * A simple {@link Fragment} subclass.
     */
    private Context context;
    private RecyclerView venueList;
    private Adapter adapter;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.list_fragment_contenair, container, false);
        context = getActivity();
        venueList = (RecyclerView) rootView.findViewById(R.id.venueList);
        venueList.setItemViewCacheSize(15);

        Bundle b = getArguments();
        if (b != null) {
            ArrayList<VenueItem> venueItems = (ArrayList<VenueItem>) b.getSerializable("venues");
            for (int i = 0; i < venueItems.size(); i++) {
                Picasso.with(context).load(venueItems.get(i).getPicUrl()).fetch(new Callback() {
                    @Override
                    public void onSuccess() {
                    }
                    @Override
                    public void onError() {
                    }
                });
            }
            adapter = new Adapter(venueItems, context, this);
            venueList.setLayoutManager(new LinearLayoutManager(context));
            venueList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
            venueList.setHasFixedSize(true);
            venueList.setAdapter(adapter);
        }
        return rootView;
    }

    /**
     * function to open the movie detail fragment
     *
     * @param position Movie list position
     */
    public void openVenueFragment(int position, View view,VenueItem venueItem) {
        if (context instanceof MainActivity) {
            VenueFragment venueFragment = new VenueFragment();
            Bundle bundle = new Bundle();

            bundle.putSerializable("venueItem",venueItem);
            bundle.putString("transitionName", "transition" + position);

            venueFragment.setArguments(bundle);
            ((MainActivity) context).showFragmentWithTransition(this, venueFragment, "venueFragment", view, "transition" + position);
        }
    }



}