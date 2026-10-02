package factory;

import exception.InvalidStoneDataException;
import model.SemiPreciousStone;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class SemiPreciousStoneFactoryTest {
    private static final double DELTA = 1e-9;

    private final SemiPreciousStoneFactory factory = new SemiPreciousStoneFactory();

    @Test
    void createStoneWithValidDataReturnsSemiPreciousStone() {
        SemiPreciousStone stone = factory.createStone("Amethyst", "3.2", "80",
                "3", "7.0", "Brazil");

        assertEquals("Amethyst", stone.getName());
        assertEquals(3.2, stone.getWeight(), DELTA);
        assertEquals(80.0, stone.getPrice(), DELTA);
        assertEquals(3, stone.getTransparency());
        assertEquals(7.0, stone.getHardnessMohs(), DELTA);
        assertEquals("Brazil", stone.getOriginRegion());
    }

    @ParameterizedTest
    @CsvSource({
            "abc, 100, 3",     // weight is not a number
            "0, 100, 3",       // weight is zero
            "-1.5, 100, 3",    // weight is negative
            "1.0, abc, 3",     // price is not a number
            "1.0, 0, 3",       // price is zero
            "1.0, -5, 3",      // price is negative
            "1.0, 100, abc",   // transparency is not a number
            "1.0, 100, 0",     // transparency below range
            "1.0, 100, 6"      // transparency above range
    })
    void createStoneThrowsOnInvalidCommonFields(String weight, String price, String transparency) {
        assertThrows(InvalidStoneDataException.class,
                () -> factory.createStone("Garnet", weight, price, transparency, "7.5", "Russia"));
    }

    @ParameterizedTest
    @CsvSource({
            "0.01, 1",   // minimal positive weight, lower transparency bound
            "1.0, 5"     // upper transparency bound
    })
    void createStoneAcceptsBoundaryCommonValues(String weight, String transparency) {
        SemiPreciousStone stone = factory.createStone("Garnet", weight, "120",
                transparency, "7.5", "Russia");

        assertEquals(Double.parseDouble(weight), stone.getWeight(), DELTA);
        assertEquals(Integer.parseInt(transparency), stone.getTransparency());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "10", "6.5"})
    void createStoneAcceptsHardnessWithinRange(String hardness) {
        SemiPreciousStone stone = factory.createStone("Garnet", "1.0", "120",
                "3", hardness, "Russia");

        assertEquals(Double.parseDouble(hardness), stone.getHardnessMohs(), DELTA);
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "0.99", "10.01", "-3"})
    void createStoneThrowsOnInvalidHardness(String hardness) {
        assertThrows(InvalidStoneDataException.class,
                () -> factory.createStone("Garnet", "1.0", "120",
                        "3", hardness, "Russia"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t"})
    void createStoneThrowsOnBlankName(String name) {
        assertThrows(InvalidStoneDataException.class,
                () -> factory.createStone(name, "1.0", "120", "3", "7.5", "Russia"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t"})
    void createStoneThrowsOnBlankRegion(String region) {
        assertThrows(InvalidStoneDataException.class,
                () -> factory.createStone("Garnet", "1.0", "120", "3", "7.5", region));
    }
}