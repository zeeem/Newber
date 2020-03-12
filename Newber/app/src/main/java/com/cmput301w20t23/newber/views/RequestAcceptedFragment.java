package com.cmput301w20t23.newber.views;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cmput301w20t23.newber.R;
import com.cmput301w20t23.newber.controllers.NameOnClickListener;
import com.cmput301w20t23.newber.controllers.RideController;
import com.cmput301w20t23.newber.controllers.UserController;
import com.cmput301w20t23.newber.models.Driver;
import com.cmput301w20t23.newber.models.RequestStatus;
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
     * Instantiate User and RideRequest controllers
     */
    private RideController rideController = new RideController();
    private UserController userController = new UserController(this.getContext());

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
                        rideRequest.setDriverUid("");
                        rideRequest.setStatus(RequestStatus.PENDING);
                        rideController.updateRideRequest(rideRequest);
                    }
                });

                ImageButton callButton = view.findViewById(R.id.call_button);
                ImageButton emailButton = view.findViewById(R.id.email_button);
                callButton.setVisibility(View.VISIBLE);
                emailButton.setVisibility(View.VISIBLE);

                callButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goToCallScreen();
                    }
                });

                emailButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goToMailScreen();
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
                        rideRequest.setStatus(RequestStatus.IN_PROGRESS);
                        rideController.updateRideRequest(rideRequest);
                    }
                });

                // Bring up profile when name is clicked
                nameTextView.setOnClickListener(new NameOnClickListener(role, rider));
                break;
        }

        return view;
    }

    /**
     * Opens Android call screen and populates it with the driver's phone number when the
     * appropriate button is clicked.
     */
    public void goToCallScreen() {
        // TODO: replace dummy phone with driver's phone
        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "7801234567"));
        this.startActivity(callIntent);
    }

    /**
     * Opens Android mail screen and populates it with the driver's email address when the
     * appropriate button is clicked.
     */
    public void goToMailScreen() {
        // TODO: replace dummy email with driver's email
        Intent mailIntent = new Intent(Intent.ACTION_SEND);
        mailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"driver@example.com"});
        mailIntent.setType("message/rfc822");
        this.startActivity(Intent.createChooser(mailIntent,
                "Send email using: "));
    }
}
