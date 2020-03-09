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

        TextView name = view.findViewById(R.id.rider_main_driver_name);
        TextView phone = view.findViewById(R.id.rider_main_driver_phone);
        TextView email = view.findViewById(R.id.rider_main_driver_email);

        // Set driver box information
        name.setText(rideRequest.getDriver().getFirstName() + rideRequest.getDriver().getLastName());
        phone.setText(rideRequest.getDriver().getPhone());
        email.setText(rideRequest.getDriver().getEmail());

        Button completeRequestButton = view.findViewById(R.id.rider_complete_ride_button);
        completeRequestButton.setOnClickListener(new View.OnClickListener()
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