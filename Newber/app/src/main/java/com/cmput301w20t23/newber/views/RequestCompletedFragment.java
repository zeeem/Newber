package com.cmput301w20t23.newber.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import com.cmput301w20t23.newber.R;
import com.cmput301w20t23.newber.models.RideRequest;

public class RequestCompletedFragment extends Fragment {

    private RideRequest rideRequest;

    public RequestCompletedFragment(RideRequest request) {
        rideRequest = request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflater of layout for this fragment
        View view = inflater.inflate(R.layout.completed_fragment, container, false);

        // Get view elements
        TextView pickupLocationTextView = view.findViewById(R.id.pickup_location);
        TextView dropoffLocationTextView = view.findViewById(R.id.dropoff_location);
        TextView fareTextView = view.findViewById(R.id.ride_fare);
        TextView nameTextView = view.findViewById(R.id.rider_main_driver_name);
        TextView phoneTextView = view.findViewById(R.id.rider_main_driver_phone);
        TextView emailTextView = view.findViewById(R.id.rider_main_driver_email);
        Button completeRequestButton = view.findViewById(R.id.rider_complete_ride_button);

        // Set view elements
//        pickupLocationTextView.setText(rideRequest.getStart().getName());
//        dropoffLocationTextView.setText(rideRequest.getEnd().getName());
        fareTextView.setText(Double.toString(rideRequest.getCost()));

        // Set driver box information
        nameTextView.setText(rideRequest.getDriver().getFirstName() + rideRequest.getDriver().getLastName());
        phoneTextView.setText(rideRequest.getDriver().getPhone());
        emailTextView.setText(rideRequest.getDriver().getEmail());

        completeRequestButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO: remove request from firebase user and the requests table
            }
        });
        return view;
    }
}