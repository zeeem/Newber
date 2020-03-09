package com.cmput301w20t23.newber.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import com.cmput301w20t23.newber.R;

public class NoRequestFragment extends Fragment {
    private String role;

    public NoRequestFragment(String role) {
        this.role = role;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflater of layout for this fragment
        View view = inflater.inflate(R.layout.no_request_fragment, container, false);

        Button createRequestButton = view.findViewById(R.id.create_request_button);

        createRequestButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                switch (role) {
                    case "Rider":
                        Intent intent = new Intent(getActivity(), RiderRequestActivity.class);
                        startActivity(intent);
                        break;
                    case "Driver":
                        break;
                }
            }
        });
        return view;
    }
}