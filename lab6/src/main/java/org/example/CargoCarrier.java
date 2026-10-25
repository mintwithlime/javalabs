package org.example;

public interface CargoCarrier {
    void loadCargo(Cargo cargo);
    void unloadCargo();
    boolean isCargoFragile();
    double getCargoValue();
    Cargo getCargo();
    double getMaxLoad();
}
