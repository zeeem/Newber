package com.cmput301w20t23.newber.controllers;

import com.cmput301w20t23.newber.models.Location;
import com.cmput301w20t23.newber.models.RideRequest;
import com.google.android.libraries.places.api.model.Place;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RideController {
    private final FirebaseDatabase database;
    private final FirebaseAuth mAuth;

    public RideController() {
        this.database = FirebaseDatabase.getInstance();
        this.mAuth = FirebaseAuth.getInstance();
    }
    
    public void createRideRequest(final Location startLocation, final Location endLocation, double cost) {
        String riderUid = this.mAuth.getCurrentUser().getUid();
        RideRequest rideRequest = new RideRequest(startLocation, endLocation, riderUid, cost);
        database.getReference("rideRequests")
                .child(rideRequest.getRequestId())
                .setValue(rideRequest);
        database.getReference("users").child(riderUid).child("currentRequestId").setValue(rideRequest.getRequestId());
    }
}
