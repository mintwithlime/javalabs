package org.example;

public class PassengerWagon extends Wagon implements PassengerCarrier {
    private final double MEAN_HUMAN_MASS = 0.075;
    private final int seatCount;
    private int passengers;

    public PassengerWagon(String registrationNumber, double tareWeight, String owner, int seatCount) {
        super(registrationNumber, tareWeight, owner);

        if (seatCount <= 0) {
            throw new IllegalArgumentException("seat count cannot be negative or 0");
        }

        this.seatCount = seatCount;
    }

    @Override
    public double getTotalWeight() {
        return getTareWeight() + (double) passengers * MEAN_HUMAN_MASS;
    }

    @Override
    public void board(int passengers) {
        requireStatus(WagonStatus.LOADING);

        if (passengers > seatCount) {
            throw new IllegalArgumentException("cannot board more people than seats");
        }

        if (passengers < 0) {
            throw new IllegalArgumentException("passengers count cannot be negative");
        }

        this.passengers = passengers;
        finishLoading();
    }

    @Override
    public void disembark() {
        requireStatus(WagonStatus.UNLOADING);
        this.passengers = 0;
        finishUnloading();
    }

    @Override
    public int getPassengerCount() {
        return passengers;
    }

    @Override
    public int getSeatCount() {
        return seatCount;
    }
}
