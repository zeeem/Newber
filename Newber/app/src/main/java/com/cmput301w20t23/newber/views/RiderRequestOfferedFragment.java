package com.cmput301w20t23.newber.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cmput301w20t23.newber.R;
import com.cmput301w20t23.newber.models.RideRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class RiderRequestOfferedFragment extends Fragment {

    private RideRequest rideRequest;
    private FirebaseAuth mAuth;

    public RiderRequestOfferedFragment(RideRequest request) {
        this.rideRequest = request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflater of layout for this fragment
        View view = inflater.inflate(R.layout.rider_pending_fragment, container, false);

        TextView driverName = view.findViewById(R.id.rider_main_driver_name);
        TextView driverPhone = view.findViewById(R.id.rider_main_driver_phone);
        TextView driverEmail = view.findViewById(R.id.rider_main_driver_email);

        final Button acceptOfferButton = view.findViewById(R.id.rider_accept_offer_button);
        final Button declineOfferButton = view.findViewById(R.id.rider_decline_offer_button);

//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser currentUser  = mAuth.getCurrentUser();
//
//        if (currentUser != null) {
//            FirebaseDatabase.getInstance().getReference("users")
//                    .child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    String role = dataSnapshot.child("role").getValue(String.class);
//                    System.out.println(role);
//
//                    switch (role) {
//                        case "Driver":
//                            // Hide buttons of fragment
//                            acceptOfferButton.setVisibility(View.INVISIBLE);
//                            declineOfferButton.setVisibility(View.INVISIBLE);
//                            break;
//                    }
//                }
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//        }

        // Set Values of Driver Info Box
        driverName.setText(rideRequest.getDriver().getFirstName() + rideRequest.getDriver().getLastName());
        driverPhone.setText(rideRequest.getDriver().getPhone());
        driverEmail.setText(rideRequest.getDriver().getEmail());

        driverName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Show driver profile
            }
        });

        acceptOfferButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO: Leave driver attached to request on firebase and set request status to ACCEPTED
            }
        });

        declineOfferButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO: Request status returns to PENDING and remove driver from request on Firebase
            }
        });

        return view;
    }
}
