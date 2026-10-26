package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WagonTest {
    private FreightWagon wagon;

    @BeforeEach
    void setUp() {
        wagon = new FreightWagon("F-1001", 25.0, "BelRailCargo", 60.0);
    }

    @Test
    void newWagonIsReadyToWork() {
        assertEquals(WagonStatus.READY_TO_WORK, wagon.getStatus());
    }

    @Test
    void fullTripGoesThroughAllStatuses() {
        wagon.giveTask("Minsk");
        assertEquals(WagonStatus.LOADING, wagon.getStatus());

        wagon.loadCargo(new Cargo(20, false, 1_000, "Coal"));
        assertEquals(WagonStatus.READY_TO_DEPART, wagon.getStatus());

        wagon.depart();
        assertEquals(WagonStatus.IN_TRANSIT, wagon.getStatus());

        wagon.arrive();
        assertEquals(WagonStatus.UNLOADING, wagon.getStatus());

        wagon.unloadCargo();
        assertEquals(WagonStatus.READY_TO_WORK, wagon.getStatus());
    }

    @Test
    void departWithoutLoadingThrows() {
        assertThrows(IllegalStateException.class, wagon::depart);
    }

    @Test
    void wagonNeedsRepairAfterMaxTrips() {
        completeTrip();
        completeTrip();
        completeTrip();

        assertEquals(WagonStatus.NEEDS_REPAIR, wagon.getStatus());
    }

    @Test
    void repairReturnsWagonToWork() {
        completeTrip();
        completeTrip();
        completeTrip();

        wagon.startRepair();
        wagon.finishRepair();

        assertEquals(WagonStatus.READY_TO_WORK, wagon.getStatus());
    }

    @Test
    void servicePreventsBreakdown() {
        completeTrip();
        completeTrip();
        wagon.service();
        completeTrip();

        assertEquals(WagonStatus.READY_TO_WORK, wagon.getStatus());
    }

    private void completeTrip() {
        wagon.giveTask("Minsk");
        wagon.loadCargo(new Cargo(20, false, 1_000, "Coal"));
        wagon.depart();
        wagon.arrive();
        wagon.unloadCargo();
    }
}