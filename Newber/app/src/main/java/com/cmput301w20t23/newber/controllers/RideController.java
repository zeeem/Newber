package com.cmput301w20t23.newber.controllers;
import android.content.Context;
import androidx.annotation.NonNull;

import com.cmput301w20t23.newber.models.RequestStatus;
import com.cmput301w20t23.newber.models.Location;
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

import java.util.ArrayList;

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

    public void removeRideRequest(RideRequest rideRequest) {
        // Remove request from firebase requests table
        FirebaseDatabase.getInstance().getReference("rideRequests")
                .child(rideRequest.getRequestId()).removeValue();
    }

    public void updateDriverAndRequest(RideRequest request) {
        String driverUid = this.mAuth.getCurrentUser().getUid();
        request.setDriverUid(driverUid);
        request.setStatus(RequestStatus.OFFERED);
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
