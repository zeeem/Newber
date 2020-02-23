package com.cmput301w20t23.newber.models;

public class Rider extends User {
    private RideRequest currentRequest;

    public Rider(String firstName, String lastName, String username, String phone, String email, String uId) {
        super(firstName, lastName, username, phone, email, uId);
        currentRequest = null;
    }
}
