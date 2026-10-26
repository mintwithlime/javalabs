package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FreightWagonTest {
    private static final double DELTA = 1e-9;

    private FreightWagon wagon;
    private Cargo glass;

    @BeforeEach
    void setUp() {
        wagon = new FreightWagon("F-1001", 25.0, "BelRailCargo", 60.0);
        glass = new Cargo(20.0, true, 150_000, "Glass panels");
        wagon.giveTask("Minsk");
    }

    @Test
    void loadCargoStoresCargoAndAddsWeight() {
        wagon.loadCargo(glass);

        assertSame(glass, wagon.getCargo());
        assertEquals(45.0, wagon.getTotalWeight(), DELTA);
    }

    @Test
    void cargoHeavierThanMaxLoadThrows() {
        Cargo heavy = new Cargo(70.0, false, 1_000, "Coal");

        assertThrows(IllegalArgumentException.class, () -> wagon.loadCargo(heavy));
    }

    @Test
    void wagonReportsCargoFragilityAndValue() {
        wagon.loadCargo(glass);

        assertTrue(wagon.isCargoFragile());
        assertEquals(150_000, wagon.getCargoValue(), DELTA);
    }

    @Test
    void fragilityOfEmptyWagonThrows() {
        assertThrows(IllegalStateException.class, wagon::isCargoFragile);
    }
}