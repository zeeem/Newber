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

public class RequestPendingFragment extends Fragment {

    private RideRequest rideRequest;

    public RequestPendingFragment(RideRequest request) {
        rideRequest = request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflater of layout for this fragment
        View view = inflater.inflate(R.layout.pending_fragment, container, false);

        // Get view elements
        TextView pickupLocationTextView = view.findViewById(R.id.pickup_location);
        TextView dropoffLocationTextView = view.findViewById(R.id.dropoff_location);
        TextView fareTextView = view.findViewById(R.id.ride_fare);
        Button cancelRequestButton = view.findViewById(R.id.rider_pending_request_button);

        // Set view elements
//        pickupLocationTextView.setText(rideRequest.getStart().getName());
//        dropoffLocationTextView.setText(rideRequest.getEnd().getName());
        fareTextView.setText(Double.toString(rideRequest.getCost()));

        cancelRequestButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO: remove request from firebase user and requests table
            }
        });
        return view;
    }
}