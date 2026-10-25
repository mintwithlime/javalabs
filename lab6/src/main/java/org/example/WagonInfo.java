package org.example;

public interface WagonInfo {
    String getRegistrationNumber();
    String getDestination();
    String getOwner();
    WagonStatus getStatus();
    double getTareWeight();
    void setOwner(String owner);
    double getTotalWeight();
}
