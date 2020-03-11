package com.cmput301w20t23.newber.controllers;

import android.view.View;

/**
 * This class listens for a user's name to be clicked and brings up the user profile
 *
 * @author Amy Hou
 */
public class NameOnClickListener implements View.OnClickListener {
    /**
     * The Role.
     */
    String role;

    /**
     * Instantiates a new NameOnClickListener.
     *
     * @param role the user's role
     */
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
