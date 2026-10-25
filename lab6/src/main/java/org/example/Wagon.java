package org.example;

public abstract class Wagon implements WagonInfo, Transportable, Maintainable {
    private static final int MAX_TRIPS = 3;
    private final String registrationNumber;
    private final double weight;
    private String owner;
    private WagonStatus status;
    private String destination;
    private int count;

    protected Wagon(String registrationNumber, double weight) {
        if (registrationNumber == null || registrationNumber.isBlank()) {
            throw new IllegalArgumentException("registrationNumber cannot be null or blank");
        }

        if (weight <= 0) {
            throw new IllegalArgumentException("weight cannot be negative or 0");
        }

        this.registrationNumber = registrationNumber;
        this.weight = weight;
        this.status = WagonStatus.READY_TO_WORK;
        this.destination = null;
        this.count = 0;
    }

    protected void requireStatus(WagonStatus expected) {
        if (status != expected) {
            throw new IllegalStateException(
                    "Expected status " + expected + ", but was " + status);
        }
    }

    @Override
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    @Override
    public String getDestination() {
        return destination;
    }

    @Override
    public String getOwner() {
        return owner;
    }

    @Override
    public WagonStatus getStatus() {
        return status;
    }

    @Override
    public double getTareWeight() {
        return weight;
    }

    @Override
    public void setOwner(String owner) {
        if (owner == null || owner.isBlank()) {
            throw new IllegalArgumentException("owner cannot be null or blank");
        }

        this.owner = owner;
    }

    @Override
    public void giveTask(String destination) {
        if (destination == null || destination.isBlank()) {
            throw new IllegalArgumentException("destination cannot be null or blank");
        }
        requireStatus(WagonStatus.READY_TO_WORK);

        this.destination = destination;
        this.status = WagonStatus.LOADING;
    }

    @Override
    public void depart() {
        requireStatus(WagonStatus.READY_TO_DEPART);

        this.status = WagonStatus.IN_TRANSIT;
    }

    @Override
    public void arrive() {
        requireStatus(WagonStatus.IN_TRANSIT);

        this.status = WagonStatus.UNLOADING;
        count++;
    }

    @Override
    public void service() {
        requireStatus(WagonStatus.READY_TO_WORK);

        count = 0;
    }

    @Override
    public void startRepair() {
        requireStatus(WagonStatus.NEEDS_REPAIR);

        this.status = WagonStatus.UNDER_REPAIR;
    }

    @Override
    public void finishRepair() {
        requireStatus(WagonStatus.UNDER_REPAIR);

        this.status = WagonStatus.READY_TO_WORK;
        count = 0;
    }

    protected void finishLoading() {
        requireStatus(WagonStatus.LOADING);
        this.status = WagonStatus.READY_TO_DEPART;
    }

    protected void finishUnloading() {
        requireStatus(WagonStatus.UNLOADING);

        if (count == MAX_TRIPS) {
            this.status = WagonStatus.NEEDS_REPAIR;
        } else {
            this.status = WagonStatus.READY_TO_DEPART;
        }
    }
}
