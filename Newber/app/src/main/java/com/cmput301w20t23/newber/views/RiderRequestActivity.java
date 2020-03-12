package com.cmput301w20t23.newber.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cmput301w20t23.newber.R;
import com.cmput301w20t23.newber.controllers.RideController;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.Locale;

/**
 * The Android Activity that handles the creation of a ride request by a rider.
 *
 * @author Ibrahim Aly, Ayushi Patel
 */
public class RiderRequestActivity extends AppCompatActivity implements OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private GoogleMap googleMap;
    private View mainLayout;
    private static final int PERMISSION_REQUEST_LOCATION = 0;

    private Place startPlace;
    private Place endPlace;

    private Marker startMarker;
    private Marker endMarker;

    private RideController rideController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_request);
        mainLayout = findViewById(R.id.main_layout);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        setUpAutoCompleteFragments();
        rideController = new RideController(this);
    }

    /**
     * Sets up auto complete fragments.
     */
    public void setUpAutoCompleteFragments() {
        AutocompleteSupportFragment startAutocompleteSupportFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.start_autocomplete_fragment);
        startAutocompleteSupportFragment.setHint("Search");

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.API_KEY), Locale.CANADA);
        }
        startAutocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        startAutocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                System.out.println("From Place: " + place.getName() + ", latlng: " + place.getLatLng());
                if (startMarker != null) {
                    startMarker.remove();
                }
                startMarker = googleMap.addMarker(new MarkerOptions().position(place.getLatLng()).title("Start"));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 12.0f));
                startPlace = place;
            }

            @Override
            public void onError(@NonNull Status status) {
                System.out.println("An error occurred: " + status);
            }
        });

        AutocompleteSupportFragment endAutocompleteSupportFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.end_autocomplete_fragment);
        endAutocompleteSupportFragment.setHint("Search");

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.API_KEY), Locale.CANADA);
        }
        endAutocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        endAutocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                System.out.println("To Place: " + place.getName() + ", latlng: " + place.getLatLng());
                if (endMarker != null) {
                    endMarker.remove();
                }
                endMarker = googleMap.addMarker(new MarkerOptions().position(place.getLatLng()).title("End"));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(),12.0f));
                endPlace = place;
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

    private void setUpUiSettings() {
        UiSettings uiSettings = this.googleMap.getUiSettings();
        uiSettings.setAllGesturesEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setCompassEnabled(true);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        setUpUiSettings();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            this.googleMap.setMyLocationEnabled(true);
        } else {
            requestLocationPermission();
        }

        // Add a marker in Edmonton and move the camera
        LatLng Edmonton = new LatLng(53.5461215,-113.4939365);
        this.googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Edmonton, 10.0f));
    }

    public void cancelRiderRequest(View view) {
        finish();
    }

    public void confirmRiderRequest(View view) {
        if ((startPlace == null) || (endPlace == null)) {
            Toast.makeText(this, "Please select endpoints", Toast.LENGTH_SHORT).show();
            return;
        }

        rideController.createRideRequest(startPlace, endPlace, 10.00);
        finish();
    }
}
