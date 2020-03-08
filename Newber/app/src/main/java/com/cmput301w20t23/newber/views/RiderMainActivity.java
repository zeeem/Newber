package com.cmput301w20t23.newber.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.cmput301w20t23.newber.R;
import com.cmput301w20t23.newber.controllers.UserController;
import com.cmput301w20t23.newber.models.DataListener;
import com.cmput301w20t23.newber.models.Driver;
import com.cmput301w20t23.newber.models.RequestStatus;
import com.cmput301w20t23.newber.models.RideRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RiderMainActivity extends AppCompatActivity {
    RideRequest currRequest;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_rider);

        Fragment riderFragment = null;
        TextView statusBanner = findViewById(R.id.main_status_banner);

        if (currRequest == null) {
            // if no current request, use "no current request" fragment
            statusBanner.setText("No Request");
            statusBanner.setBackgroundColor(Color.LTGRAY);
            riderFragment = new RiderRequestNoneFragment();
        }
        else {
            switch (currRequest.getStatus()) {
                case PENDING:
                    statusBanner.setText("Requested");
                    statusBanner.setBackgroundColor(Color.RED);
                    riderFragment = new RiderRequestPendingFragment(currRequest);
                case OFFERED:
                    statusBanner.setText("Offered");
                    statusBanner.setBackgroundColor(Color.rgb(255,165,0)); // orange
                    riderFragment = new RiderRequestOfferedFragment(currRequest);
                case ACCEPTED:
                    statusBanner.setText("Accepted");
                    statusBanner.setBackgroundColor(Color.GREEN);
                case IN_PROGRESS:
                    statusBanner.setText("In Progress");
                    statusBanner.setBackgroundColor(Color.YELLOW);
                case COMPLETED:
                    statusBanner.setText("Completed");
                    statusBanner.setBackgroundColor(Color.CYAN);
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
