package org.example;

public interface WagonInfo {
    String getRegistrationNumber();
    String getDestination();
    String getOwner();
    WagonStatus getStatus();
    double getTareWeight(); // in tons
    void setOwner(String owner);
    double getTotalWeight();
}
