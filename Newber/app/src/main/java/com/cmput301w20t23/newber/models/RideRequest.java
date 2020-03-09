package com.cmput301w20t23.newber.models;

import java.util.UUID;

public class RideRequest {
    private String requestId;
    private String startPlaceId;
    private String endPlaceId;
    private RequestStatus status;
    private String driverUid;
    private String riderUid;
    private double cost;

    public RideRequest(String startPlaceId, String endPlaceId, String riderUid, double cost) {
        this.requestId = UUID.randomUUID().toString();
        this.startPlaceId = startPlaceId;
        this.endPlaceId = endPlaceId;
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

    public String getStartPlaceId() {
        return startPlaceId;
    }

    public void setStartPlaceId(String startPlaceId) {
        this.startPlaceId = startPlaceId;
    }

    public String getEndPlaceId() {
        return endPlaceId;
    }

    public void setEndPlaceId(String endPlaceId) {
        this.endPlaceId = endPlaceId;
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
