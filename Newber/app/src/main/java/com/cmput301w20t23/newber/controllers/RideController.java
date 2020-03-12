package com.cmput301w20t23.newber.controllers;

import android.content.Context;

import androidx.annotation.NonNull;

import com.cmput301w20t23.newber.models.RideRequest;
import com.cmput301w20t23.newber.models.Rider;
import com.cmput301w20t23.newber.views.DriverRequestActivity;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.libraries.places.api.model.Place;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;

public class RideController {
    private Context context;
    private final FirebaseDatabase database;
    private final FirebaseAuth mAuth;

    public RideController(Context context) {
        this.context = context;
        this.database = FirebaseDatabase.getInstance();
        this.mAuth = FirebaseAuth.getInstance();
    }
    
    public void createRideRequest(Place startPlace, Place endPlace, double cost) {
        String requestId = database.getReference("rideRequests").push().getKey();
        String riderUid = this.mAuth.getCurrentUser().getUid();
        RideRequest rideRequest = new RideRequest(requestId, startPlace, endPlace, riderUid, cost);
        database.getReference("rideRequests")
                .child(requestId)
                .setValue(rideRequest);
        updateUserCurrentRequest(rideRequest.getRequestId());
    }

    public void updateDriverAndRequest(RideRequest request) {
        String driverUid = this.mAuth.getCurrentUser().getUid();
        request.setDriverUid(driverUid);
        updateRideRequest(request);
        updateUserCurrentRequest(request.getRequestId());
    }

    public void updateRideRequest(RideRequest request) {
        database.getReference("rideRequests")
                .child(request.getRequestId())
                .setValue(request);
    }

    private void updateUserCurrentRequest(String requestId) {
        String userUid = mAuth.getCurrentUser().getUid();
        database.getReference("users")
                .child(userUid)
                .child("currentRequestId")
                .setValue(requestId);
    }
}
