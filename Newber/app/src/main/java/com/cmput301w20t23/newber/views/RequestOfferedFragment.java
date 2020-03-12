package com.cmput301w20t23.newber.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cmput301w20t23.newber.R;
import com.cmput301w20t23.newber.controllers.NameOnClickListener;
import com.cmput301w20t23.newber.controllers.RideController;
import com.cmput301w20t23.newber.models.Driver;
import com.cmput301w20t23.newber.models.RequestStatus;
import com.cmput301w20t23.newber.models.RideRequest;
import com.cmput301w20t23.newber.models.Rider;
import com.cmput301w20t23.newber.models.User;

import androidx.fragment.app.Fragment;

/**
 * The Android Fragment that is shown when the user has an offered current ride request.
 *
 * @author Amy Hou
 */
public class RequestOfferedFragment extends Fragment {

    private RideRequest rideRequest;
    private String role;
    private Rider rider;
    private Driver driver;

    /**
     * Instantiate RideRequest controller
     */
    private RideController rideController = new RideController();

    /**
     * Instantiates a new RequestOfferedFragment.
     *
     * @param request the current request
     * @param role    the user's role
     * @param rider   the rider attached to current request
     * @param driver  the driver attached to the current request
     */
    public RequestOfferedFragment(RideRequest request, String role, Rider rider, Driver driver) {
        this.rideRequest = request;
        this.role = role;
        this.rider = rider;
        this.driver = driver;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // View for this fragment
        View view = inflater.inflate(R.layout.pending_fragment, container, false);

        // Get view elements
        TextView pickupLocationTextView = view.findViewById(R.id.pickup_location);
        TextView dropoffLocationTextView = view.findViewById(R.id.dropoff_location);
        TextView fareTextView = view.findViewById(R.id.ride_fare);
        TextView nameTextView = view.findViewById(R.id.rider_main_driver_name);
        TextView phoneTextView = view.findViewById(R.id.rider_main_driver_phone);
        TextView emailTextView = view.findViewById(R.id.rider_main_driver_email);
        Button acceptOfferButton = view.findViewById(R.id.rider_accept_offer_button);
        Button declineOfferButton = view.findViewById(R.id.rider_decline_offer_button);

        // Set view elements
        pickupLocationTextView.setText(rideRequest.getStartLocation().getName());
        dropoffLocationTextView.setText(rideRequest.getEndLocation().getName());
        fareTextView.setText(Double.toString(rideRequest.getCost()));

        switch (role) {
            case "Rider":
                // Set values of info box
                nameTextView.setText(driver.getUsername());
                phoneTextView.setText(driver.getPhone());
                emailTextView.setText(driver.getEmail());

                // Rider can click Accept or Decline to the driver's offer
                acceptOfferButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        // TODO: Leave driver attached to request on firebase and set request status to ACCEPTED
                        rideRequest.setStatus(RequestStatus.ACCEPTED);
//                        rideController.updateRideRequest(rideRequest);
                    }
                });

                declineOfferButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        // TODO: Request status returns to PENDING and remove driver from request on Firebase
                        rideRequest.setStatus(RequestStatus.PENDING);
                        rideRequest.setDriverUid("");
//                        rideController.updateRideRequest(rideRequest);
                    }
                });

                // Bring up profile when name is clicked
                nameTextView.setOnClickListener(new NameOnClickListener(role, driver));
                break;

            case "Driver":
                // Set values of info box
                nameTextView.setText(rider.getUsername());
                phoneTextView.setText(rider.getPhone());
                emailTextView.setText(rider.getEmail());

                // Show decline/accept offer buttons only for Riders
                acceptOfferButton.setVisibility(View.INVISIBLE);
                declineOfferButton.setVisibility(View.INVISIBLE);

                // Bring up profile when name is clicked
                nameTextView.setOnClickListener(new NameOnClickListener(role, rider));
                break;
        }

        return view;
    }
}
