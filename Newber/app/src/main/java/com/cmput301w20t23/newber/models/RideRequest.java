package com.cmput301w20t23.newber.models;

import com.google.android.libraries.places.api.model.Place;

import java.util.UUID;

public class RideRequest {
    private String requestId;
    private Place startPlace;
    private Place endPlace;
    private RequestStatus status;
    private String driverUid;
    private String riderUid;
    private double cost;

    public RideRequest(Place startPlace, Place endPlace, String riderUid, double cost) {
        this.requestId = UUID.randomUUID().toString();
        this.startPlace = startPlace;
        this.endPlace = endPlace;
        this.status = RequestStatus.PENDING;
        this.riderUid = riderUid;
        this.cost = cost;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Place getStartPlace() {
        return startPlace;
    }

    public void setStartPlace(Place startPlace) {
        this.startPlace = startPlace;
    }

    public Place getEndPlace() {
        return endPlace;
    }

    public void setEndPlace(Place endPlace) {
        this.endPlace = endPlace;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public String getDriverUid() {
        return driverUid;
    }

    public void setDriverUid(String driver) {
        this.driverUid = driver;
    }

    public String getRiderUid() {
        return riderUid;
    }

    public void setRiderUid(String riderUid) {
        this.riderUid = riderUid;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
