package service;

import model.Clarity;
import model.Necklace;
import model.PreciousStone;
import model.SemiPreciousStone;
import model.Stone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NecklaceServiceTest {
    private static final double DELTA = 1e-9;

    private NecklaceService service;
    private List<Stone> stones;
    private Necklace fullNecklace;

    private static List<String> names(List<Stone> stones) {
        return stones.stream()
                .map(Stone::getName)
                .toList();
    }

    @BeforeEach
    void setUp() {
        service = new NecklaceService();

        // total prices: Diamond 7500, Ruby 6400, Amethyst 256, Garnet 216
        stones = List.of(
                new PreciousStone("Diamond", 1.5, 5000,
                        5, Clarity.VVS1, true),
                new PreciousStone("Ruby", 2.0, 3200,
                        4, Clarity.SI1, false),
                new SemiPreciousStone("Amethyst", 3.2, 80,
                        3, 7.0, "Brazil"),
                new SemiPreciousStone("Garnet", 1.8, 120,
                        2, 7.5, "Russia")
        );
        fullNecklace = new Necklace(new ArrayList<>(stones));
    }

    // ---------- selectStones ----------

    @Test
    void selectStonesSkipsStoneThatExceedsBudget() {
        Necklace necklace = service.selectStones(stones, 8000);

        assertEquals(List.of("Diamond", "Amethyst", "Garnet"), names(necklace.getStones()));
    }

    @Test
    void selectStonesTakesStonesThatFitBudgetExactly() {
        Necklace necklace = service.selectStones(stones, 7972);

        assertEquals(List.of("Diamond", "Amethyst", "Garnet"), names(necklace.getStones()));
    }

    @Test
    void selectStonesReturnsEmptyNecklaceWhenBudgetTooSmall() {
        Necklace necklace = service.selectStones(stones, 100);

        assertTrue(necklace.getStones().isEmpty());
    }

    @Test
    void selectStonesTakesAllStonesWhenBudgetIsLarge() {
        Necklace necklace = service.selectStones(stones, 100_000);

        assertEquals(4, necklace.getStones().size());
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, -100})
    void selectStonesThrowsOnNonPositiveBudget(double budget) {
        assertThrows(IllegalArgumentException.class,
                () -> service.selectStones(stones, budget));
    }

    // ---------- calculateTotalWeight / calculateTotalCost ----------

    @Test
    void calculateTotalWeightSumsWeights() {
        Necklace necklace = service.selectStones(stones, 8000);

        assertEquals(6.5, service.calculateTotalWeight(necklace), DELTA);
    }

    @Test
    void calculateTotalCostSumsTotalPrices() {
        Necklace necklace = service.selectStones(stones, 8000);

        assertEquals(7972.0, service.calculateTotalCost(necklace), DELTA);
    }

    @Test
    void calculationsReturnZeroForEmptyNecklace() {
        Necklace empty = new Necklace(new ArrayList<>());

        assertEquals(0.0, service.calculateTotalWeight(empty), DELTA);
        assertEquals(0.0, service.calculateTotalCost(empty), DELTA);
    }

    // ---------- sortByValue ----------

    @Test
    void sortByValueOrdersFromMostToLeastValuable() {
        List<Stone> sorted = service.sortByValue(fullNecklace);

        assertEquals(List.of("Diamond", "Ruby", "Amethyst", "Garnet"), names(sorted));
    }

    @Test
    void sortByValueDoesNotModifyNecklace() {
        Necklace necklace = new Necklace(new ArrayList<>(List.of(
                stones.get(3), stones.get(2), stones.get(0)   // Garnet, Amethyst, Diamond
        )));
        List<String> before = names(necklace.getStones());

        service.sortByValue(necklace);

        assertEquals(before, names(necklace.getStones()));
    }

    // ---------- findByTransparency ----------

    @Test
    void findByTransparencyReturnsStonesWithinRange() {
        List<Stone> found = service.findByTransparency(fullNecklace, 3, 5);

        assertEquals(List.of("Diamond", "Ruby", "Amethyst"), names(found));
    }

    @Test
    void findByTransparencyIncludesBoundaries() {
        List<Stone> found = service.findByTransparency(fullNecklace, 2, 2);

        assertEquals(List.of("Garnet"), names(found));
    }

    @Test
    void findByTransparencyReturnsEmptyListWhenNothingMatches() {
        List<Stone> found = service.findByTransparency(fullNecklace, 1, 1);

        assertTrue(found.isEmpty());
    }

    @Test
    void findByTransparencyThrowsWhenMinGreaterThanMax() {
        assertThrows(IllegalArgumentException.class,
                () -> service.findByTransparency(fullNecklace, 5, 2));
    }
}