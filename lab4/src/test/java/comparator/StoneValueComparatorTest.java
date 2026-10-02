package comparator;

import model.Clarity;
import model.PreciousStone;
import model.Stone;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StoneValueComparatorTest {
    private final Stone first = new PreciousStone("first", 1.0, 4.0,
            3, Clarity.SI1, true);
    private final Stone second = new PreciousStone("second", 2.0, 2.5,
            3, Clarity.SI1, true);
    private final Stone third = new PreciousStone("third", 1.0, 5.0,
            3, Clarity.SI1, true);
    private final StoneValueComparator comparator = new StoneValueComparator();


    @Test
    void compareReturnsNegativeWhenFirstIsCheaper() {
        int result = comparator.compare(first, second);
        assertTrue(result < 0);
    }

    @Test
    void compareReturnsPositiveWhenFirstIsMoreExpensive() {
        int result = comparator.compare(second, first);
        assertTrue(result > 0);
    }

    @Test
    void compareReturnsZeroWhenEqualValue() {
        int result = comparator.compare(second, third);
        assertEquals(0, result);
    }
}
