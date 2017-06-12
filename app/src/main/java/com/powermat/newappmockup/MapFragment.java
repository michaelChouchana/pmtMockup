package com.powermat.newappmockup;

/**
 * Created by mchouchana on 5/11/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.powermat.newappmockup.models.VenueItem;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;


    ArrayList<VenueItem> venueItems;

    private View rootView;

    public void addCustomMarkersToMap( final VenueItem venueItem) {
         Target loadtarget;
 loadtarget = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                Marker marker;

                marker =   mMap.addMarker(new
                        MarkerOptions().position(venueItem.getLatLng()).
                        title(venueItem.getDetails()).icon(BitmapDescriptorFactory.fromBitmap(getRoundedCornerBitmap(getResizedBitmap(bitmap,150,150),50))));
                marker.setTag(venueItem);
                marker.hideInfoWindow();

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }


        };

        Picasso.with(getActivity()).load(venueItem.getPicUrl()).into(loadtarget);
    }

    public void openVenueFragment(VenueItem venueItem) {
            VenueFragment venueFragment = new VenueFragment();
            Bundle bundle = new Bundle();
            bundle.putString("url", venueItem.getPicUrl());
            bundle.putString("title", venueItem.getDetails());
            bundle.putBoolean("isInStore", venueItem.isInStore());
        bundle.putSerializable("venueItem",venueItem);


            venueFragment.setArguments(bundle);
            ((MainActivity) getActivity()).showFragmentWithTransition(this, venueFragment, "venueFragment", null, null);
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        FragmentManager fm = getChildFragmentManager();

        if(rootView==null){
            rootView = LayoutInflater.from(getActivity()).inflate(
                    R.layout.fragment_map, container, false);
        }


        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentByTag("mapFragment");
        if (mapFragment == null) {
            mapFragment = new SupportMapFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.contenair, mapFragment, "mapFragment");
            ft.commit();
            fm.executePendingTransactions();
        }


        mapFragment.getMapAsync(this);
        Bundle b = getArguments();
        if (b != null) {
         venueItems = (ArrayList<VenueItem>) b.getSerializable("venues");
        }
        return rootView;
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * In this case, we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device.
     * This method will only be triggered once the user has installed
     Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                VenueItem venueItem = (VenueItem)marker.getTag();
                openVenueFragment(venueItem);
                return true;
            }
        });

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return new View(getActivity());
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });

        for(int i=0;i<venueItems.size();i++){
            VenueItem venueItem = venueItems.get(i);
            addCustomMarkersToMap(venueItem);
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom( new LatLng(40.709556, -74.010193),12f));
    }


    private  Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }


    private Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
      //  bm.recycle();
        return resizedBitmap;
    }
}