package factory;

import exception.InvalidStoneDataException;
import model.Clarity;
import model.PreciousStone;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class PreciousStoneFactoryTest {
    private final PreciousStoneFactory factory = new PreciousStoneFactory();

    @Test
    void createStoneWithValidDataReturnsPreciousStone() {
        PreciousStone stone = factory.createStone("Diamond", "2.5", "21.3",
                "5", "IF", "True");

        assertEquals("Diamond", stone.getName());
        assertEquals(2.5, stone.getWeight(), 1e-9);
        assertEquals(21.3, stone.getPrice(), 1e-9);
        assertEquals(5, stone.getTransparency());
        assertEquals(Clarity.IF, stone.getClarityGrade());
        assertTrue(stone.hasCertificate());
    }

    @ParameterizedTest
    @CsvSource({
            "Ruby, abc, 100, 3",    // weight is not a number
            "Ruby, 0, 100, 3",      // weight is zero
            "Ruby, -5.3, 100, 3",   // negative weight
            "Ruby, 1.0, abc, 3",    // price is not a number
            "Ruby, 1.0, 0, 3",      // price is zero
            "Ruby, 1.0, -5, 3",     // negative price
            "Ruby, 1.0, 100, 6",    // transparency out of range
            "Ruby, 1.0, 100, abc",    // transparency is not a number
    })
    void createStoneThrowsOnInvalidCommonFields(String name, String weight,
                                                String price, String transparency) {
        assertThrows(InvalidStoneDataException.class,
                () -> factory.createStone(name, weight, price, transparency, "VS1", "true"));
    }

    @ParameterizedTest
    @CsvSource({"1", "5"})
    void createStoneAcceptsBoundaryTransparency(String transparency) {
        PreciousStone stone = factory.createStone("Ruby", "1.0", "100", transparency, "VS1", "true");
        assertEquals(Integer.parseInt(transparency), stone.getTransparency());
    }

    @ParameterizedTest
    @CsvSource({
            "'', IF, True",          // empty name
            "'      ', IF, True",    // name out of spaces
            ", IF, True",            // name is null
            "Ruby, XYZ, True",       // invalid clarity
            "Ruby, if, True",        // lowercase clarity
            "Ruby, IF, yes"          // invalid hasCertificate
    })
    void createStoneThrowsOnInvalidPreciousFields(String name, String clarity, String hasCertificate) {
        assertThrows(InvalidStoneDataException.class,
                () -> factory.createStone(name, "1.0", "1.0", "1", clarity, hasCertificate));
    }
}
