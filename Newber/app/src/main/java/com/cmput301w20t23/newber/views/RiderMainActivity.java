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
import com.cmput301w20t23.newber.models.RideRequest;
import com.cmput301w20t23.newber.models.Rider;
import com.cmput301w20t23.newber.models.User;
import com.google.firebase.auth.FirebaseAuth;

public class RiderMainActivity extends AppCompatActivity {
    RideRequest currRequest;
    User currUserInfo;
    FirebaseAuth mAuth;
    String role = "Rider";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_rider);

        Fragment riderFragment = null;
        TextView statusBanner = findViewById(R.id.main_status_banner);

        // TODO: Get current user information and pass to NoRequestFragment
        // create user object and update role attribute

        if (currRequest == null) {
            // if no current request, use "no current request" fragment
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
                case OFFERED:
                    statusBanner.setText("Offered");
                    statusBanner.setBackgroundColor(Color.rgb(255,165,0)); // orange
                    riderFragment = new RequestOfferedFragment(currRequest, role);
                case ACCEPTED:
                    statusBanner.setText("Accepted");
                    statusBanner.setBackgroundColor(Color.GREEN);
                    riderFragment = new RequestAcceptedFragment(currRequest, role);
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
