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
import com.cmput301w20t23.newber.models.Driver;
import com.cmput301w20t23.newber.models.RideRequest;
import com.cmput301w20t23.newber.models.Rider;

import androidx.fragment.app.Fragment;

/**
 * The Android Fragment that is shown when the user has an accepted current ride request.
 *
 * @author Amy Hou
 */
public class RequestAcceptedFragment extends Fragment {

    private RideRequest rideRequest;
    private String role;
    private Rider rider;
    private Driver driver;

    /**
     * Instantiates a new RequestAcceptedFragment.
     *
     * @param request the user's current request
     * @param role    the user's role
     */
    public RequestAcceptedFragment(RideRequest request, String role, Rider rider, Driver driver) {
        this.rideRequest = request;
        this.role = role;
        this.rider = rider;
        this.driver = driver;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // View for this fragment
        View view = inflater.inflate(R.layout.accepted_fragment, container, false);

        // Get view elements
        TextView pickupLocationTextView = view.findViewById(R.id.pickup_location);
        TextView dropoffLocationTextView = view.findViewById(R.id.dropoff_location);
        TextView fareTextView = view.findViewById(R.id.ride_fare);
        TextView nameTextView = view.findViewById(R.id.rider_main_driver_name);
        TextView phoneTextView = view.findViewById(R.id.rider_main_driver_phone);
        TextView emailTextView = view.findViewById(R.id.rider_main_driver_email);
        Button button = view.findViewById(R.id.request_accepted_button);

        // Set view elements
        pickupLocationTextView.setText(rideRequest.getStartLocation().getName());
        dropoffLocationTextView.setText(rideRequest.getEndLocation().getName());
        fareTextView.setText(Double.toString(rideRequest.getCost()));

        // Change UI based on role
        switch(role)
        {
            case "Rider": // Cancel button
                button.setBackgroundColor(Color.LTGRAY);
                button.setText("Cancel");

                // Set values of info box
                nameTextView.setText(driver.getUsername());
                phoneTextView.setText(driver.getPhone());
                emailTextView.setText(driver.getEmail());

                button.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        // TODO: If rider, remove driver from request and set status to PENDING
                    }
                });

                // Bring up profile when name is clicked
                nameTextView.setOnClickListener(new NameOnClickListener(role, driver));
                break;

            case "Driver": // Rider Picked Up button
                button.setBackgroundColor(Color.YELLOW);
                button.setText("Rider picked up");

                // Set values of info box
                nameTextView.setText(rider.getUsername());
                phoneTextView.setText(rider.getPhone());
                emailTextView.setText(rider.getEmail());

                button.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        // TODO: If driver, set request status to IN_PROGRESS
                    }
                });

                // Bring up profile when name is clicked
                nameTextView.setOnClickListener(new NameOnClickListener(role, rider));
                break;
        }

        return view;
    }
}
