package com.cmput301w20t23.newber.controllers;

import android.view.View;

import com.cmput301w20t23.newber.models.User;

/**
 * This class listens for a user's name to be clicked and brings up the user profile
 *
 * @author Amy Hou
 */
public class NameOnClickListener implements View.OnClickListener {
    /**
     * The Role.
     */
    private String role;
    private User user;

    /**
     * Instantiates a new NameOnClickListener.
     *
     * @param role      the user's role
     * @param user      the user whose profile we will show
     */
    public NameOnClickListener(String role, User user) {
        this.role = role;
        this.user = user;
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
