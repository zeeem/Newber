package com.cmput301w20t23.newber.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.cmput301w20t23.newber.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.Locale;

public class RiderRequestActivity extends AppCompatActivity implements OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private GoogleMap googleMap;
    private View mainLayout;
    private static final int PERMISSION_REQUEST_LOCATION = 0;
    private AutocompleteSupportFragment fromAutocompleteSupportFragment;
    private AutocompleteSupportFragment toAutocompleteSupportFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_request);
        mainLayout = findViewById(R.id.main_layout);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        setUpAutoCompleteFragments();
    }

    public void setUpAutoCompleteFragments() {
        fromAutocompleteSupportFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.from_autocomplete_fragment);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyAGmBxEjzU68YpgCNWsldO73dFcwnj0BT8", Locale.CANADA);
        }

        fromAutocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        fromAutocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                System.out.println("From Place: " + place.getName() + ", latlng: " + place.getLatLng());
                RiderRequestActivity.this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
            }

            @Override
            public void onError(@NonNull Status status) {
                System.out.println("An error occurred: " + status);
            }
        });

        toAutocompleteSupportFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.to_autocomplete_fragment);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyAGmBxEjzU68YpgCNWsldO73dFcwnj0BT8", Locale.CANADA);
        }

        toAutocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        toAutocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                System.out.println("To Place: " + place.getName() + ", latlng: " + place.getLatLng());
                RiderRequestActivity.this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
            }

            @Override
            public void onError(@NonNull Status status) {
                System.out.println("An error occurred: " + status);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_LOCATION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(mainLayout, "Location permission was granted.",
                        Snackbar.LENGTH_SHORT)
                        .show();

                this.googleMap.setMyLocationEnabled(true);
            } else {
                // Permission request was denied.
                Snackbar.make(mainLayout, "Location permission was denied",
                        Snackbar.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void requestLocationPermission() {
        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)) {
            Snackbar.make(mainLayout, "Location permission is required",
                    Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Request the permission
                    ActivityCompat.requestPermissions(RiderRequestActivity.this,
                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                            PERMISSION_REQUEST_LOCATION);
                }
            }).show();

        } else {
            Snackbar.make(mainLayout, "Location unavailable", Snackbar.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_LOCATION);
        }
    }

    private void setupUiSettings() {
        UiSettings uiSettings = this.googleMap.getUiSettings();
        uiSettings.setAllGesturesEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setCompassEnabled(true);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        setupUiSettings();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            this.googleMap.setMyLocationEnabled(true);
        } else {
            requestLocationPermission();
        }

        // Add a marker in Sydney and move the camera
        LatLng edmonton = new LatLng(53.5558749,-113.772903);
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(edmonton));
    }
}
