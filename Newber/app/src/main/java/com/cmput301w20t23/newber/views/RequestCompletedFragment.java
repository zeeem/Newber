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
import com.cmput301w20t23.newber.controllers.RideController;
import com.cmput301w20t23.newber.controllers.UserController;
import com.cmput301w20t23.newber.models.Driver;
import com.cmput301w20t23.newber.models.RideRequest;
import com.cmput301w20t23.newber.models.Rider;

/**
 * The Android Fragment that is shown when the user has a completed current ride request.
 *
 * @author Amy Hou
 */
public class RequestCompletedFragment extends Fragment {

    private RideRequest rideRequest;

    private RideController rideController;
    private UserController userController = new UserController(this.getContext());

    /**
     * Instantiates a new RequestCompletedFragment.
     *
     * @param request the current request
     */
    public RequestCompletedFragment(RideRequest request) {
        this.rideRequest = request;
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
        pickupLocationTextView.setText(rideRequest.getStartLocation().getName());
        dropoffLocationTextView.setText(rideRequest.getEndLocation().getName());
        fareTextView.setText(Double.toString(rideRequest.getCost()));

        // Set driver box information
        nameTextView.setText(rideRequest.getDriver().getUsername());
        phoneTextView.setText(rideRequest.getDriver().getPhone());
        emailTextView.setText(rideRequest.getDriver().getEmail());

        completeRequestButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO: remove request from firebase user and the requests table
                Driver tempDriver = rideRequest.getDriver();
                Rider tempRider = rideRequest.getRider();
                tempDriver.setCurrentRequestId("");
                tempRider.setCurrentRequestId("");

                // Update currentRequestId fields of driver
                userController.updateUserCurrentRequestId(tempDriver);
                // Update currentRequestId fields of rider
                userController.updateUserCurrentRequestId(tempRider);
            }
        });
        return view;
    }
}