package com.powermat.newappmockup;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.powermat.newappmockup.models.VenueItem;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mchouchana on 3/20/2017.
 */
public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<VenueItem> venueItems;
    ListFragment listFragment;
    private Context context;

    public Adapter(ArrayList<VenueItem> venueItems, Context context, ListFragment listFragment) {
        this.venueItems = venueItems;
        this.context = context;
        this.listFragment = listFragment;
    }


    @Override
    public int getItemCount() {
        return venueItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        protected ImageView imageView;
        protected TextView textView;
        protected CardView cardView;

        public ViewHolder(View v) {
            super(v);
            imageView = (ImageView) v.findViewById(R.id.mypic);
            textView = (TextView) v.findViewById(R.id.card_text);
            cardView = (CardView) v.findViewById(R.id.cardView);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.image_item, null));

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final VenueItem venueItem = venueItems.get(position);

        ViewHolder viewHolder = (ViewHolder) holder;

        Picasso.with(context).load(venueItem.getPicUrl()).priority(Picasso.Priority.HIGH).resize(1500, 0).into(viewHolder.imageView);

        viewHolder.textView.setText(venueItem.getDetails());
        viewHolder.imageView.setTransitionName("transition" + position);
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listFragment.openVenueFragment(position, v.findViewById(R.id.mypic), venueItem);
            }
        });

    }
}


