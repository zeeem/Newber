package com.cmput301w20t23.newber.models;

import android.location.Location;

import java.util.UUID;

/**
 * Describes a request for a ride.
 *
 * @author Jessica D'Cunha, Gaurav Sekhar
 */
public class RideRequest {
    private UUID requestId;
    private Location start;
    private Location end;
    private RequestStatus status;
    private Driver driver;
    private Rider rider;
    private double cost;

    /**
     * Instantiates a new Ride request.
     *
     * @param requestId the request id
     * @param start     the starting location
     * @param end       the end location
     * @param status    the status of the request
     * @param driver    the driver involved
     * @param rider     the rider involved
     * @param cost      the cost of the ride
     */
    public RideRequest(UUID requestId, Location start, Location end, RequestStatus status, Driver driver, Rider rider, double cost) {
        this.requestId = requestId;
        this.start = start;
        this.end = end;
        this.status = status;
        this.driver = driver;
        this.rider = rider;
        this.cost = cost;
    }

    /**
     * Gets request id.
     *
     * @return the request id
     */
    public UUID getRequestId() {
        return requestId;
    }

    /**
     * Sets request id.
     *
     * @param requestId the request id
     */
    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }

    /**
     * Gets start.
     *
     * @return the starting location
     */
    public Location getStart() {
        return start;
    }

    /**
     * Sets start.
     *
     * @param start the starting location
     */
    public void setStart(Location start) {
        this.start = start;
    }

    /**
     * Gets end.
     *
     * @return the ending location
     */
    public Location getEnd() {
        return end;
    }

    /**
     * Sets end.
     *
     * @param end the ending location
     */
    public void setEnd(Location end) {
        this.end = end;
    }

    /**
     * Gets status.
     *
     * @return the request status
     */
    public RequestStatus getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the request status
     */
    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    /**
     * Gets driver.
     *
     * @return the driver involved
     */
    public Driver getDriver() {
        return driver;
    }

    /**
     * Sets driver.
     *
     * @param driver the driver involved
     */
    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    /**
     * Gets rider.
     *
     * @return the rider involved
     */
    public Rider getRider() {
        return rider;
    }

    /**
     * Sets rider.
     *
     * @param rider the rider involved
     */
    public void setRider(Rider rider) {
        this.rider = rider;
    }

    /**
     * Gets cost.
     *
     * @return the cost of the ride
     */
    public double getCost() {
        return cost;
    }

    /**
     * Sets cost.
     *
     * @param cost the cost of the ride
     */
    public void setCost(double cost) {
        this.cost = cost;
    }
}
