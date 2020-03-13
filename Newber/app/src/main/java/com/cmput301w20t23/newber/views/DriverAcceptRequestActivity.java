package com.cmput301w20t23.newber.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cmput301w20t23.newber.R;
import com.cmput301w20t23.newber.controllers.RideController;
import com.cmput301w20t23.newber.models.Driver;
import com.cmput301w20t23.newber.models.Location;
import com.cmput301w20t23.newber.models.RideRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DriverAcceptRequestActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private RideRequest request;
    private RideController rideController;
    private Driver driver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_accept_request);
        request = (RideRequest) getIntent().getSerializableExtra("request");
        driver = (Driver) getIntent().getSerializableExtra("driver");

        System.out.println("DriverAcceptRequestActivity " + driver.getUsername());
        rideController = new RideController();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        setUpTextViews();
    }

    public void setUpTextViews() {
        TextView pickUp = findViewById(R.id.driver_accept_pick_up);
        pickUp.setText(request.getStartLocation().toString());

        TextView dropOff = findViewById(R.id.driver_accept_drop_off);
        dropOff.setText(request.getEndLocation().toString());


        final TextView riderName = findViewById(R.id.driver_accept_rider_name);
        FirebaseDatabase.getInstance()
                .getReference("users")
                .child(request.getRider().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String name = dataSnapshot.child("firstName").getValue(String.class) + " " +
                                dataSnapshot.child("lastName").getValue(String.class);

                        riderName.setText(name);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) { }
                });

        TextView fare = findViewById(R.id.driver_accept_fare);
        fare.setText(String.format("$%s", request.getCost()));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        // Add a marker in Edmonton and move the camera
        this.googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(request.getStartLocation().toLatLng(), 12.0f));
        this.googleMap.addMarker(new MarkerOptions().position(request.getStartLocation().toLatLng()));
        this.googleMap.addMarker(new MarkerOptions().position(request.getEndLocation().toLatLng()));
    }

    public void cancelRequest(View view) {
        setResult(Activity.RESULT_CANCELED, new Intent());
        finish();
    }

    public void acceptRequest(View view) {
        setResult(Activity.RESULT_OK, new Intent());
        request.setDriver(driver);
        rideController.updateDriverAndRequest(request);
        finish();
    }
}
