package org.example;

public interface PassengerCarrier {
    void board(int passengers);
    void disembark();
    int getPassengerCount();
    int getSeatCount();
}
