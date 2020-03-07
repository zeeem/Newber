package com.cmput301w20t23.newber.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.cmput301w20t23.newber.R;
import com.cmput301w20t23.newber.models.RideRequest;

public class MainActivity extends AppCompatActivity {

    RideRequest currRequest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment riderFragment = null;

        if (currRequest == null) {
            // if no current request, use "no current request" fragment
            riderFragment = new RiderRequestNoneFragment();
        }
        else {
            switch (currRequest.getStatus()) {
                case PENDING:
                    riderFragment = new RiderRequestPendingFragment();
                case OFFERED:
                case ACCEPTED:
                case IN_PROGRESS:
                case COMPLETED:
                default:
                    riderFragment = new RiderRequestNoneFragment();
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
