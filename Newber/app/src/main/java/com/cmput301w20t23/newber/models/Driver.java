package com.cmput301w20t23.newber.models;

/**
 * Describes the Driver type of user.
 *
 * @author Jessica D'Cunha, Gaurav Sekhar
 */
public class Driver extends User {
    private RideRequest currentRequest;
    private Rating rating;

    /**
     * Instantiates a new Driver.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @param username  the username
     * @param phone     the phone
     * @param email     the email
     * @param uId       the user id
     */
    public Driver(String firstName, String lastName, String username, String phone, String email, String uId) {
        super(firstName, lastName, username, phone, email, uId);
        currentRequest = null;
    }
}
