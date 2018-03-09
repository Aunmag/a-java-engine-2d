package aunmag.nightingale.utilities;

import org.junit.jupiter.api.Test;

import static aunmag.nightingale.utilities.UtilsMath.*;
import static org.junit.jupiter.api.Assertions.*;

class UtilsMathTest {

    private static final float precision = 0.000001f;

    @Test
    void testCorrectRadians() {
        assertEquals(0, correctRadians(0));
        assertEquals(1, correctRadians(1));
        assertEquals(-1, correctRadians(-1));
        assertEquals(Math.PI, correctRadians(Math.PI), precision);
        assertEquals(0, correctRadians(Math.PI * 2));
        assertEquals(0, correctRadians(Math.PI * 4));

        double radians = Math.PI * 2.0;
        assertEquals(0.1, correctRadians(radians + 0.1), precision);
        assertEquals(radians - 0.1, correctRadians(radians - 0.1), precision);
    }

    @Test
    void testCalculateDistanceBetween() {
        assertEquals(0, calculateDistanceBetween(0, 0, 0, 0));

        float n = 312346.848729800778264f;
        assertEquals(n, calculateDistanceBetween(n, 0, 0, 0));
        assertEquals(n, calculateDistanceBetween(0, n, 0, 0));
        assertEquals(n, calculateDistanceBetween(0, 0, n, 0));
        assertEquals(n, calculateDistanceBetween(0, 0, 0, n));
        assertEquals(n, calculateDistanceBetween(0, 0, 0, -n));
        assertEquals(n * 2, calculateDistanceBetween(0, n, 0, -n), precision);

        assertEquals(
                33.203915431767984,
                calculateDistanceBetween(548.25f, -1.5f, 537.75f, 30),
                precision
        );
    }

    @Test
    void testCalculateIsNumberInsideRange() {
        assertTrue(calculateIsNumberInsideRange(0, 0, 0));
        assertTrue(calculateIsNumberInsideRange(1, 1, 1));
        assertTrue(calculateIsNumberInsideRange(1, -1, 1));
        assertTrue(calculateIsNumberInsideRange(-1, -1, 1));
        assertTrue(calculateIsNumberInsideRange(0.5f, 0, 1));

        assertFalse(calculateIsNumberInsideRange(1, 0, 0));
        assertFalse(calculateIsNumberInsideRange(1, -1, 0));
        assertFalse(calculateIsNumberInsideRange(0.5f, -1, 0));
    }

    @Test
    void testLimitNumber() {
        assertEquals(0, limitNumber(0, 0, 0));
        assertEquals(0, limitNumber(0, 0, 1));
        assertEquals(1, limitNumber(0, 1, 1));
        assertEquals(-1, limitNumber(-1.5f, -1, 1));
        assertEquals(+1, limitNumber(+1.5f, -1, 1));
    }

}
