package com.cmput301w20t23.newber.models;

import androidx.annotation.NonNull;

import com.google.android.libraries.places.api.model.Place;

import java.io.Serializable;
import java.util.UUID;

/**
 * Describes a request for a ride.
 *
 * @author Jessica D'Cunha, Gaurav Sekhar
 */
public class RideRequest implements Serializable {
    private String requestId;
    private Location startLocation;
    private Location endLocation;
    private RequestStatus status;
    private String driverUid;
    private String riderUid;
    private double cost;

    public RideRequest() {

    }

    /**
     * Instantiates a new Ride request.
     *
     * @param requestId     the unique request id
     * @param startPlace    the starting location
     * @param endPlace      the end location
     * @param riderUid      the rider involved
     * @param cost          the cost of the ride
     */
    public RideRequest(String requestId, Place startPlace, Place endPlace, String riderUid, double cost) {
        this.requestId = requestId;
        this.startLocation = new Location(startPlace);
        this.endLocation = new Location(endPlace);
        this.status = RequestStatus.PENDING;
        this.riderUid = riderUid;
        this.driverUid = "";
        this.cost = cost;
    }

    public RideRequest(String requestId, Location startLocation, Location endLocation, RequestStatus status, String riderUid, String driverUid, double cost) {
        this.requestId = requestId;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.status = status;
        this.riderUid = riderUid;
        this.driverUid = driverUid;
        this.cost = cost;
    }

    /**
     * Gets request id.
     *
     * @return the request id
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Sets request id.
     *
     * @param requestId the request id
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    /**
     * Gets start.
     *
     * @return the starting location
     */
    public Location getStartLocation() {
        return startLocation;
    }

    /**
     * Sets start.
     *
     * @param startLocation the starting location
     */
    public void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
    }

    /**
     * Gets end.
     *
     * @return the ending location
     */
    public Location getEndLocation() {
        return endLocation;
    }

    /**
     * Sets end.
     *
     * @param endLocation the ending location
     */
    public void setEndLocation(Location endLocation) {
        this.endLocation = endLocation;
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
    public String getDriverUid() {
        return driverUid;
    }

    /**
     * Sets driver.
     *
     * @param driverUid the driver involved
     */
    public void setDriverUid(String driverUid) {
        this.driverUid = driverUid;
    }

    /**
     * Gets rider.
     *
     * @return the rider involved
     */
    public String getRiderUid() {
        return riderUid;
    }

    /**
     * Sets rider.
     *
     * @param riderUid the rider involved
     */
    public void setRiderUid(String riderUid) {
        this.riderUid = riderUid;
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

    @NonNull
    @Override
    public String toString() {
        return "Pick up at: " + startLocation.getName();
    }
}