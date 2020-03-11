package com.cmput301w20t23.newber.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.cmput301w20t23.newber.R;
import com.cmput301w20t23.newber.models.Location;
import com.cmput301w20t23.newber.models.RequestStatus;
import com.cmput301w20t23.newber.models.RideRequest;
import com.google.android.libraries.places.api.model.Place;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * The Android Activity that acts as the main user screen of the app.
 *
 * @author Amy Hou
 */
public class MainActivity extends AppCompatActivity {
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();

    /**
     * The user's current ride request.
     */
    private RideRequest currRequest; // To be updated when querying db

    private String role, currentRequestId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get current userID
        String userId = mAuth.getCurrentUser().getUid();

        System.out.println(userId);

        // Get User object using Firebase users table
        database.getReference("users")
                .child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                role = dataSnapshot.child("role").getValue(String.class);
                currentRequestId = dataSnapshot.child("currentRequestId").getValue(String.class);
                System.out.println("changed");

                // Use User.currRequestId to get RideRequest object from requests table
                if (currentRequestId != null) {
                    System.out.println("currReqId not null");
                    database.getReference("rideRequests")
                            .child(currentRequestId).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                currRequest = dataSnapshot.getValue(RideRequest.class);
                                displayFragment();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else {
                    currRequest = null;
                    displayFragment();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void displayFragment() {
        Fragment riderFragment = null;
        TextView statusBanner = findViewById(R.id.main_status_banner);

        if (currRequest == null) {
            // if current user has no request attached, use "no current request" fragment
            statusBanner.setText("No Request");
            statusBanner.setBackgroundColor(Color.LTGRAY);
            riderFragment = new NoRequestFragment(role);
        }
        else {
            switch (currRequest.getStatus()) {
                case PENDING:
                    statusBanner.setText("Requested");
                    statusBanner.setBackgroundColor(Color.RED);
                    riderFragment = new RequestPendingFragment(currRequest);
                    break;
                case OFFERED:
                    statusBanner.setText("Offered");
                    statusBanner.setBackgroundColor(Color.rgb(255,165,0)); // orange
                    riderFragment = new RequestOfferedFragment(currRequest, role);
                    break;
                case ACCEPTED:
                    statusBanner.setText("Accepted");
                    statusBanner.setBackgroundColor(Color.GREEN);
                    riderFragment = new RequestAcceptedFragment(currRequest, role);
                    break;
                case IN_PROGRESS:
                    statusBanner.setText("In Progress");
                    statusBanner.setBackgroundColor(Color.YELLOW);
                    riderFragment = new RequestInProgressFragment(currRequest, role);
                    break;
                case COMPLETED:
                    statusBanner.setText("Completed");
                    statusBanner.setBackgroundColor(Color.CYAN);
                    riderFragment = new RequestCompletedFragment(currRequest);
            }
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.rider_request_details, riderFragment);
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // options menu contains button going to profile
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                // go to profile activity
                Intent i = new Intent(this, ProfileActivity.class);
                this.startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
