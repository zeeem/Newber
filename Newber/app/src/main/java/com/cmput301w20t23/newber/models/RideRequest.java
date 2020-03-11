package com.cmput301w20t23.newber.models;

import com.google.android.libraries.places.api.model.Place;

import java.util.UUID;

/**
 * Describes a request for a ride.
 *
 * @author Jessica D'Cunha, Gaurav Sekhar
 */
public class RideRequest {
    private String requestId;
    private Place startPlace;
    private Place endPlace;
    private RequestStatus status;
    private String driverUid;
    private String riderUid;
    private double cost;

    /**
     * Instantiates a new Ride request.
     *
     * @param startPlace    the starting location
     * @param endPlace      the end location
     * @param riderUid      the rider involved
     * @param cost          the cost of the ride
     */
    public RideRequest(Place startPlace, Place endPlace, String riderUid, double cost) {
        this.requestId = UUID.randomUUID().toString();
        this.startPlace = startPlace;
        this.endPlace = endPlace;
        this.status = RequestStatus.PENDING;
        this.riderUid = riderUid;
        this.driverUid = "";
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
    public Place getStartPlace() {
        return startPlace;
    }

    /**
     * Sets start.
     *
     * @param startPlace the starting location
     */
    public void setStartPlace(Place startPlace) {
        this.startPlace = startPlace;
    }

    /**
     * Gets end.
     *
     * @return the ending location
     */
    public Place getEndPlace() {
        return endPlace;
    }

    /**
     * Sets end.
     *
     * @param endPlace the ending location
     */
    public void setEndPlace(Place endPlace) {
        this.endPlace = endPlace;
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
}