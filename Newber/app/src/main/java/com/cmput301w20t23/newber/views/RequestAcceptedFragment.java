package com.cmput301w20t23.newber.views;

import android.graphics.Color;
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

public class RequestAcceptedFragment extends Fragment {

    private RideRequest rideRequest;
    private String role;

    public RequestAcceptedFragment(RideRequest request, String role) {
        this.rideRequest = request;
        this.role = role;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // View for this fragment
        View view = inflater.inflate(R.layout.accepted_fragment, container, false);

        // Get layout components
        TextView name = view.findViewById(R.id.rider_main_driver_name);
        TextView phone = view.findViewById(R.id.rider_main_driver_phone);
        TextView email = view.findViewById(R.id.rider_main_driver_email);
        Button button = view.findViewById(R.id.request_accepted_button);

        // Set values of info box
        name.setText(rideRequest.getDriver().getFirstName() + rideRequest.getDriver().getLastName());
        phone.setText(rideRequest.getDriver().getPhone());
        email.setText(rideRequest.getDriver().getEmail());

        // Change button based on role
        switch(role)
        {
            case "Rider": // Cancel button
                button.setBackgroundColor(Color.LTGRAY);
                button.setText("Cancel");
                break;
            case "Driver": // Rider Picked Up button
                button.setBackgroundColor(Color.YELLOW);
                button.setText("Rider picked up");
                break;
        }

        // Bring up profile when name is clicked
        name.setOnClickListener(new NameOnClickListener(role));

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                switch(role) {
                    case "Rider":
                        // TODO: If rider, remove driver from request and set status to PENDING
                        break;
                    case "Driver":
                        // TODO: If driver, set request status to IN_PROGRESS
                        break;
                }
            }
        });

        return view;
    }
}
