package xyz.theprogramsrc.supercoreapi.global.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    void capitalizeTest() {
        String capitalized = Utils.capitalize("abCde");
        assertEquals("AbCde", capitalized);
    }

    @Test
    void toTicksTest() {
        long ticks = Utils.toTicks(5);
        assertEquals(100, ticks);
    }

    @Test
    void toMillisTest() {
        long millis = Utils.toMillis(5);
        assertEquals(5000, millis);
    }

    @Test
    void isIntegerTest() {
        boolean integer = Utils.isInteger("1");
        assertTrue(integer);
    }

    @Test
    void parseHypotenuseTest() {
        double hypotenuse = Utils.parseHypotenuse(3,4);
        assertEquals(25, hypotenuse);
    }

    @Test
    void squareRootTest() {
        double sqrt = Utils.squareRoot(Utils.parseHypotenuse(3,4));
        assertEquals(5, sqrt);
    }
}