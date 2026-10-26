package org.example;

public class FreightWagon extends Wagon implements CargoCarrier {
    private Cargo cargo;
    private final double maxLoad;

    public FreightWagon(String registrationNumber, double tareWeight, String owner, double MaxLoad) {
        super(registrationNumber, tareWeight, owner);
        if (MaxLoad <= 0) {
            throw new IllegalArgumentException("max load cannot be negative or 0");
        }
        this.maxLoad = MaxLoad;
    }

    @Override
    public double getTotalWeight() {
        return getTareWeight() + (cargo == null ? 0 : cargo.getWeight());
    }

    @Override
    public void loadCargo(Cargo cargo) {
        requireStatus(WagonStatus.LOADING);
        if (cargo == null) {
            throw new IllegalArgumentException("cargo cannot be null");
        }
        if (cargo.getWeight() > maxLoad) {
            throw new IllegalArgumentException("total weight cannot be bigger than max load");
        }
        this.cargo = cargo;
        finishLoading();
    }

    @Override
    public void unloadCargo() {
        requireStatus(WagonStatus.UNLOADING);
        cargo = null;
        finishUnloading();
    }

    @Override
    public boolean isCargoFragile() {
        if (cargo == null) {
            throw new IllegalStateException("Wagon " + getRegistrationNumber() + "has no cargo");
        }
        return cargo.isFragile();
    }

    @Override
    public double getCargoValue() {
        if (cargo == null) {
            throw new IllegalStateException("there cannot be value when cargo is null");
        }
        return cargo.getPrice();
    }

    @Override
    public Cargo getCargo() {
        return cargo;
    }

    @Override
    public double getMaxLoad() {
        return maxLoad;
    }
}
