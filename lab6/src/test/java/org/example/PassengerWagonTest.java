package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PassengerWagonTest {
    private PassengerWagon wagon;

    @BeforeEach
    void setUp() {
        wagon = new PassengerWagon("P-2002", 55.0, "BelRail", 54);
        wagon.giveTask("Grodno");
    }

    @Test
    void boardSetsPassengerCount() {
        wagon.board(40);

        assertEquals(40, wagon.getPassengerCount());
    }

    @Test
    void boardingMoreThanSeatsThrows() {
        assertThrows(IllegalArgumentException.class, () -> wagon.board(55));
    }

    @Test
    void disembarkEmptiesWagon() {
        wagon.board(40);
        wagon.depart();
        wagon.arrive();

        wagon.disembark();

        assertEquals(0, wagon.getPassengerCount());
    }
}