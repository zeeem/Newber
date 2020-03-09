package com.cmput301w20t23.newber.controllers;

import android.view.View;

public class NameOnClickListener implements View.OnClickListener {
    String role;
    public NameOnClickListener(String role) {
        this.role = role;
    }
    @Override
    public void onClick(View view) {
        // TODO: Show profile in pop up dialog
        switch (role) {
            case "Rider":
                // Get rider info from request
                break;
            case "Driver":
                // Get driver info from request
                break;
        }
    }
}
