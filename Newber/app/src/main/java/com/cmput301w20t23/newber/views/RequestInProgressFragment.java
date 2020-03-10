package com.cmput301w20t23.newber.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cmput301w20t23.newber.R;
import com.cmput301w20t23.newber.models.NameOnClickListener;
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

        // Get view elements
        TextView pickupLocationTextView = view.findViewById(R.id.pickup_location);
        TextView dropoffLocationTextView = view.findViewById(R.id.dropoff_location);
        TextView fareTextView = view.findViewById(R.id.ride_fare);
        TextView nameTextView = view.findViewById(R.id.rider_main_driver_name);
        TextView phoneTextView = view.findViewById(R.id.rider_main_driver_phone);
        TextView emailTextView = view.findViewById(R.id.rider_main_driver_email);
        Button completeButton = view.findViewById(R.id.driver_complete_ride_button);

        // Set view elements
//        pickupLocationTextView.setText(rideRequest.getStart().getName());
//        dropoffLocationTextView.setText(rideRequest.getEnd().getName());
        fareTextView.setText(Double.toString(rideRequest.getCost()));

        switch (role)
        {
            case "Rider":
                // Set values of info box
//                nameTextView.setText(rideRequest.getDriver().getFirstName() + " " + rideRequest.getDriver().getLastName());
//                phoneTextView.setText(rideRequest.getDriver().getPhone());
//                emailTextView.setText(rideRequest.getDriver().getEmail());

                // Complete ride button only visible by driver; rider hides it
                completeButton.setVisibility(View.INVISIBLE);
                break;

            case "Driver":
                // Set values of info box
//                nameTextView.setText(rideRequest.getRider().getFirstName() + " " + rideRequest.getRider().getLastName());
//                phoneTextView.setText(rideRequest.getRider().getPhone());
//                emailTextView.setText(rideRequest.getRider().getEmail());

                completeButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        // TODO: Set request status to COMPLETED and move Driver to PAYMENT screen
                    }
                });
                break;
        }

        // Bring up profile when name is clicked
        nameTextView.setOnClickListener(new NameOnClickListener(role));

        return view;
    }
}
