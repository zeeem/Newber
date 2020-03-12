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
import com.cmput301w20t23.newber.models.RideRequest;
import com.cmput301w20t23.newber.models.Location;
import com.cmput301w20t23.newber.models.Rider;
import com.google.firebase.database.FirebaseDatabase;

/**
 * The Android Fragment that is shown when the user has a pending current ride request.
 *
 * @author Amy Hou
 */
public class RequestPendingFragment extends Fragment {

    private RideRequest rideRequest;
    private Rider rider;
    private RideController rideController;
    private UserController userController;

    /**
     * Instantiates a new RequestPendingFragment.
     *
     * @param request the current request
     */
    public RequestPendingFragment(RideRequest request, Rider rider) {
        this.rideRequest = request;
        this.rider = rider;
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
        System.out.println(rideRequest.getStartLocation());
        pickupLocationTextView.setText(rideRequest.getStartLocation().getName());
        dropoffLocationTextView.setText(rideRequest.getEndLocation().getName());
        fareTextView.setText(Double.toString(rideRequest.getCost()));

        cancelRequestButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Remove ride request entry from firebase
                rideController.removeRideRequest(rideRequest);

                // Remove current request ID from firebase user entry
                rider.setCurrentRequestId("");
                userController.updateUserInfo(rider);
            }
        });
        return view;
    }
}