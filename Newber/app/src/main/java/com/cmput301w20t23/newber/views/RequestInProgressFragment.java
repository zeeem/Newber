package com.cmput301w20t23.newber.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cmput301w20t23.newber.R;
import com.cmput301w20t23.newber.controllers.NameOnClickListener;
import com.cmput301w20t23.newber.models.RideRequest;

import androidx.fragment.app.Fragment;

public class RequestInProgressFragment extends Fragment {

    private RideRequest rideRequest;
    private String role;

    public RequestInProgressFragment(RideRequest request, String role) {
        this.rideRequest = request;
        this.role = role;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // View for this fragment
        View view = inflater.inflate(R.layout.in_progress_fragment, container, false);

        TextView name = view.findViewById(R.id.rider_main_driver_name);
        TextView phone = view.findViewById(R.id.rider_main_driver_phone);
        TextView email = view.findViewById(R.id.rider_main_driver_email);

        Button completeButton = view.findViewById(R.id.driver_complete_ride_button);

        switch (role)
        {
            case "Rider":
                // Set values of info box
                name.setText(rideRequest.getDriver().getFirstName() + rideRequest.getDriver().getLastName());
                phone.setText(rideRequest.getDriver().getPhone());
                email.setText(rideRequest.getDriver().getEmail());

                // Show complete ride button only for Driver
                completeButton.setVisibility(View.INVISIBLE);
                break;

            case "Driver":
                // Set values of info box
                name.setText(rideRequest.getRider().getFirstName() + rideRequest.getDriver().getLastName());
                phone.setText(rideRequest.getRider().getPhone());
                email.setText(rideRequest.getRider().getEmail());
                break;
        }

        // Bring up profile when name is clicked
        name.setOnClickListener(new NameOnClickListener(role));

        completeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO: Set request status to COMPLETED and move Driver to PAYMENT screen
            }
        });

        return view;
    }
}
