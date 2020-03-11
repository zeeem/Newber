package com.cmput301w20t23.newber.models;

/**
 * Describes the Driver type of user.
 *
 * @author Jessica D'Cunha, Gaurav Sekhar
 */
public class Driver extends User {
    private Rating rating;
    private RideRequest currentRequest;

<<<<<<< HEAD
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

    public Driver(String firstName, String lastName, String username, String phone, String email,
                  String uId, String currRequestId, Rating rating) {
        super(firstName, lastName, username, phone, email, uId, currRequestId);
        this.rating = rating;
        this.currentRequest = null;
    }

    public void setRating(Rating rating) { this.rating = rating; }

    public Rating getRating() { return this.rating; }

    public void setCurrentRequest(RideRequest request) { this.currentRequest = request; }

    public RideRequest getCurrentRequest() { return this.currentRequest; }

}
