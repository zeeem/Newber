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

public class RequestOfferedFragment extends Fragment {

    private RideRequest rideRequest;
    private String role;

    public RequestOfferedFragment(RideRequest request, String role) {
        this.rideRequest = request;
        this.role = role;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // View for this fragment
        View view = inflater.inflate(R.layout.pending_fragment, container, false);

        TextView name = view.findViewById(R.id.rider_main_driver_name);
        TextView phone = view.findViewById(R.id.rider_main_driver_phone);
        TextView email = view.findViewById(R.id.rider_main_driver_email);

        Button acceptOfferButton = view.findViewById(R.id.rider_accept_offer_button);
        Button declineOfferButton = view.findViewById(R.id.rider_decline_offer_button);

        // Set values of info box
        name.setText(rideRequest.getDriver().getFirstName() + rideRequest.getDriver().getLastName());
        phone.setText(rideRequest.getDriver().getPhone());
        email.setText(rideRequest.getDriver().getEmail());

        // Show decline/accept offer buttons only for Riders
        if (role.matches("Driver"))
        {
            acceptOfferButton.setVisibility(View.INVISIBLE);
            declineOfferButton.setVisibility(View.INVISIBLE);
        }

        // Bring up profile when name is clicked
        name.setOnClickListener(new NameOnClickListener(role));

        acceptOfferButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO: Leave driver attached to request on firebase and set request status to ACCEPTED
            }
        });

        declineOfferButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO: Request status returns to PENDING and remove driver from request on Firebase
            }
        });

        return view;
    }
}
