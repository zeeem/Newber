package com.cmput301w20t23.newber.models;

import android.location.Location;

import java.util.UUID;

public class RideRequest {
    private UUID requestId;
    private Location start;
    private Location end;
    private RequestStatus status;
    private Driver driver;
    private Rider rider;
    private double cost;

    public RideRequest(UUID requestId, Location start, Location end, RequestStatus status, Driver driver, Rider rider, double cost) {
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

    public Location getStart() {
        return start;
    }

    public void setStart(Location start) {
        this.start = start;
    }

    public Location getEnd() {
        return end;
    }

    public void setEnd(Location end) {
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
