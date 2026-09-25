package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PointTest {

        @Test
        void testDist() {
            Point a = new Point(0, 0);
            Point b = new Point(3, 4);
            assertEquals(5.0, a.dist(b), 0.0001); // 3-4-5 треугольник
        }

        @Test
        void testDistToOrigin() {
            Point p = new Point(3, 4);
            assertEquals(5.0, p.distToOrigin(), 0.0001);
        }

        @Test
        void testIsOnOneStraight_true() {
            Point a = new Point(0, 0);
            Point b = new Point(2, 0);
            Point c = new Point(1, 0);
            assertTrue(c.isOnOneStraight(a, b));
        }

        @Test
        void testIsOnOneStraight_false() {
            Point a = new Point(0, 0);
            Point b = new Point(2, 0);
            Point c = new Point(1, 5);
            assertFalse(c.isOnOneStraight(a, b));
        }
}