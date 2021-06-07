package xyz.theprogramsrc.supercoreapi.global.utils;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void checksumGeneratorTest() throws IOException, NoSuchAlgorithmException {
        // First we download the sample jar
        File pomFile = new File("pom.xml");
        String knownHash = "65b99694a741b0e3a0296f800b0dabde"; // We Store the known hash
        MessageDigest messageDigest = Utils.getDigest("MD5"); // We generate the message digest
        String generatedHash = Utils.generateFileChecksum(messageDigest, pomFile); // Now we generate the hash
        assertEquals(knownHash, generatedHash); // Now we check the hash
    }
}