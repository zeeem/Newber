package com.cmput301w20t23.newber.models;

import android.location.Location;

import com.google.android.libraries.places.api.model.Place;

import java.util.UUID;

public class RideRequest {
    private UUID requestId;
    private Place start;
    private Place end;
    private RequestStatus status;
    private Driver driver;
    private Rider rider;
    private double cost;

    public RideRequest(UUID requestId, Place start, Place end, RequestStatus status, Driver driver, Rider rider, double cost) {
        this.requestId = requestId;
        this.start = start;
        this.end = end;
        this.status = status;
        this.driver = driver;
        this.rider = rider;
        this.cost = cost;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }

    public Place getStart() {
        return start;
    }

    public void setStart(Place start) {
        this.start = start;
    }

    public Place getEnd() {
        return end;
    }

    public void setEnd(Place end) {
        this.end = end;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Rider getRider() {
        return rider;
    }

    public void setRider(Rider rider) {
        this.rider = rider;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
