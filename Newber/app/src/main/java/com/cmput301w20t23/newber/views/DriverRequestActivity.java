package com.cmput301w20t23.newber.views;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.cmput301w20t23.newber.R;
import com.cmput301w20t23.newber.models.Driver;
import com.cmput301w20t23.newber.models.RideRequest;
import com.cmput301w20t23.newber.models.Rider;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.SphericalUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * The Android Activity that handles General Driver Request Activity.
 *
 * @author Ayushi Patel, Ibrahim Aly
 */
public class DriverRequestActivity extends AppCompatActivity implements OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int DRIVER_ACCEPT_REQUEST = 1;

    private GoogleMap googleMap;
    private View mainLayout;
    private static final int PERMISSION_REQUEST_LOCATION = 0;

    // List View and Adapter for all the Requests coming in
    private ArrayAdapter<RideRequest> requestListAdapter;
    private ListView requestListView;

    // Marker to place when driver selects pick-up location
    private Marker startMarker;

    // For translating Latitude/Longitude to human-readable addresses
    private Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_request);

        Intent parentIntent = getIntent();
        final Driver driver = (Driver) parentIntent.getSerializableExtra("driver");

        mainLayout = findViewById(R.id.main_layout);

        // Start the Google Map Fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Set up the auto-complete and the map-buttons fragments
        setUpAutoCompleteFragments();
        setUpMapButton();

        // Set up the ListView and Adapter to handle receiving requests, and set the Click Listener
        requestListView = findViewById(R.id.request_list);
        requestListAdapter = new ArrayAdapter<>(this, R.layout.request_list_content);
        requestListView.setAdapter(requestListAdapter);
        requestListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getBaseContext(), DriverAcceptRequestActivity.class);
                intent.putExtra("request", requestListAdapter.getItem(i));
                intent.putExtra("driver", driver);
                System.out.println("From driver request activity: " + driver.getUsername());
                startActivityForResult(intent, DRIVER_ACCEPT_REQUEST);
            }
        });
    }

    /**
     * Handling Accepting a Request from DriverAcceptRequestActivity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DRIVER_ACCEPT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                finish();
            }
        }
    }

    /**
     * Sets up auto complete fragments.
     */
    public void setUpAutoCompleteFragments() {
        final AutocompleteSupportFragment startAutocompleteSupportFragment = (AutocompleteSupportFragment)
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

                startMarker = googleMap.addMarker(new MarkerOptions().position(place.getLatLng()));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 12.0f));

                queryOpenRequests(place.getLatLng());
            }

            @Override
            public void onError(@NonNull Status status) {
                System.out.println("An error occurred: " + status);
            }
        });
    }

    /**
     * Function to get human-readable address from a latitude and longitude
     * @param latLng the Latitude/Longitude object
     * @return Returns an address in a String type
     */
    public String getNameFromLatLng(LatLng latLng) {
        List<Address> addresses;

        if (this.geocoder == null)
            this.geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            Address address = addresses.get(0);

            return address.getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Sets up the Pick-Up Location Map Button to be clickable, and allow the user to select
     * a location on the map
     */
    public void setUpMapButton() {
        Button driverMapButton = findViewById(R.id.driver_map_button);

        driverMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DriverRequestActivity.this.googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {
                        if (startMarker != null) {
                            startMarker.remove();
                        }

                        startMarker = googleMap.addMarker(new MarkerOptions().position(latLng));
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
                        queryOpenRequests(latLng);
                    }
                });
            }
        });
    }

    /**
     * After making a permission request, get the result and set myLocationEnabled if successful
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
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

    /**
     * Request Location (MyLocationEnabled) Permissions
     */
    private void requestLocationPermission() {
        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)) {
            Snackbar.make(mainLayout, "Location permission is required",
                    Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Request the permission
                    ActivityCompat.requestPermissions(DriverRequestActivity.this,
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

    /**
     * Sets up the Google Maps UI Settings such as zooming in and out.
     */
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

        // Move the camera to Edmonton
        LatLng Edmonton = new LatLng(53.5461215,-113.4939365);
        this.googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Edmonton, 10.0f));
    }

    /**
     * Finds available requests with a 5 Km distance radius from the current pick-up location
     * @param latLng
     */
    private void queryOpenRequests(final LatLng latLng) {
        // Set up search bounds
        double distanceCenterToCorner = 5000 * Math.sqrt(2.0);
        LatLng southwestCorner =
                SphericalUtil.computeOffset(latLng, distanceCenterToCorner, 225.0);
        LatLng northeastCorner =
                SphericalUtil.computeOffset(latLng, distanceCenterToCorner, 45.0);
        final LatLngBounds searchBounds = new LatLngBounds(southwestCorner, northeastCorner);

        // TODO: Make Sorting more efficient
        // Call the Databasee to find all available requests within a 5km radius, and sort (very simply)
        // them by distance
        FirebaseDatabase.getInstance().getReference("rideRequests").orderByChild("driver").equalTo(null)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<RideRequest> openRequests = new ArrayList<RideRequest>();
                        for (DataSnapshot requestSnapshot : dataSnapshot.getChildren()) {
                            RideRequest request = requestSnapshot.getValue(RideRequest.class);
                            LatLng startLatLng = new LatLng(request.getStartLocation().getLatitude(),
                                    request.getStartLocation().getLongitude());

                            if (searchBounds.contains(startLatLng)) {
                                System.out.println(request.toString());
                                openRequests.add(request);
                            }
                        }

                        Collections.sort(openRequests, new Comparator<RideRequest>() {
                            @Override
                            public int compare(RideRequest o1, RideRequest o2) {
                                double val1 =
                                        (latLng.latitude - o1.getStartLocation().getLatitude()) +
                                        (latLng.longitude - o1.getStartLocation().getLongitude());

                                double val2 =
                                        (latLng.latitude - o2.getStartLocation().getLatitude()) +
                                        (latLng.longitude - o2.getStartLocation().getLongitude());

                                return (int) (val1 - val2);
                            }
                        });

                        updateRequestList(openRequests);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    /**
     * Update the ListView and Adapter to display the open requests available
     * @param openRequests A List of all open requests available to choose from
     */
    private void updateRequestList(ArrayList<RideRequest> openRequests) {
        System.out.println("in updateRequestList");
        for (RideRequest req : openRequests) {
            System.out.println(req.toString());
        }
        requestListAdapter.clear();
        requestListAdapter.addAll(openRequests);
        requestListAdapter.notifyDataSetChanged();
    }

    public void cancelDriverRequest(View view) {
        finish();
    }
}
