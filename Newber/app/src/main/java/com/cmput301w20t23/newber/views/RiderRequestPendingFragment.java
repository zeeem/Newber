package com.cmput301w20t23.newber.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import com.cmput301w20t23.newber.R;
import com.cmput301w20t23.newber.models.RideRequest;

public class RiderRequestPendingFragment extends Fragment {

    private RideRequest rideRequest;

    public RiderRequestPendingFragment(RideRequest request) {
        rideRequest = request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflater of layout for this fragment
        View view = inflater.inflate(R.layout.rider_pending_fragment, container, false);

        Button cancelRequestButton = view.findViewById(R.id.rider_pending_request_button);

        cancelRequestButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO: remove request from firebase
            }
        });
        return view;
    }
}