package org.example;

import org.junit.jupiter.api.Test;

import java.util.function.BiPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.ToDoubleFunction;

import static org.junit.jupiter.api.Assertions.*;

public class Lab7Test {
    private static final double DELTA = 1e-9;

    private static final double[] SQUARE = {0, 0, 4, 0, 4, 4, 0, 4};

    @Test
    void discriminantWithOwnInterface() {
        TriDoubleFunction discriminant = (a, b, c) -> b * b - 4 * a * c;

        assertEquals(1.0, discriminant.apply(1, -3, 2), DELTA);
        assertEquals(-16.0, discriminant.apply(1, 2, 5), DELTA);
    }

    @Test
    void discriminantWithStandardInterface() {
        ToDoubleFunction<double[]> discriminant =
                coeffs -> coeffs[1] * coeffs[1] - 4 * coeffs[0] * coeffs[2];

        assertEquals(1.0, discriminant.applyAsDouble(new double[]{1, -3, 2}), DELTA);
        assertEquals(-16.0, discriminant.applyAsDouble(new double[]{1, 2, 5}), DELTA);
    }

    @Test
    void countDigitWithOwnInterface() {
        int digit = 1;
        DigitCounter counter = x -> Main.countDigit(x, digit);

        assertEquals(3, counter.count(12131));
        assertEquals(0, counter.count(2345));
    }

    @Test
    void countDigitWithStandardInterface() {
        int digit = 5;
        IntUnaryOperator counter = x -> Main.countDigit(x, digit);

        assertEquals(2, counter.applyAsInt(505));
    }

    @Test
    void countDigitHandlesNegativeNumbersAndZero() {
        assertEquals(2, Main.countDigit(-505, 5));
        assertEquals(1, Main.countDigit(0, 0));
        assertEquals(0, Main.countDigit(0, 7));
    }

    @Test
    void pointInSquareWithOwnInterface() {
        PointChecker checker = (x, y) -> Main.isInside(SQUARE, x, y);

        assertTrue(checker.contains(2, 2));
        assertFalse(checker.contains(5, 2));
    }

    @Test
    void pointInSquareWithStandardInterface() {
        BiPredicate<Double, Double> checker = (x, y) -> Main.isInside(SQUARE, x, y);

        assertTrue(checker.test(2.0, 2.0));
        assertFalse(checker.test(-1.0, 2.0));
    }


    @Test
    void polygonWithLessThanThreeVerticesThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> Main.isInside(new double[]{0, 0, 1, 1}, 0, 0));
    }
}