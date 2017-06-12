package com.powermat.newappmockup.models;




import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by mchouchana on 5/10/2017.
 */

public class VenueItem implements Serializable {
    private static final long serialVersionUID = 46543445l;
    private String picUrl;
    private String details;
    private transient LatLng latLng;

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLocation(LatLng LatLng) {
        this.latLng = LatLng;
    }



    public boolean isInStore() {
        return isInStore;
    }

    public void setInStore(boolean inStore) {
        isInStore = inStore;
    }

    private boolean isInStore;

    public VenueItem(String picUrl, String details,boolean inStore, LatLng latLng) {
        this.picUrl = picUrl;
        this.details = details;
        this.isInStore=inStore;
        this.latLng=latLng;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }



}
