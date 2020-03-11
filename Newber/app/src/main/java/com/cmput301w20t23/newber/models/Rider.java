package com.cmput301w20t23.newber.models;

/**
 * Describes the Rider type of user.
 *
 * @author Jessica D'Cunha, Gaurav Sekhar
 */
public class Rider extends User {

    RideRequest currentRequest;

    /**
     * Instantiates a new Rider.
     *
     * @param firstName     the first name
     * @param lastName      the last name
     * @param username      the username
     * @param phone         the phone
     * @param email         the email
     * @param uId           the user id
     * @param currRequestId the current request of driver
     */

    public Rider(String firstName, String lastName, String username, String phone, String email,
                 String uId, String currRequestId) {
        super(firstName, lastName, username, phone, email, uId, currRequestId);
        this.currentRequest = null;
    }

    public void setCurrentRequest(RideRequest request) { this.currentRequest = request; }

    public RideRequest getCurrentRequest() { return this.currentRequest; }
}
