package com.cmput301w20t23.newber.models;

public class Driver extends User {
    private RideRequest currentRequest;
    private Rating rating;

    public Driver(String firstName, String lastName, String username, String phone, String email, String uId) {
        super(firstName, lastName, username, phone, email, uId);
        currentRequest = null;
    }
}
