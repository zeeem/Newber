package com.cmput301w20t23.newber.models;

import com.google.android.libraries.places.api.model.Place;

import java.io.Serializable;

public class Location implements Serializable {
    private String name;
    private double latitude;
    private double longitude;

    public Location() {

    }

    public Location(Place place) {
        this.name = place.getName();
        this.latitude = place.getLatLng().latitude;
        this.longitude = place.getLatLng().longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
