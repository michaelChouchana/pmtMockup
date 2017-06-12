package com.powermat.newappmockup;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.transition.TransitionInflater;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.powermat.newappmockup.models.VenueItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<VenueItem> venueItems = new ArrayList<>();
    NavigationView navigationView;
    DrawerLayout drawer;
    Toolbar toolbar;
    public ActionBarDrawerToggle toggle;




    private FragmentManager.OnBackStackChangedListener
            mOnBackStackChangedListener = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            syncActionBarArrowState();
        }
    };

    private void syncActionBarArrowState() {

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.contenair);
        if (currentFragment instanceof ListFragment) {
            toggle.setDrawerIndicatorEnabled(true);
        } else {
            toggle.setDrawerIndicatorEnabled(false);
        }
    }

    private void showFragment(Fragment name, String tag, Bundle bundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (bundle != null)
            name.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contenair, name, tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();


    }

    private void showOfferFragment() {
        Bundle bundle = new Bundle();
        showFragment(new OfferFragment(), null, bundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        toggle.setDrawerIndicatorEnabled(true);
    }

    private void showListFragment() {

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.contenair);
        if (!(currentFragment instanceof ListFragment)) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("venues", venueItems);
            showFragment(new ListFragment(), "ListFragment", bundle);
        }

    }

    private void ShowMapFragment() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.contenair);
        if (!(currentFragment instanceof MapFragment)) {
            MapFragment mapFragment = new MapFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("venues", venueItems);
            showFragment(mapFragment, null, bundle);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.map:
                ShowMapFragment();
                break;
            case R.id.badges:
                showOfferFragment();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        getSupportFragmentManager().removeOnBackStackChangedListener(mOnBackStackChangedListener);
        super.onDestroy();
    }
    private void createData() {
        venueItems = new ArrayList<>();
        venueItems.add(new VenueItem("https://pbs.twimg.com/media/C-SCSZ4U0AAHEtv.jpg:large", "PMT", true, new LatLng(31.806033, 35.092754)));
        venueItems.add(new VenueItem("https://pbs.twimg.com/media/BkUR0FECQAAK193.jpg", "MC DO", false, new LatLng(40.709556, -74.010193)));
        venueItems.add(new VenueItem("https://s10.postimg.org/ypynt02zd/6110496646_cb65da1f2c_z.jpg", "STEACK HOUSE", false, new LatLng(40.709498, -73.960240)));
        venueItems.add(new VenueItem("https://s22.postimg.org/jzut6bysx/19067109309_2fb36084d3_z.jpg", "RESTAURANT", false, new LatLng(40.720973, -73.987169)));
        venueItems.add(new VenueItem("https://s7.postimg.org/7xgn3ibaj/placeimg_700_500_any_1.jpg", "WORK", false, new LatLng(40.757526, -73.865590)));
        venueItems.add(new VenueItem("https://s16.postimg.org/k4e16re6d/33322832535_41fda4b5e4_z.jpg", "NEW YORK", false, new LatLng(40.757526, -73.865590)));
        venueItems.add(new VenueItem("https://s24.postimg.org/nbh4cbno5/placeimg_700_500_arch.jpg", "STREET", false, new LatLng(40.669006, -73.942305)));
        venueItems.add(new VenueItem("https://s12.postimg.org/ukmz9bda5/placeimg_700_500_arch_1.jpg", "PARIS", false, new LatLng(48.846865, 2.341032)));
        venueItems.add(new VenueItem("http://core0.staticworld.net/images/article/2016/09/android-old-habits-100682662-primary.idge.jpg", "ANDROID", false, new LatLng(37.386018, -122.083315)));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("POWERMAT");

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED | ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.BLUETOOTH)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.BLUETOOTH_ADMIN)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.BLUETOOTH,
                            Manifest.permission.BLUETOOTH_ADMIN},
                    123);
        }

        setSupportActionBar(toolbar);
        createData();
        showListFragment();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                syncActionBarArrowState();
            }


            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                syncActionBarArrowState();
            }


        };

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toggle.syncState();
        drawer.setDrawerListener(toggle);
        getSupportFragmentManager().addOnBackStackChangedListener(mOnBackStackChangedListener);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void showFragmentWithTransition(Fragment current, Fragment newFragment, String tag, View sharedView, String sharedElementName) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        //  check if the fragment is in back stack

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (sharedView != null && sharedElementName != null) {
                current.setSharedElementReturnTransition(TransitionInflater.from(this).inflateTransition(R.transition.tre));
                current.setExitTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.no_transition));

                newFragment.setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.tre));
                newFragment.setEnterTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.no_transition));
            }

        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contenair, newFragment, tag);
        fragmentTransaction.addToBackStack(null);

        if (sharedView != null && sharedElementName != null)
            fragmentTransaction.addSharedElement(sharedView, sharedElementName);
        fragmentTransaction.commit();
        //   }
    }


    // double back pressed function
    private static long back_pressed;

    private void doubleClickToExit() {
        if ((back_pressed + 2000) > System.currentTimeMillis())
            finish();
        else
            Toast.makeText(MainActivity.this, "Click again to exit", Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }

    @Override
    public void onBackPressed() {
        syncActionBarArrowState();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.contenair);
            if (currentFragment instanceof ListFragment) {
                doubleClickToExit();

            } else {
                FragmentManager fts = getSupportFragmentManager();
                if (fts.getBackStackEntryCount() > 1) {
                    fts.popBackStackImmediate();

                }
            }
        }

    }
//
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            oneStepBack();
//          //  super.onBackPressed();
//        }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_about) {
            // Handle the camera action
        } else if (id == R.id.help) {

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }
}
